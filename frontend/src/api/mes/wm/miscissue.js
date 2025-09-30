import request from '@/utils/request'

// 查询杂项出库单列表
export function listMiscissue(query) {
  return request({
    url: '/mes/wm/miscissue/list',
    method: 'get',
    params: query
  })
}

// 查询杂项出库单详细
export function getMiscissue(issueId) {
  return request({
    url: '/mes/wm/miscissue/' + issueId,
    method: 'get'
  })
}

// 新增杂项出库单
export function addMiscissue(data) {
  return request({
    url: '/mes/wm/miscissue',
    method: 'post',
    data: data
  })
}

// 修改杂项出库单
export function updateMiscissue(data) {
  return request({
    url: '/mes/wm/miscissue',
    method: 'put',
    data: data
  })
}

// 删除杂项出库单
export function delMiscissue(issueId) {
  return request({
    url: '/mes/wm/miscissue/' + issueId,
    method: 'delete'
  })
}

//执行出库
export function execute(issueId) {
  return request({
    url: '/mes/wm/miscissue/' + issueId,
    method: 'put'
  })
}
