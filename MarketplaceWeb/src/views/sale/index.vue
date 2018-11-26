<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('product.productName')" v-model="listQuery.productName" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.categoryId" :placeholder="$t('product.productType')" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id"/>
      </el-select>
      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
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
      <el-table-column :label="$t('product.productName')" min-width="30%" align="center">
        <template slot-scope="scope">
          <router-link :to="'/sale/buy/'+scope.row.id" class="link-type">
            <span>{{ scope.row.productName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.productType')" min-width="15%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.categoryId | categoryFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.originalPrice')" min-width="15%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.originalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.salePrice')" min-width="15%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.salePrice }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('product.star')" min-width="15%">
        <template slot-scope="scope">
          <svg-icon v-for="n in +scope.row.star" :key="n" icon-class="star" class="meta-item__icon"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="15%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="'/sale/buy/'+scope.row.id">
            <el-button type="primary" size="small" icon="el-icon-edit">{{ $t('sale.buy') }}</el-button>
          </router-link>
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
import { fetchSaleProductList } from '@/api/sale'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

var categoryDictList = []

export default {
  name: 'SaleProductTable',
  directives: {
    waves
  },
  filters: {
    categoryFilter(categoryId) {
      if (categoryDictList !== undefined && categoryDictList !== null) {
        for (var i = 0; i < categoryDictList.length; i++) {
          if (categoryId === categoryDictList[i].id) {
            return categoryDictList[i].categoryName
          }
        }
      }
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
      sortOptions: [
        { label: '默认排序', key: '+id' },
        { label: '名称升序', key: '+productName' },
        { label: '名称降序', key: '-productName' },
        { label: '价格升序', key: '+salePrice' },
        { label: '价格降序', key: '-salePrice' }
      ],
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
      fetchSaleProductList(this.listQuery).then(response => {
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
    handleBuy(row) {
      console.log('row', row)
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['序号', '产品名称', '产品类型', '出厂价格（元）', '销售价格（元）', '产品等级', '上架时间', '下架时间', '状态']
        const filterVal = ['id', 'productName', 'categoryId', 'originalPrice', 'salePrice', 'star', 'onlineDate', 'offlineDate', 'status']
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
