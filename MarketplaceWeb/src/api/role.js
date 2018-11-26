import request from '@/utils/request'

export function fetchRoleList(query) {
  return request({
    url: '/role/list',
    method: 'get',
    params: query
  })
}

export function createRole(data) {
  return request({
    url: '/role/create',
    method: 'put',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/role/update',
    method: 'post',
    data
  })
}
