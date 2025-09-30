import request from '@/utils/request'

// 查询备料通知单明细列表
export function listMrnoticeline(query) {
  return request({
    url: '/mes/wm/mrnoticeline/list',
    method: 'get',
    params: query
  })
}

// 查询备料通知单明细详细
export function getMrnoticeline(lineId) {
  return request({
    url: '/mes/wm/mrnoticeline/' + lineId,
    method: 'get'
  })
}

// 新增备料通知单明细
export function addMrnoticeline(data) {
  return request({
    url: '/mes/wm/mrnoticeline',
    method: 'post',
    data: data
  })
}

// 修改备料通知单明细
export function updateMrnoticeline(data) {
  return request({
    url: '/mes/wm/mrnoticeline',
    method: 'put',
    data: data
  })
}

// 删除备料通知单明细
export function delMrnoticeline(lineId) {
  return request({
    url: '/mes/wm/mrnoticeline/' + lineId,
    method: 'delete'
  })
}
