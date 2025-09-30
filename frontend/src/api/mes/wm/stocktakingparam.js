import request from '@/utils/request'

// 查询库存盘点方案参数列表
export function listStocktakingparam(query) {
  return request({
    url: '/mes/wm/stocktakingparam/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点方案参数详细
export function getStocktakingparam(paramId) {
  return request({
    url: '/mes/wm/stocktakingparam/' + paramId,
    method: 'get'
  })
}

// 新增库存盘点方案参数
export function addStocktakingparam(data) {
  return request({
    url: '/mes/wm/stocktakingparam',
    method: 'post',
    data: data
  })
}

// 修改库存盘点方案参数
export function updateStocktakingparam(data) {
  return request({
    url: '/mes/wm/stocktakingparam',
    method: 'put',
    data: data
  })
}

// 删除库存盘点方案参数
export function delStocktakingparam(paramId) {
  return request({
    url: '/mes/wm/stocktakingparam/' + paramId,
    method: 'delete'
  })
}
