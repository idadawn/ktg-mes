import request from '@/utils/request'

// 查询批次记录列表
export function listBatch(query) {
  return request({
    url: '/mes/wm/batch/list',
    method: 'get',
    params: query
  })
}

// 查询批次记录详细
export function getBatch(batchId) {
  return request({
    url: '/mes/wm/batch/' + batchId,
    method: 'get'
  })
}

// 查询批次记录列表
export function listForward(query) {
  return request({
    url: '/mes/wm/batch/listForward',
    method: 'get',
    params: query
  })
}
// 查询批次记录列表
export function listBackward(query) {
  return request({
    url: '/mes/wm/batch/listBackward',
    method: 'get',
    params: query
  })
}


// 新增批次记录
export function addBatch(data) {
  return request({
    url: '/mes/wm/batch',
    method: 'post',
    data: data
  })
}

// 修改批次记录
export function updateBatch(data) {
  return request({
    url: '/mes/wm/batch',
    method: 'put',
    data: data
  })
}

// 删除批次记录
export function delBatch(batchId) {
  return request({
    url: '/mes/wm/batch/' + batchId,
    method: 'delete'
  })
}
