<template>
  <div class="buyProduct-container">
    <el-form ref="postForm" :model="postForm" class="form-container">

      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <el-form-item label-width="45px" label="会员:" class="postInfo-container-item">
                  <el-select v-model="vipUser" :loading="loading" :remote-method="getRemoteUserList" filterable remote reserve-keyword placeholder="请选择会员">
                    <el-option v-for="item in vipListOptions" :key="item.id" :label="item.vipName" :value="item.id" />
                  </el-select>
                </el-form-item>
              </div>
              <div class="component-item">
                <el-col :span="4">
                  <pan-thumb width="100px" height="100px" image="https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191">
                    vue-element-admin
                  </pan-thumb>
                </el-col>
                <el-col :span="20" class="text-center">
                  <div class="postInfo-container">
                    <input v-model="postForm.id" type="hidden">

                    <el-row>
                      <el-col :span="24">
                        <el-form-item label-width="80px" label="产品民称:" class="postInfo-container-item">
                          <span>{{ postForm.productName }}</span>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label-width="80px" label="产品原价:" class="postInfo-container-item">
                          <span style="color:gray;text-decoration:line-through">{{ postForm.originalPrice }} 元</span>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label-width="80px" label="活动价格:" class="postInfo-container-item">
                          {{ postForm.salePrice }} 元
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="24">
                        <el-form-item label-width="80px" label="上线时间:" class="postInfo-container-item">
                          {{ postForm.onlineDate }}
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label-width="80px" label="下线时间:" class="postInfo-container-item">
                          {{ postForm.offlineDate }}
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label-width="80px" label="产品等级:" class="postInfo-container-item">
                          <el-rate
                            v-model="postForm.star"
                            :max="postForm.star"
                            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                            :low-threshold="1"
                            :high-threshold="5"
                            style="margin-top:8px;"/>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label-width="80px" label="数量:" class="postInfo-container-item">
                          <input v-model="quantity" type="number" >
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </el-col>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>总价： {{ totalPrice }} 元</span>
              </div>
              <div class="component-item">
                <el-button v-loading="loading" type="primary" @click="createOrder">提交订单</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-form>

  </div>
</template>

<script>

import PanThumb from '@/components/PanThumb'
import Sticky from '@/components/Sticky' // 粘性header组件
import { fetchProductDetail } from '@/api/sale'
import { fetchVipList } from '@/api/vip'
import { placeOrder } from '@/api/order'

const defaultForm = {
  status: 'draft',
  categoryId: undefined, // 产品类别
  productName: '', // 产品名称
  originalPrice: '', // 出厂价格
  salePrice: '', // 销售价格
  detail: '', // 产品详情
  content_short: '', // 文章摘要
  source_uri: '', // 文章外链
  image_uri: '', // 文章图片
  display_time: undefined, // 前台展示时间
  id: undefined,
  platforms: ['a-platform'],
  comment_disabled: false,
  star: 3
}

export default {
  name: 'BuyProductDetail',
  components: { Sticky, PanThumb },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      vipUser: '',
      vipListOptions: [],
      totalPrice: undefined,
      quantity: 1,
      categoryId: undefined,
      id: undefined,
      postForm: Object.assign({}, defaultForm),
      categoryList: [],
      setMoneyPrefix: '¥ '
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    }
  },
  created() {
    const id = this.$route.params && this.$route.params.id
    console.log('route.params', this.$route.params)
    this.quantity = this.$route.params.quantity
    this.fetchData(id)
    // this.getRemoteUserList()
  },
  methods: {
    handleTabClick(tab, event) {
      console.log('tab.event', tab, event)
    },
    fetchData(id) {
      fetchProductDetail(id).then(response => {
        this.postForm = response.data.data
        console.log('this.postForm', this.postForm)
        this.totalPrice = this.quantity * this.postForm.salePrice
      }).catch(err => {
        console.log(err)
      })
    },
    getRemoteUserList(params) {
      console.log('getRemoteUserList', query)
      var query
      if (params !== '') {
        query = {
          vipName: params
        }
      }
      fetchVipList(query).then(response => {
        if (!response.data.data.content) return
        this.vipListOptions = response.data.data.content
      })
    },
    addCart() {
      console.log(this.postForm)
    },
    createOrder() {
      if (this.vipUser === '') {
        this.$message({
          title: '提示',
          message: '请选择会员',
          type: 'warning',
          duration: 2000
        })
        return
      }
      console.log('createOrder', this.vipUser)
      if (this.quantity < 1) {
        this.$message({
          message: '产品数量至少为1',
          type: 'warning'
        })
        return
      }
      var postData = {
        productList: [{
          productNo: this.postForm.productNo,
          quantity: this.quantity
        }],
        vipId: this.vipUser,
        username: 'abc'
      }
      placeOrder(postData).then(response => {
        var respData = response.data
        console.log('respData', respData)
        if (respData.code === 200) {
          this.$router.push({ name: 'PayOrder', params: { id: respData.data.id }})
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.buyProduct-container {
  position: relative;
  .createPost-main-container {
    padding: 40px 45px 20px 50px;
    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;
      .postInfo-container-item {
        float: left;
      }
    }
    .editor-container {
      min-height: 500px;
      margin: 0 0 30px;
      .editor-upload-btn-container {
        text-align: right;
        margin-right: 10px;
        .editor-upload-btn {
          display: inline-block;
        }
      }
    }
  }
  .word-counter {
    width: 40px;
    position: absolute;
    right: -10px;
    top: 0px;
  }

}
</style>
