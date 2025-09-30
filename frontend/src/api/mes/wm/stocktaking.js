import request from '@/utils/request'

// 查询库存盘点记录列表
export function listStocktaking(query) {
  return request({
    url: '/mes/wm/stocktaking/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点记录详细
export function getStocktaking(takingId) {
  return request({
    url: '/mes/wm/stocktaking/' + takingId,
    method: 'get'
  })
}

// 新增库存盘点记录
export function addStocktaking(data) {
  return request({
    url: '/mes/wm/stocktaking',
    method: 'post',
    data: data
  })
}

// 修改库存盘点记录
export function updateStocktaking(data) {
  return request({
    url: '/mes/wm/stocktaking',
    method: 'put',
    data: data
  })
}

// 删除库存盘点记录
export function delStocktaking(takingId) {
  return request({
    url: '/mes/wm/stocktaking/' + takingId,
    method: 'delete'
  })
}

//执行转移
export function execute(takingId){
  return request({
    url: '/mes/wm/stocktaking/'+takingId,
    method: 'put'
  })
}
