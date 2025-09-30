import request from '@/utils/request'

// 查询库存盘点明细列表
export function listStocktakingline(query) {
  return request({
    url: '/mes/wm/stocktakingline/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点明细详细
export function getStocktakingline(lineId) {
  return request({
    url: '/mes/wm/stocktakingline/' + lineId,
    method: 'get'
  })
}

// 新增库存盘点明细
export function addStocktakingline(data) {
  return request({
    url: '/mes/wm/stocktakingline',
    method: 'post',
    data: data
  })
}

// 修改库存盘点明细
export function updateStocktakingline(data) {
  return request({
    url: '/mes/wm/stocktakingline',
    method: 'put',
    data: data
  })
}

// 删除库存盘点明细
export function delStocktakingline(lineId) {
  return request({
    url: '/mes/wm/stocktakingline/' + lineId,
    method: 'delete'
  })
}
