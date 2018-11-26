import request from '@/utils/request'

export function fetchSaleProductList(query) {
  return request({
    url: '/sale/list',
    method: 'get',
    params: query
  })
}

export function fetchProductEmpty() {
  return request({
    url: '/product/empty',
    method: 'get'
  })
}

export function fetchProductDetail(id) {
  console.log('/product/detail/', id)
  return request({
    url: '/product/detail/' + id,
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/product/pv',
    method: 'get',
    params: { pv }
  })
}

export function createProduct(data) {
  return request({
    url: '/product/create',
    method: 'post',
    data
  })
}

export function updateProduct(data) {
  return request({
    url: '/product/update',
    method: 'post',
    data
  })
}
