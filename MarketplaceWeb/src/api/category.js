import request from '@/utils/request'

export function fetchCategoryList(query) {
  return request({
    url: '/category/list',
    method: 'get',
    params: query
  })
}

export function fetchNormalCategoryList(query) {
  return request({
    url: '/category/listNomal',
    method: 'get',
    params: query
  })
}

export function fetchCategoryDetail(id) {
  return request({
    url: '/category/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/category/pv',
    method: 'get',
    params: { pv }
  })
}

export function createCategory(data) {
  return request({
    url: '/category/create',
    method: 'put',
    data
  })
}

export function updateCategory(data) {
  return request({
    url: '/category/update',
    method: 'post',
    data
  })
}

export function modifyStatus(data) {
  return request({
    url: '/category/modifyStatus',
    method: 'post',
    data
  })
}
