import request from '@/utils/request'

// 查询安检记录列表
export function listRecordAj(query) {
  return request({
    url: '/zayy/recordAj/list',
    method: 'get',
    params: query
  })
}

// 查询安检记录详细
export function getRecordAj(id) {
  return request({
    url: '/zayy/recordAj/' + id,
    method: 'get'
  })
}

// 新增安检记录
export function addRecordAj(data) {
  return request({
    url: '/zayy/recordAj',
    method: 'post',
    data: data
  })
}

// 修改安检记录
export function updateRecordAj(data) {
  return request({
    url: '/zayy/recordAj',
    method: 'put',
    data: data
  })
}

// 删除安检记录
export function delRecordAj(id) {
  return request({
    url: '/zayy/recordAj/' + id,
    method: 'delete'
  })
}
