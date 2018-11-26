<template>
  <div class="buyProduct-container">
    <el-form ref="postForm" :model="postForm" class="form-container">

      <sticky :class-name="'sub-navbar '+postForm.status">
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="addCart">加入购物车
        </el-button>
        <router-link :to="'/sale/placeorder/' + postForm.id + '/' + quantity">
          <el-button type="warning" >{{ $t('sale.buyNow') }}</el-button>
        </router-link>
      </sticky>

      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>{{ postForm.productName }}</span>
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
                          <el-input-number v-model="quantity" :min="1" :max="10" />
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
            <div class="tab-container">
              <el-tabs v-model="activeName" type="border-card" style="margin-top:15px;" @tab-click="handleTabClick">
                <el-tab-pane name="PRODUCTDETAIL" label="详情">
                  <div v-html=" postForm.detail"/>
                </el-tab-pane>
                <el-tab-pane name="PRODUCTCOMMENT" label="评论">
                  评论
                </el-tab-pane>
              </el-tabs>
            </div>
          </el-col>
        </el-row>

      </div>
    </el-form>

  </div>
</template>

<script>
// import tabPane from './components/tabPane'
import PanThumb from '@/components/PanThumb'
import Sticky from '@/components/Sticky' // 粘性header组件
import { fetchProductDetail } from '@/api/sale'
import { fetchNormalCategoryList } from '@/api/category'

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
      quantity: 1,
      tabMapOptions: [
        { label: '产品详情', key: 'PRODUCTDETAIL' },
        { label: '产品评论', key: 'PRODUCTCOMMENT' }
      ],
      activeName: 'PRODUCTDETAIL',
      categoryId: undefined,
      id: undefined,
      postForm: Object.assign({}, defaultForm),
      loading: false,
      categoryList: [],
      userListOptions: [],
      setMoneyPrefix: '¥ '
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    }
  },
  created() {
    // this.getCategoryList()
    const id = this.$route.params && this.$route.params.id
    this.fetchData(id)
  },
  methods: {
    handleTabClick(tab, event) {
      console.log('tab.event', tab, event)
    },
    getCategoryList() {
      fetchNormalCategoryList().then(response => {
        this.categoryList = response.data.data
      }).catch(err => {
        console.log(err)
      })
    },
    fetchData(id) {
      fetchProductDetail(id).then(response => {
        this.postForm = response.data.data
        // Just for test
        // this.postForm.productName += `   Article Id:${this.postForm.id}`
        // this.postForm.content_short += `   Article Id:${this.postForm.id}`
      }).catch(err => {
        console.log(err)
      })
    },
    showComment() {
      this.createdTimes = this.createdTimes + 1
    },
    addCart() {
      console.log(this.postForm)
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
