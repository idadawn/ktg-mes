import request from '@/utils/request'

// 查询杂项入库单行列表
export function listMiscrecptline(query) {
  return request({
    url: '/mes/wm/miscrecptline/list',
    method: 'get',
    params: query
  })
}

// 查询杂项入库单行详细
export function getMiscrecptline(lineId) {
  return request({
    url: '/mes/wm/miscrecptline/' + lineId,
    method: 'get'
  })
}

// 新增杂项入库单行
export function addMiscrecptline(data) {
  return request({
    url: '/mes/wm/miscrecptline',
    method: 'post',
    data: data
  })
}

// 修改杂项入库单行
export function updateMiscrecptline(data) {
  return request({
    url: '/mes/wm/miscrecptline',
    method: 'put',
    data: data
  })
}

// 删除杂项入库单行
export function delMiscrecptline(lineId) {
  return request({
    url: '/mes/wm/miscrecptline/' + lineId,
    method: 'delete'
  })
}
