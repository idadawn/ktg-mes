import request from '@/utils/request'

// 查询外协入库单行列表
export function listOutsourcerecptline(query) {
  return request({
    url: '/mes/wm/oursourcerecptline/list',
    method: 'get',
    params: query
  })
}

// 查询外协入库单行详细
export function getOutsourcerecptline(lineId) {
  return request({
    url: '/mes/wm/outsourcerecptline/' + lineId,
    method: 'get'
  })
}


// 查询外协入库单行列表（带明细）
export function listWithDetail(query) {
  return request({
    url: '/mes/wm/outsourcerecptline/listWithDetail',
    method: 'get',
    params: query
  })
}


// 新增外协入库单行
export function addOutsourcerecptline(data) {
  return request({
    url: '/mes/wm/outsourcerecptline',
    method: 'post',
    data: data
  })
}

// 修改外协入库单行
export function updateOutsourcerecptline(data) {
  return request({
    url: '/mes/wm/outsourcerecptline',
    method: 'put',
    data: data
  })
}

// 删除外协入库单行
export function delOutsourcerecptline(lineId) {
  return request({
    url: '/mes/wm/outsourcerecptline/' + lineId,
    method: 'delete'
  })
}
