import request from '@/utils/request'

// 查询图形报组件列表
export function listChart(query) {
  return request({
    url: '/mes/report/chart/list',
    method: 'get',
    params: query
  })
}


// 查询当前登录人全下下的图形报组件列表
export function getMyCharts() {
  return request({
    url: '/mes/report/chart/getMyCharts',
    method: 'get'
  })
}

// 查询图形报组件详细
export function getChart(chartId) {
  return request({
    url: '/mes/report/chart/' + chartId,
    method: 'get'
  })
}



// 新增图形报组件
export function addChart(data) {
  return request({
    url: '/mes/report/chart',
    method: 'post',
    data: data
  })
}

// 修改图形报组件
export function updateChart(data) {
  return request({
    url: '/mes/report/chart',
    method: 'put',
    data: data
  })
}

// 删除图形报组件
export function delChart(chartId) {
  return request({
    url: '/mes/report/chart/' + chartId,
    method: 'delete'
  })
}
