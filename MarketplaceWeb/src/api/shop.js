import request from '@/utils/request'

export function fetchShopDetail(query) {
  return request({
    url: '/mall/detail',
    method: 'get',
    params: query
  })
}
