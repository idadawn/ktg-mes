import request from '@/utils/request'

// 查询杂项出库单行列表
export function listMiscissueline(query) {
  return request({
    url: '/mes/wm/miscissueline/list',
    method: 'get',
    params: query
  })
}

// 查询杂项出库单行详细
export function getMiscissueline(lineId) {
  return request({
    url: '/mes/wm/miscissueline/' + lineId,
    method: 'get'
  })
}

// 新增杂项出库单行
export function addMiscissueline(data) {
  return request({
    url: '/mes/wm/miscissueline',
    method: 'post',
    data: data
  })
}

// 修改杂项出库单行
export function updateMiscissueline(data) {
  return request({
    url: '/mes/wm/miscissueline',
    method: 'put',
    data: data
  })
}

// 删除杂项出库单行
export function delMiscissueline(lineId) {
  return request({
    url: '/mes/wm/miscissueline/' + lineId,
    method: 'delete'
  })
}
