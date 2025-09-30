import request from '@/utils/request'

// 查询待检验任务清单
export function listPending(query) {
  return request({
    url: '/mes/qc/pending/list',
    method: 'get',
    params: query
  })
}

