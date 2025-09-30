import request from '@/utils/request'

// 查询库区设置列表
export function listLocation(query) {
  return request({
    url: '/mes/wm/location/list',
    method: 'get',
    params: query
  })
}

// 查询库区设置详细
export function getLocation(locationId) {
  return request({
    url: '/mes/wm/location/' + locationId,
    method: 'get'
  })
}

// 新增库区设置
export function addLocation(data) {
  return request({
    url: '/mes/wm/location',
    method: 'post',
    data: data
  })
}

// 修改库区设置
export function updateLocation(data) {
  return request({
    url: '/mes/wm/location',
    method: 'put',
    data: data
  })
}

// 删除库区设置
export function delLocation(locationId) {
  return request({
    url: '/mes/wm/location/' + locationId,
    method: 'delete'
  })
}

// 设置库区下所有库位是否允许产品混放
export function setProductMixing(locationId, flag){
  return request({
    url: '/mes/wm/location/setProductMixing',
    method: 'post',
    params: {
      'locationId': locationId,
      'flag': flag
    }
  })
}

// 设置库区下所有库位是否允许批次混放
export function setBatchMixing(locationId, flag){
  return request({
    url: '/mes/wm/location/setBatchMixing',
    method: 'post',
    params: {
      'locationId': locationId,
      'flag': flag
    }
  })
}

//更改库区的冻结状态
export function changeFrozenState(locationId, status) {
  const data = {
    'locationId': locationId,
    'frozenFlag': status
  }
  return request({
    url: '/mes/wm/location',
    method: 'put',
    data: data
  })
}
