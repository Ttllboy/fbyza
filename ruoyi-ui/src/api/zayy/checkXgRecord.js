import request from '@/utils/request'

// 查询巡更记录列表
export function listCheckXgRecord(query) {
  return request({
    url: '/zayy/checkXgRecord/list',
    method: 'get',
    params: query
  })
}

// 查询巡更记录详细
export function getCheckXgRecord(id) {
  return request({
    url: '/zayy/checkXgRecord/' + id,
    method: 'get'
  })
}

// 新增巡更记录
export function addCheckXgRecord(data) {
  return request({
    url: '/zayy/checkXgRecord',
    method: 'post',
    data: data
  })
}

// 修改巡更记录
export function updateCheckXgRecord(data) {
  return request({
    url: '/zayy/checkXgRecord',
    method: 'put',
    data: data
  })
}

// 删除巡更记录
export function delCheckXgRecord(id) {
  return request({
    url: '/zayy/checkXgRecord/' + id,
    method: 'delete'
  })
}
