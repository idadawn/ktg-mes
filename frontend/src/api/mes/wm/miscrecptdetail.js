import request from '@/utils/request'

// 查询杂项入库单明细列表
export function listMiscrecptdetail(query) {
  return request({
    url: '/wm/miscrecptdetail/list',
    method: 'get',
    params: query
  })
}

// 查询杂项入库单明细详细
export function getMiscrecptdetail(detailId) {
  return request({
    url: '/wm/miscrecptdetail/' + detailId,
    method: 'get'
  })
}

// 新增杂项入库单明细
export function addMiscrecptdetail(data) {
  return request({
    url: '/wm/miscrecptdetail',
    method: 'post',
    data: data
  })
}

// 修改杂项入库单明细
export function updateMiscrecptdetail(data) {
  return request({
    url: '/wm/miscrecptdetail',
    method: 'put',
    data: data
  })
}

// 删除杂项入库单明细
export function delMiscrecptdetail(detailId) {
  return request({
    url: '/wm/miscrecptdetail/' + detailId,
    method: 'delete'
  })
}
