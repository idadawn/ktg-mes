import request from '@/utils/request'

// 查询备料通知单列表
export function listMrnotice(query) {
  return request({
    url: '/mes/wm/mrnotice/list',
    method: 'get',
    params: query
  })
}

// 查询备料通知单详细
export function getMrnotice(noticeId) {
  return request({
    url: '/mes/wm/mrnotice/' + noticeId,
    method: 'get'
  })
}

// 新增备料通知单
export function addMrnotice(data) {
  return request({
    url: '/mes/wm/mrnotice',
    method: 'post',
    data: data
  })
}

// 修改备料通知单
export function updateMrnotice(data) {
  return request({
    url: '/mes/wm/mrnotice',
    method: 'put',
    data: data
  })
}

// 删除备料通知单
export function delMrnotice(noticeId) {
  return request({
    url: '/mes/wm/mrnotice/' + noticeId,
    method: 'delete'
  })
}
