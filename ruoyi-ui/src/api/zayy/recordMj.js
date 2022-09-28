import request from '@/utils/request'

// 查询门禁管理列表
export function listRecordMj(query) {
  return request({
    url: '/zayy/recordMj/list',
    method: 'get',
    params: query
  })
}

// 查询门禁管理详细
export function getRecordMj(id) {
  return request({
    url: '/zayy/recordMj/' + id,
    method: 'get'
  })
}

// 新增门禁管理
export function addRecordMj(data) {
  return request({
    url: '/zayy/recordMj',
    method: 'post',
    data: data
  })
}

// 修改门禁管理
export function updateRecordMj(data) {
  return request({
    url: '/zayy/recordMj',
    method: 'put',
    data: data
  })
}

// 删除门禁管理
export function delRecordMj(id) {
  return request({
    url: '/zayy/recordMj/' + id,
    method: 'delete'
  })
}
