import request from '@/utils/request'

// 查询到货通知单行列表
export function listArrivalnoticeline(query) {
  return request({
    url: '/mes/wm/arrivalnoticeline/list',
    method: 'get',
    params: query
  })
}

// 查询到货通知单行详细
export function getArrivalnoticeline(lineId) {
  return request({
    url: '/mes/wm/arrivalnoticeline/' + lineId,
    method: 'get'
  })
}

// 新增到货通知单行
export function addArrivalnoticeline(data) {
  return request({
    url: '/mes/wm/arrivalnoticeline',
    method: 'post',
    data: data
  })
}

// 修改到货通知单行
export function updateArrivalnoticeline(data) {
  return request({
    url: '/mes/wm/arrivalnoticeline',
    method: 'put',
    data: data
  })
}

// 删除到货通知单行
export function delArrivalnoticeline(lineId) {
  return request({
    url: '/mes/wm/arrivalnoticeline/' + lineId,
    method: 'delete'
  })
}
