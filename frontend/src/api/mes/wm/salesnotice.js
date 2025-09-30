import request from '@/utils/request'

// 查询发货通知单列表
export function listSalesnotice(query) {
  return request({
    url: '/mes/wm/salesnotice/list',
    method: 'get',
    params: query
  })
}

// 查询发货通知单详细
export function getSalesnotice(noticeId) {
  return request({
    url: '/mes/wm/salesnotice/' + noticeId,
    method: 'get'
  })
}

// 新增发货通知单
export function addSalesnotice(data) {
  return request({
    url: '/mes/wm/salesnotice',
    method: 'post',
    data: data
  })
}

// 修改发货通知单
export function updateSalesnotice(data) {
  return request({
    url: '/mes/wm/salesnotice',
    method: 'put',
    data: data
  })
}

// 删除发货通知单
export function delSalesnotice(noticeId) {
  return request({
    url: '/mes/wm/salesnotice/' + noticeId,
    method: 'delete'
  })
}
