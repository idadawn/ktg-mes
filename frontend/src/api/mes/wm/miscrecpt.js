import request from '@/utils/request'

// 查询杂项入库单列表
export function listMiscrecpt(query) {
  return request({
    url: '/mes/wm/miscrecpt/list',
    method: 'get',
    params: query
  })
}

// 查询杂项入库单详细
export function getMiscrecpt(recptId) {
  return request({
    url: '/mes/wm/miscrecpt/' + recptId,
    method: 'get'
  })
}

// 新增杂项入库单
export function addMiscrecpt(data) {
  return request({
    url: '/mes/wm/miscrecpt',
    method: 'post',
    data: data
  })
}

// 修改杂项入库单
export function updateMiscrecpt(data) {
  return request({
    url: '/mes/wm/miscrecpt',
    method: 'put',
    data: data
  })
}

// 删除杂项入库单
export function delMiscrecpt(recptId) {
  return request({
    url: '/mes/wm/miscrecpt/' + recptId,
    method: 'delete'
  })
}

//执行入库
export function execute(recptId) {
  return request({
    url: '/mes/wm/miscrecpt/' + recptId,
    method: 'put'
  })
}
