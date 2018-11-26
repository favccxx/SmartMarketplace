<template>
  <div class="app-container">
    <split-pane :default-percent="30" split="vertical">
      <template slot="paneL" style="width:300px;">
        <div class="left-container">
          <el-tree :data="data" :props="defaultProps" :default-expand-all="true" @node-click="handleNodeClick" />
        </div>
      </template>
      <template slot="paneR">
        <div class="right-container">
          YYYY
        </div>
      </template>
    </split-pane>
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import { fetchRoleList } from '@/api/role'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'RoleView',
  components: { splitPane },
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
      defaultProps: {
        children: 'children',
        label: 'roleName'
      },
      data: [],
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        roleCode: undefined,
        type: undefined,
        sort: '+id'
      },
      showReviewer: false,
      temp: {
        id: undefined,
        roleCode: '',
        idCard: '',
        vipMail: '',
        vipTel: '',
        vipStar: 'BRONZE',
        vipFee: 0.00,
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
        roleCode: [{ required: true, message: '会员名称不允许为空', trigger: 'blur' }],
        vipTel: [{ required: true, message: '电话号码不允许为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    resize() {
      console.log('resize')
    },
    getList() {
      this.listLoading = true
      fetchRoleList(this.listQuery).then(response => {
        var responseData = response.data
        if (responseData.code === 200) {
          var childrenRoles = responseData.data.content

          var roleItem = {
            id: 0,
            label: '角色列表',
            roleCode: '角色列表',
            children: childrenRoles
          }
          this.data.push(roleItem)
          console.log('roleData', this.data)
          // this.data = roles
          this.listLoading = false
        }

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleNodeClick(data) {
      console.log(data)
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
        roleCode: '',
        idCard: '',
        vipMail: '',
        vipTel: '',
        vipStar: 'BRONZE',
        vipFee: 0.00,
        detail: '',
        status: 'ENABLE'
      }
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

<style  scoped>
  .components-container {
    position: relative;
    height: 100vh;
  }

  .left-container {
    background-color: #F38181;
    height: 100%;
  }

  .right-container {
    background-color: #FCE38A;
    height: 200px;
  }

  .top-container {
    background-color: #FCE38A;
    width: 100%;
    height: 100%;
  }

  .bottom-container {
    width: 100%;
    background-color: #95E1D3;
    height: 100%;
  }
</style>
