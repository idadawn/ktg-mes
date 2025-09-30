import request from '@/utils/request'

// 查询转移调拨单明细列表
export function listTransferdetail(query) {
  return request({
    url: '/mes/wm/transferdetail/list',
    method: 'get',
    params: query
  })
}

// 查询转移调拨单明细详细
export function getTransferdetail(detailId) {
  return request({
    url: '/mes/wm/transferdetail/' + detailId,
    method: 'get'
  })
}

// 新增转移调拨单明细
export function addTransferdetail(data) {
  return request({
    url: '/mes/wm/transferdetail',
    method: 'post',
    data: data
  })
}

// 修改转移调拨单明细
export function updateTransferdetail(data) {
  return request({
    url: '/mes/wm/transferdetail',
    method: 'put',
    data: data
  })
}

// 删除转移调拨单明细
export function delTransferdetail(detailId) {
  return request({
    url: '/mes/wm/transferdetail/' + detailId,
    method: 'delete'
  })
}
