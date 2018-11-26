<template>
  <div class="app-container">
    <p class="warn-content">
      {{ shop.mallName }}
    </p>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>负责人：{{ shop.charger }}</span>  <span style="float:right;">联系电话：{{ shop.chargeTel }}</span>
      </div>
      <div class="component-item">
        <div v-html="shop.description"/>
      </div>
    </el-card>

  </div>
</template>

<script>

import { fetchShopDetail } from '@/api/shop'

export default {
  name: 'Guide',
  data() {
    return {
      shop: {
        mallName: '',
        mallType: '',
        description: '',
        charger: '',
        chargeTel: ''
      }
    }
  },
  mounted() {
  },
  created() {
    this.fetchShopDetail()
  },
  methods: {
    fetchShopDetail() {
      fetchShopDetail().then(response => {
        this.shop = response.data.data
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>
