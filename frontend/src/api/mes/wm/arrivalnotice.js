import request from '@/utils/request'

// 查询到货通知单列表
export function listArrivalnotice(query) {
  return request({
    url: '/mes/wm/arrivalnotice/list',
    method: 'get',
    params: query
  })
}

// 查询到货通知单详细
export function getArrivalnotice(noticeId) {
  return request({
    url: '/mes/wm/arrivalnotice/' + noticeId,
    method: 'get'
  })
}

// 新增到货通知单
export function addArrivalnotice(data) {
  return request({
    url: '/mes/wm/arrivalnotice',
    method: 'post',
    data: data
  })
}

// 修改到货通知单
export function updateArrivalnotice(data) {
  return request({
    url: '/mes/wm/arrivalnotice',
    method: 'put',
    data: data
  })
}

// 删除到货通知单
export function delArrivalnotice(noticeId) {
  return request({
    url: '/mes/wm/arrivalnotice/' + noticeId,
    method: 'delete'
  })
}
