import request from '@/utils/request'

// 查询刀具加工参数列表
export function listPocket(query) {
  return request({
    url: '/system/pocket/list',
    method: 'get',
    params: query
  })
}

// 查询刀具加工参数详细
export function getPocket(toolId) {
  return request({
    url: '/system/pocket/' + toolId,
    method: 'get'
  })
}

// 新增刀具加工参数
export function addPocket(data) {
  return request({
    url: '/system/pocket',
    method: 'post',
    data: data
  })
}

// 修改刀具加工参数
export function updatePocket(data) {
  return request({
    url: '/system/pocket',
    method: 'put',
    data: data
  })
}

// 删除刀具加工参数
export function delPocket(toolId) {
  return request({
    url: '/system/pocket/' + toolId,
    method: 'delete'
  })
}