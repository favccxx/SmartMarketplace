<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('product.productCode')" v-model="listQuery.productNo" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input :placeholder="$t('product.productName')" v-model="listQuery.productName" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.categoryId" :placeholder="$t('product.productType')" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id"/>
      </el-select>
      <el-select v-model="listQuery.status" :placeholder="$t('table.status')" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in statusOptions" :key="item.value" :label="item.name" :value="item.value"/>
      </el-select>
      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <router-link :to="'/product/create'">
        <el-button type="primary" class="filter-item" style="margin-left: 10px;" icon="el-icon-edit">{{ $t('table.add') }}</el-button>
      </router-link>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('table.export') }}</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column :label="$t('table.id')" type="index" align="center" width="80" />
      <el-table-column :label="$t('product.productCode')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.productNo }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.productName')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.productName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.productType')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.categoryId | categoryFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.originalPrice')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.originalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.salePrice')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.salePrice }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.star')" min-width="10%">
        <template slot-scope="scope">
          <svg-icon v-for="n in +scope.row.star" :key="n" icon-class="star" class="meta-item__icon"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.onlineDate')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.onlineDate | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.offlineDate')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.offlineDate | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.status')" class-name="status-col" min-width="5%">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="10%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status!='400'" type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button v-if="scope.row.status=='300'" size="mini" type="success" @click="handleModifyStatus(scope.row,'published')">{{ $t('table.publish') }}
          </el-button>
          <el-button v-if="scope.row.status!='400'" size="mini" type="danger" @click="handleModifyStatus(scope.row,'deleted')">{{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel"/>
        <el-table-column prop="pv" label="Pv"/>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { fetchNormalCategoryList } from '@/api/category'
import { fetchProductList, fetchProductDetail, createProduct, updateProduct } from '@/api/product'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

var categoryDictList = []

export default {
  name: 'ProductTable',
  directives: {
    waves
  },
  filters: {
    categoryFilter(categoryId) {
      console.log('categoryId', categoryId)
      if (categoryDictList !== undefined && categoryDictList !== null) {
        for (var i = 0; i < categoryDictList.length; i++) {
          if (categoryId === categoryDictList[i].id) {
            console.log('categoryDictList[i].id', categoryDictList[i].id, categoryDictList[i].categoryName, categoryDictList)
            return categoryDictList[i].categoryName
          }
        }
      }
    },
    statusFilter(status) {
      const statusMap = {
        200: '已发布',
        300: '草稿',
        400: '已删除'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      categoryList: [],
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        categoryId: undefined,
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID升序', key: '+id' }, { label: 'ID降序', key: '-id' }],
      statusOptions: [
        {
          name: '全部',
          value: ''
        },
        {
          name: '已发布',
          value: 200
        },
        {
          name: '草稿',
          value: 300
        },
        {
          name: '已删除',
          value: 400
        }],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getCategoryList()
    // this.getList()
  },
  methods: {
    getCategoryList() {
      fetchNormalCategoryList().then(response => {
        this.categoryList = response.data.data
        categoryDictList = response.data.data
        this.getList()
      }).catch(err => {
        console.log(err)
      })
    },
    getList() {
      this.listLoading = true
      fetchProductList(this.listQuery).then(response => {
        var responseData = response.data
        if (responseData.code === 200) {
          this.list = responseData.data.content
          this.total = responseData.data.totalElements
          this.listLoading = false
        }
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'
          createProduct(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateProduct(tempData).then(() => {
            for (const v of this.list) {
              if (v.id === this.temp.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      this.$notify({
        title: '成功',
        message: '删除成功',
        type: 'success',
        duration: 2000
      })
      const index = this.list.indexOf(row)
      this.list.splice(index, 1)
    },
    handleFetchPv(pv) {
      fetchProductDetail(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['序号', '产品编码', '产品名称', '产品类型', '出厂价格（元）', '销售价格（元）', '产品等级', '上架时间', '下架时间', '状态']
        const filterVal = ['id', 'productNo', 'productName', 'categoryId', 'originalPrice', 'salePrice', 'star', 'onlineDate', 'offlineDate', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
