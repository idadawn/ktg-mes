import request from '@/utils/request'

// 查询外协领料单头列表
export function listOutsourceissue(query) {
  return request({
    url: '/mes/wm/outsourceissue/list',
    method: 'get',
    params: query
  })
}

// 查询外协领料单头详细
export function getOutsourceissue(issueId) {
  return request({
    url: '/mes/wm/outsourceissue/' + issueId,
    method: 'get'
  })
}

// 新增外协领料单头
export function addOutsourceissue(data) {
  return request({
    url: '/mes/wm/outsourceissue',
    method: 'post',
    data: data
  })
}

// 修改外协领料单头
export function updateOutsourceissue(data) {
  return request({
    url: '/mes/wm/outsourceissue',
    method: 'put',
    data: data
  })
}

/**
 * 检查领料数量与拣货数量是否一致
 * @param  issueId 
 * @returns 
 */
export function checkQuantity(issueId) {
  return request({
    url: '/mes/wm/outsourceissue/checkQuantity/' + issueId,
    method: 'get'
  })
}

// 删除外协领料单头
export function delOutsourceissue(issueId) {
  return request({
    url: '/mes/wm/outsourceissue/' + issueId,
    method: 'delete'
  })
}

//执行发料
export function execute(issueId) {
  return request({
    url: '/mes/wm/outsourceissue/' + issueId,
    method: 'put'
  })
}