import request from '@/utils/request'

// 查询科室列表列表
export function listFbyDept(query) {
  return request({
    url: '/zayy/fbyDept/list',
    method: 'get',
    params: query
  })
}

// 查询科室列表详细
export function getFbyDept(id) {
  return request({
    url: '/zayy/fbyDept/' + id,
    method: 'get'
  })
}

// 新增科室列表
export function addFbyDept(data) {
  return request({
    url: '/zayy/fbyDept',
    method: 'post',
    data: data
  })
}

// 修改科室列表
export function updateFbyDept(data) {
  return request({
    url: '/zayy/fbyDept',
    method: 'put',
    data: data
  })
}

// 删除科室列表
export function delFbyDept(id) {
  return request({
    url: '/zayy/fbyDept/' + id,
    method: 'delete'
  })
}
