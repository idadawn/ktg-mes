import request from '@/utils/request'

// 查询库存盘点结果列表
export function listStocktakingresult(query) {
  return request({
    url: '/mes/wm/stocktakingresult/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点结果详细
export function getStocktakingresult(resultId) {
  return request({
    url: '/mes/wm/stocktakingresult/' + resultId,
    method: 'get'
  })
}

// 新增库存盘点结果
export function addStocktakingresult(data) {
  return request({
    url: '/mes/wm/stocktakingresult',
    method: 'post',
    data: data
  })
}

// 修改库存盘点结果
export function updateStocktakingresult(data) {
  return request({
    url: '/mes/wm/stocktakingresult',
    method: 'put',
    data: data
  })
}

// 删除库存盘点结果
export function delStocktakingresult(resultId) {
  return request({
    url: '/mes/wm/stocktakingresult/' + resultId,
    method: 'delete'
  })
}
