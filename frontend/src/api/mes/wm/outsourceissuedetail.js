import request from '@/utils/request'

// 查询外协领料单明细列表
export function listOutsourceissuedetail(query) {
  return request({
    url: '/mes/wm/outsourceissuedetail/list',
    method: 'get',
    params: query
  })
}

// 查询外协领料单明细详细
export function getOutsourceissuedetail(detailId) {
  return request({
    url: '/mes/wm/outsourceissuedetail/' + detailId,
    method: 'get'
  })
}

// 新增外协领料单明细
export function addOutsourceissuedetail(data) {
  return request({
    url: '/mes/wm/outsourceissuedetail',
    method: 'post',
    data: data
  })
}

// 修改外协领料单明细
export function updateOutsourceissuedetail(data) {
  return request({
    url: '/mes/wm/outsourceissuedetail',
    method: 'put',
    data: data
  })
}

// 删除外协领料单明细
export function delOutsourceissuedetail(detailId) {
  return request({
    url: '/mes/wm/outsourceissuedetail/' + detailId,
    method: 'delete'
  })
}
