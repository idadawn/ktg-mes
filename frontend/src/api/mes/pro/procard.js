import request from '@/utils/request'

// 查询工序流转卡列表
export function listProcard(query) {
  return request({
    url: '/mes/pro/procard/list',
    method: 'get',
    params: query
  })
}

// 查询工序流转卡详细
export function getProcard(cardId) {
  return request({
    url: '/mes/pro/procard/' + cardId,
    method: 'get'
  })
}

// 新增工序流转卡
export function addProcard(data) {
  return request({
    url: '/mes/pro/procard',
    method: 'post',
    data: data
  })
}

// 修改工序流转卡
export function updateProcard(data) {
  return request({
    url: '/mes/pro/procard',
    method: 'put',
    data: data
  })
}

// 删除工序流转卡
export function delProcard(cardId) {
  return request({
    url: '/mes/pro/procard/' + cardId,
    method: 'delete'
  })
}
