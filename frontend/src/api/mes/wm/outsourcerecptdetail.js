import request from '@/utils/request'

// 查询外协入库单明细列表
export function listOutsourcerecptdetail(query) {
  return request({
    url: '/mes/wm/outsourcerecptdetail/list',
    method: 'get',
    params: query
  })
}

// 查询外协入库单明细详细
export function getOutsourcerecptdetail(detailId) {
  return request({
    url: '/mes/wm/outsourcerecptdetail/' + detailId,
    method: 'get'
  })
}

// 新增外协入库单明细
export function addOutsourcerecptdetail(data) {
  return request({
    url: '/mes/wm/outsourcerecptdetail',
    method: 'post',
    data: data
  })
}

// 修改外协入库单明细
export function updateOutsourcerecptdetail(data) {
  return request({
    url: '/mes/wm/outsourcerecptdetail',
    method: 'put',
    data: data
  })
}

// 删除外协入库单明细
export function delOutsourcerecptdetail(detailId) {
  return request({
    url: '/mes/wm/outsourcerecptdetail/' + detailId,
    method: 'delete'
  })
}
