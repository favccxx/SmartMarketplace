<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('log.uri')" v-model="listQuery.uri" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input :placeholder="$t('log.statusCode')" v-model="listQuery.statusCode" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.method" :placeholder="$t('log.method')" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in methodOptions" :key="item" :label="item" :value="item"/>
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
      <el-table-column :label="$t('log.clientIp')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.clientIp }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.uri')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.uri }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.method')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.method }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.statusCode')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.statusCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.params')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.params }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.startTime')" min-width="15%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('log.spendTime')" class-name="status-col" min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.spendTime }}</span>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>

  </div>
</template>

<script>
import { fetchLogs } from '@/api/log'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'AuditLogTable',
  directives: {
    waves
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        ENABLE: 'success',
        DISABLE: 'info',
        DELETED: 'danger'
      }
      return statusMap[status]
    },
    statusLabelFilter(status) {
      const statusMap = {
        ENABLE: '启用',
        DISABLE: '禁用',
        DELETED: '删除'
      }
      return statusMap[status]
    },
    starFilter(status) {
      const statusMap = {
        BRONZE: '铜牌',
        SILVER: '银牌',
        GOLD: '金牌'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        logName: undefined,
        type: undefined,
        sort: '-id'
      },
      methodOptions: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
      sortOptions: [
        { label: '默认排序', key: '-id' },
        { label: '响应时间升序', key: '+spendTime' },
        { label: '响应时间降序', key: '-spendTime' }
      ],
      starOptions: [
        {
          label: '铜牌',
          value: 'BRONZE'
        },
        {
          label: '银牌',
          value: 'SILVER'
        },
        {
          label: '金牌',
          value: 'GOLD'
        }
      ],
      showReviewer: false,
      temp: {
        id: undefined,
        logName: '',
        idCard: '',
        logMail: '',
        logTel: '',
        logStar: 'BRONZE',
        logFee: 0.00,
        detail: '',
        status: 'ENABLE'
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
        logName: [{ required: true, message: '会员名称不允许为空', trigger: 'blur' }],
        logTel: [{ required: true, message: '电话号码不允许为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchLogs(this.listQuery).then(response => {
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
        logName: '',
        idCard: '',
        logMail: '',
        logTel: '',
        logStar: 'BRONZE',
        logFee: 0.00,
        detail: '',
        status: 'ENABLE'
      }
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
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
