import request from '@/utils/request'

// 查询刀具管理列表
export function listTools(query) {
  return request({
    url: '/system/tools/list',
    method: 'get',
    params: query
  })
}

// 查询刀具管理详细
export function getTools(toolId) {
  return request({
    url: '/system/tools/' + toolId,
    method: 'get'
  })
}

// 新增刀具管理
export function addTools(data) {
  return request({
    url: '/system/tools',
    method: 'post',
    data: data
  })
}

// 修改刀具管理
export function updateTools(data) {
  return request({
    url: '/system/tools',
    method: 'put',
    data: data
  })
}

// 删除刀具管理
export function delTools(toolId) {
  return request({
    url: '/system/tools/' + toolId,
    method: 'delete'
  })
}
