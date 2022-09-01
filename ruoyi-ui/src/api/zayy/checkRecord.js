import request from '@/utils/request'

// 查询巡检记录列表
export function listCheckRecord(query) {
  return request({
    url: '/zayy/checkRecord/list',
    method: 'get',
    params: query
  })
}

// 查询巡检记录详细
export function getCheckRecord(id) {
  return request({
    url: '/zayy/checkRecord/' + id,
    method: 'get'
  })
}

// 新增巡检记录
export function addCheckRecord(data) {
  return request({
    url: '/zayy/checkRecord',
    method: 'post',
    data: data
  })
}

// 修改巡检记录
export function updateCheckRecord(data) {
  return request({
    url: '/zayy/checkRecord',
    method: 'put',
    data: data
  })
}

// 删除巡检记录
export function delCheckRecord(id) {
  return request({
    url: '/zayy/checkRecord/' + id,
    method: 'delete'
  })
}
