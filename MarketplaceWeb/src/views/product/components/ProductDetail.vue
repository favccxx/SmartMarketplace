<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :class-name="'sub-navbar '+postForm.status">
        <CommentDropdown v-model="postForm.comment_disabled" />
        <PlatformDropdown v-model="postForm.platforms" />
        <SourceUrlDropdown v-model="postForm.source_uri" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">发布
        </el-button>
        <el-button v-loading="loading" type="warning" @click="draftForm">草稿</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-row>

          <Warning />

          <el-col :span="24">

            <div class="postInfo-container">
              <input v-model="postForm.id" type="hidden">
              <el-form-item style="margin-bottom: 40px;" label-width="85px" label="产品类别:">
                <el-select v-model="postForm.categoryId" clearable class="filter-item">
                  <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id"/>
                </el-select>
              </el-form-item>

              <el-form-item style="margin-bottom: 40px;" label-width="85px" label="产品名称:">
                <el-input v-model="postForm.productName" placeholder="请输入产品名称" />
              </el-form-item>

              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="80px" label="出厂价格:" class="postInfo-container-item">
                    <input v-model="postForm.originalPrice" :precision="2" type="number" placeholder="请输入内容"> 元
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="80px" label="销售价格:" class="postInfo-container-item">
                    <input v-model="postForm.salePrice" :precision="2" type="number" name="salePrice" > 元
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="80px" label="上架时间:" class="postInfo-container-item">
                    <el-date-picker v-model="postForm.onlineDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" placeholder="选择上架时间"/>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="80px" label="下架时间:" class="postInfo-container-item">
                    <el-date-picker v-model="postForm.offlineDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" placeholder="选择下架时间"/>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label-width="80px" label="产品等级:" class="postInfo-container-item">
                    <el-rate
                      v-model="postForm.star"
                      :max="5"
                      :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                      :low-threshold="1"
                      :high-threshold="5"
                      style="margin-top:8px;"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>

        <div class="editor-container">
          <Tinymce ref="editor" :height="400" v-model="postForm.detail" />
        </div>

        <div style="margin-bottom: 20px;">
          <Upload v-model="postForm.image_uri" :pro-id="id" />
        </div>
      </div>
    </el-form>

  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import Upload from '@/components/Upload/productImage'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky' // 粘性header组件
import { validateURL } from '@/utils/validate'
import { fetchProductDetail, fetchProductEmpty, updateProduct } from '@/api/product'
import { fetchNormalCategoryList } from '@/api/category'
import { userSearch } from '@/api/remoteSearch'
import Warning from './Warning'
import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from './Dropdown'

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
  name: 'ProductDetail',
  components: { Tinymce, MDinput, Upload, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    const validateSourceUri = (rule, value, callback) => {
      if (value) {
        if (validateURL(value)) {
          callback()
        } else {
          this.$message({
            message: '外链url填写不正确',
            type: 'error'
          })
          callback(new Error('外链url填写不正确'))
        }
      } else {
        callback()
      }
    }
    return {
      categoryId: undefined,
      id: undefined,
      postForm: Object.assign({}, defaultForm),
      loading: false,
      categoryList: [],
      userListOptions: [],
      setMoneyPrefix: '¥ ',
      rules: {
        image_uri: [{ validator: validateRequire }],
        productName: [{ validator: validateRequire }],
        detail: [{ validator: validateRequire }],
        source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
      }
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    }
  },
  created() {
    this.getCategoryList()
    console.log('editStatus', this.isEdit)
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.fetchEmpty()
      // this.postForm = Object.assign({}, defaultForm)
    }
  },
  methods: {
    getCategoryList() {
      fetchNormalCategoryList().then(response => {
        this.categoryList = response.data.data
      }).catch(err => {
        console.log(err)
      })
    },
    fetchEmpty() {
      fetchProductEmpty().then(response => {
        this.id = response.data.data.id
        this.postForm.id = response.data.data.id
      }).catch(err => {
        console.log(err)
      })
    },
    fetchData(id) {
      fetchProductDetail(id).then(response => {
        this.postForm = response.data
        // Just for test
        this.postForm.productName += `   Article Id:${this.postForm.id}`
        this.postForm.content_short += `   Article Id:${this.postForm.id}`
      }).catch(err => {
        console.log(err)
      })
    },
    submitForm() {
      this.postForm.display_time = parseInt(this.display_time / 1000)
      console.log(this.postForm)
      this.$refs.postForm.validate(valid => {
        if (valid) {
          if (!this.postForm.categoryId || this.postForm.categoryId.length === 0) {
            this.$message({
              message: '请选择产品类别',
              type: 'warning'
            })
            return
          }
          if (this.postForm.detail.length === 0 || this.postForm.productName.length === 0) {
            this.$message({
              message: '请填写必要的标题和内容',
              type: 'warning'
            })
            return
          }
          if (!this.postForm.onlineDate || this.postForm.onlineDate.length === 0 || !this.postForm.offlineDate || this.postForm.offlineDate.length === 0) {
            this.$message({
              message: '请填写产品上线日期和下线日期',
              type: 'warning'
            })
            return
          }
          updateProduct(this.postForm).then(response => {
            console.log('updateProduct.response', response)
          }).catch(err => {
            console.log(err)
          })
          this.loading = true
          this.$notify({
            title: '成功',
            message: '发布文章成功',
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 'published'
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    draftForm() {
      if (this.postForm.detail.length === 0 || this.postForm.productName.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      })
      this.postForm.status = 'draft'
    },
    getRemoteUserList(query) {
      userSearch(query).then(response => {
        if (!response.data.items) return
        this.userListOptions = response.data.items.map(v => v.name)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.createPost-container {
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
