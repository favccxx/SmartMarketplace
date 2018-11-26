import request from '@/utils/request'

export function fetchProductSliderImages(query) {
  return request({
    url: '/image/product/list',
    method: 'get',
    params: query
  })
}

export function uploadImage(query) {
  return request({
    url: '/image/upload',
    method: 'post',
    params: query
  })
}
