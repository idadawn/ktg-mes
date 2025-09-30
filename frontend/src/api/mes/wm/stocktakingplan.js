import request from '@/utils/request'

// 查询库存盘点方案列表
export function listStocktakingplan(query) {
  return request({
    url: '/mes/wm/stocktakingplan/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点方案详细
export function getStocktakingplan(planId) {
  return request({
    url: '/mes/wm/stocktakingplan/' + planId,
    method: 'get'
  })
}

// 新增库存盘点方案
export function addStocktakingplan(data) {
  return request({
    url: '/mes/wm/stocktakingplan',
    method: 'post',
    data: data
  })
}

// 修改库存盘点方案
export function updateStocktakingplan(data) {
  return request({
    url: '/mes/wm/stocktakingplan',
    method: 'put',
    data: data
  })
}

// 删除库存盘点方案
export function delStocktakingplan(planId) {
  return request({
    url: '/mes/wm/stocktakingplan/' + planId,
    method: 'delete'
  })
}
