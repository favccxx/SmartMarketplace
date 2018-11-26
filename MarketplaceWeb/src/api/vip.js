import request from '@/utils/request'

export function fetchVipList(query) {
  return request({
    url: '/vip/list',
    method: 'get',
    params: query
  })
}

export function fetchVipUser(id) {
  return request({
    url: '/vip/detail',
    method: 'get',
    params: { id }
  })
}

export function createVipUser(data) {
  return request({
    url: '/vip/create',
    method: 'put',
    data
  })
}

export function updateVipUser(data) {
  return request({
    url: '/vip/update',
    method: 'post',
    data
  })
}
