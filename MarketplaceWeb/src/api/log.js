import request from '@/utils/request'

export function fetchLogs(query) {
  return request({
    url: '/log/list',
    method: 'get',
    params: query
  })
}
