import request from '@/utils/request'

// 查询发货通知单行列表
export function listSalesnoticeline(query) {
  return request({
    url: '/mes/wm/salesnoticeline/list',
    method: 'get',
    params: query
  })
}

// 查询发货通知单行详细
export function getSalesnoticeline(lineId) {
  return request({
    url: '/mes/wm/salesnoticeline/' + lineId,
    method: 'get'
  })
}

// 新增发货通知单行
export function addSalesnoticeline(data) {
  return request({
    url: '/mes/wm/salesnoticeline',
    method: 'post',
    data: data
  })
}

// 修改发货通知单行
export function updateSalesnoticeline(data) {
  return request({
    url: '/mes/wm/salesnoticeline',
    method: 'put',
    data: data
  })
}

// 删除发货通知单行
export function delSalesnoticeline(lineId) {
  return request({
    url: '/mes/wm/salesnoticeline/' + lineId,
    method: 'delete'
  })
}
