import request from '@/utils/request'

// 查询外协入库单列表
export function listOutsourcerecpt(query) {
  return request({
    url: '/mes/wm/outsourcerecpt/list',
    method: 'get',
    params: query
  })
}

// 查询外协入库单详细
export function getOutsourcerecpt(recptId) {
  return request({
    url: '/mes/wm/outsourcerecpt/' + recptId,
    method: 'get'
  })
}

// 新增外协入库单
export function addOutsourcerecpt(data) {
  return request({
    url: '/mes/wm/outsourcerecpt',
    method: 'post',
    data: data
  })
}

// 修改外协入库单
export function updateOutsourcerecpt(data) {
  return request({
    url: '/mes/wm/outsourcerecpt',
    method: 'put',
    data: data
  })
}

// 删除外协入库单
export function delOutsourcerecpt(recptId) {
  return request({
    url: '/mes/wm/outsourcerecpt/' + recptId,
    method: 'delete'
  })
}


//执行入库
export function execute(recptId) {
  return request({
    url: '/mes/wm/outsourcerecpt/' + recptId,
    method: 'put'
  })
}