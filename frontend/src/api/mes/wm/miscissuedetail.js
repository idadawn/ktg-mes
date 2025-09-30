import request from '@/utils/request'

// 查询杂项出库单明细列表
export function listMiscissuedetail(query) {
  return request({
    url: '/mes/wm/miscissuedetail/list',
    method: 'get',
    params: query
  })
}

// 查询杂项出库单明细详细
export function getMiscissuedetail(detailId) {
  return request({
    url: '/mes/wm/miscissuedetail/' + detailId,
    method: 'get'
  })
}

// 新增杂项出库单明细
export function addMiscissuedetail(data) {
  return request({
    url: '/mes/wm/miscissuedetail',
    method: 'post',
    data: data
  })
}

// 修改杂项出库单明细
export function updateMiscissuedetail(data) {
  return request({
    url: '/mes/wm/miscissuedetail',
    method: 'put',
    data: data
  })
}

// 删除杂项出库单明细
export function delMiscissuedetail(detailId) {
  return request({
    url: '/mes/wm/miscissuedetail/' + detailId,
    method: 'delete'
  })
}
