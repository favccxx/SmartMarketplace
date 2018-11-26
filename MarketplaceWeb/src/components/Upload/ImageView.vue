<template>
  <div class="image-preview">
    <div v-show="imageViewItem.id>1" class="image-preview-wrapper">
      <img :src="'http://172.17.3.111:8088/api/image/thumbnail/' + imageViewItem.id">
      <div class="image-preview-action">
        <i class="el-icon-delete" @click="rmImage"/>
      </div>
    </div>
  </div>
</template>

<script>
import { getToken } from '@/api/qiniu'
import { fetchProductSliderImages } from '@/api/image'

export default {
  name: 'ImageView',
  props: {
    imageItem: {
      type: Object,
      default: undefined
    },
    value: {
      type: String,
      default: ''
    },
    pId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      imageViewItem: this.imageItem
    }
  },
  computed: {
    imageUrl() {
      return this.value
    }
  },
  methods: {
    rmImage() {
      this.emitInput('')
    },
    emitInput(val) {
      this.$emit('input', val)
    },
    handleImageSuccess(file) {
      this.getProductSliderImages()
      // this.emitInput(file.files.file)
    },
    getProductSliderImages() {
      var params = {
        productId: this.productId
      }
      fetchProductSliderImages(params).then(response => {
        var respData = response.data
        console.log('sliders', respData)
      })
    },
    beforeUpload() {
      const _self = this
      return new Promise((resolve, reject) => {
        console.log('resolve', resolve, reject)
        getToken().then(response => {
          console.log('upload.token', response)
          const key = response.data.qiniu_key
          const token = response.data.qiniu_token
          _self._data.dataObj.token = token
          _self._data.dataObj.key = key
          _self._data.dataObj.productId = this.productId
          this.tempUrl = response.data.qiniu_url
          resolve(true)
        }).catch(err => {
          console.log(err)
          reject(false)
        })
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.upload-container {
  width: 100%;
  position: relative;
  @include clearfix;
  .image-uploader {
    width: 35%;
    float: left;
  }
  .image-preview {
    width: 200px;
    height: 200px;
    position: relative;
    border: 1px dashed #d9d9d9;
    float: left;
    margin-left: 50px;
    .image-preview-wrapper {
      position: relative;
      width: 100%;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .image-preview-action {
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      top: 0;
      cursor: default;
      text-align: center;
      color: #fff;
      opacity: 0;
      font-size: 20px;
      background-color: rgba(0, 0, 0, .5);
      transition: opacity .3s;
      cursor: pointer;
      text-align: center;
      line-height: 200px;
      .el-icon-delete {
        font-size: 36px;
      }
    }
    &:hover {
      .image-preview-action {
        opacity: 1;
      }
    }
  }
  .image-app-preview {
    width: 320px;
    height: 180px;
    position: relative;
    border: 1px dashed #d9d9d9;
    float: left;
    margin-left: 50px;
    .app-fake-conver {
      height: 44px;
      position: absolute;
      width: 100%; // background: rgba(0, 0, 0, .1);
      text-align: center;
      line-height: 64px;
      color: #fff;
    }
  }
}
</style>
