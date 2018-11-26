import request from '@/utils/request'

export function fetchOrderList(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query
  })
}

export function fetchOrderDetail(param) {
  return request({
    url: '/order/detail/' + param,
    method: 'get'
  })
}

export function fetchMyOrderList(query) {
  return request({
    url: '/iorder/list',
    method: 'get',
    params: query
  })
}

export function placeOrder(data) {
  return request({
    url: '/iorder/placeOrder',
    method: 'post',
    data
  })
}

export function payOrder(data) {
  return request({
    url: '/iorder/pay',
    method: 'post',
    data
  })
}

export function cancelOrder(pathId) {
  return request({
    url: '/iorder/cancel/' + pathId,
    method: 'post'
  })
}
