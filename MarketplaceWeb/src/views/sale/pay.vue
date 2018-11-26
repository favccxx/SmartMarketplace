<template>
  <div class="buyProduct-container">
    <el-form ref="postForm" :model="postForm" class="form-container">

      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>订单编号：{{ postForm.orderNo }}</span>  <span style="float:right;">订单金额：{{ postForm.orderPrice }} 元</span>
              </div>

              <div v-for="item in productList" :key="item.id">
                <div class="component-item">
                  <el-col :span="4">
                    <pan-thumb width="100px" height="100px" image="https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191">
                      vue-element-admin
                    </pan-thumb>
                  </el-col>
                  <el-col :span="20" class="text-center">
                    <div class="postInfo-container">
                      <el-row>
                        <el-col :span="24">
                          <el-form-item label-width="80px" label="产品名称:" class="postInfo-container-item">
                            <span style="color:gray;text-decoration:line-through">{{ item.productName }} 元</span>
                          </el-form-item>
                        </el-col>

                        <el-col :span="24">
                          <el-form-item label-width="80px" label="产品售价:" class="postInfo-container-item">
                            {{ item.price }} 元
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </el-col>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>支付方式：</span>
                <el-radio-group v-model="payType">
                  <el-radio :label="1">会员卡</el-radio>
                  <el-radio :label="2">微信</el-radio>
                  <el-radio :label="3">支付宝</el-radio>
                  <el-radio :label="4">现金</el-radio>
                  <el-radio :label="5">免支付</el-radio>
                </el-radio-group>
              </div>
              <div class="component-item">
                <el-button v-loading="loading" type="primary" @click="payOrder">提交</el-button>
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
import { fetchOrderDetail, payOrder } from '@/api/order'

export default {
  name: 'PayOrderForm',
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
      payType: 1,
      postForm: {
        id: 0,
        orderNo: '', // 订单编码
        originalPrice: '', // 原价
        orderPrice: '', // 订单价格
        couponPrice: '', // 优惠价格
        orderDate: '', // 订单日期
        status: '', // 订单状态
        vipId: '', // 会员Id
        userName: '' // 员工用户名
      },
      productList: []
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
    // this.quantity = this.$route.params.quantity
    this.fetchData(id)
  },
  methods: {
    handleTabClick(tab, event) {
      console.log('tab.event', tab, event)
    },
    fetchData(id) {
      fetchOrderDetail(id).then(response => {
        console.log('fetchOrderDetail', response)
        this.postForm = response.data.data
        this.productList = response.data.data.productList
        console.log('this.postForm', this.postForm)
        this.totalPrice = this.quantity * this.postForm.salePrice
      }).catch(err => {
        console.log(err)
      })
    },
    addCart() {
      console.log(this.postForm)
    },
    payOrder() {
      var params = {
        orderNo: this.postForm.orderNo,
        payType: this.payType
      }
      payOrder(params).then(response => {
        console.log('placeOrder.response', response)
        this.$message({
          message: '保存成功',
          type: 'success',
          showClose: true,
          duration: 1000
        })
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
