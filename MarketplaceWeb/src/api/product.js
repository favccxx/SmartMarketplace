import request from '@/utils/request'

export function fetchProductList(query) {
  return request({
    url: '/product/list',
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
  return request({
    url: '/product/detail',
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
