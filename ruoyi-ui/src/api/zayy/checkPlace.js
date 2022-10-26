import request from '@/utils/request'

// 查询二级管理片区列表
export function listCheckPlace(query) {
  return request({
    url: '/zayy/checkPlace/list',
    method: 'get',
    params: query
  })
}

// 查询二级管理片区详细
export function getCheckPlace(id) {
  return request({
    url: '/zayy/checkPlace/' + id,
    method: 'get'
  })
}

// 新增二级管理片区
export function addCheckPlace(data) {
  return request({
    url: '/zayy/checkPlace',
    method: 'post',
    data: data
  })
}

// 修改二级管理片区
export function updateCheckPlace(data) {
  return request({
    url: '/zayy/checkPlace',
    method: 'put',
    data: data
  })
}

// 删除二级管理片区
export function delCheckPlace(id) {
  return request({
    url: '/zayy/checkPlace/' + id,
    method: 'delete'
  })
}
