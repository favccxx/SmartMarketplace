<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select v-model="listQuery.status" :placeholder="$t('order.status')" clearable style="width: 120px" class="filter-item">
        <el-option v-for="item in statusOptions" :key="item.value" :label="item.name" :value="item.value"/>
      </el-select>
      <el-input :placeholder="$t('order.orderNo')" v-model="listQuery.orderNo" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
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
      <el-table-column :label="$t('order.orderNo')" width="300" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.orderNo }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.vip')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.vipName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.orderPrice')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.orderPrice | priceFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.productList')" min-width="30%" align="center">
        <template slot-scope="scope">
          <div v-for="item in scope.row.productList" :key="item.id" style="text-align:left;">
            <h5>产品名称： {{ item.productName }}</h5>
            <div>产品价格：{{ item.price }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.status')" min-width="10%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | statusShowFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.payStatus')" min-width="10%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.payStatus | statusFilter">{{ scope.row.payStatus | payStatusShowFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('order.createTime')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="10%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="'/sale/pay/' + scope.row.id">
            <el-button v-if="scope.row.status=='CREATED' && scope.row.payStatus==0" type="primary" size="mini">{{ $t('order.pay') }}</el-button>
          </router-link>
          <el-button v-if="scope.row.status=='CREATED' && scope.row.payStatus==0" size="mini" type="danger" @click="handleCancelOrder(scope.row)">{{ $t('table.cancel') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>

  </div>
</template>

<script>

import user from '@/store/modules/user'
import { fetchMyOrderList, cancelOrder } from '@/api/order'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'MyOrderTable',
  directives: {
    waves
  },
  filters: {
    priceFilter(value) {
      value = Number(value)
      return value.toFixed(2)
    },
    statusFilter(status) {
      const statusMap = {
        'FINISHED': 'success',
        'CREATED': 'info',
        'CANCELD': 'danger',
        0: 'success',
        1: 'warning',
        2: 'info'
      }
      return statusMap[status]
    },
    statusShowFilter(status) {
      const statusShowMap = {
        'CREATED': '已创建',
        'CANCELD': '已取消',
        'FINISHED': '已完成'
      }
      return statusShowMap[status]
    },
    payStatusShowFilter(status) {
      const statusShowMap = {
        0: '待支付',
        1: '已支付',
        2: '已退款'
      }
      return statusShowMap[status]
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
        username: user.state.name,
        status: 'NEEDPAY',
        sort: '+id'
      },
      statusOptions: [
        {
          name: '待支付',
          value: 'NEEDPAY'
        },
        {
          name: '已完成',
          value: 'FINISHED'
        },
        {
          name: '已取消',
          value: 'CANCELD'
        }]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchMyOrderList(this.listQuery).then(response => {
        var respData = response.data
        if (respData.code === 200) {
          this.list = respData.data.content
          this.total = respData.data.totalElements
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
    handleCancelOrder(row) {
      var orderNo = row.orderNo
      cancelOrder(orderNo).then((response) => {
        var respData = response.data
        if (respData.code === 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          })
          row.status = 'CANCELD'
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
