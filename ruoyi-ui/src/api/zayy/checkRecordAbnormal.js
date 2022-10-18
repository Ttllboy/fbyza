import request from '@/utils/request'

// 查询巡检异常列表
export function listCheckRecordAbnormal(query) {
  return request({
    url: '/zayy/checkRecordAbnormal/list',
    method: 'get',
    params: query
  })
}

// 查询巡检异常详细
export function getCheckRecordAbnormal(id) {
  return request({
    url: '/zayy/checkRecordAbnormal/' + id,
    method: 'get'
  })
}

// 新增巡检异常
export function addCheckRecordAbnormal(data) {
  return request({
    url: '/zayy/checkRecordAbnormal',
    method: 'post',
    data: data
  })
}

// 修改巡检异常
export function updateCheckRecordAbnormal(data) {
  return request({
    url: '/zayy/checkRecordAbnormal',
    method: 'put',
    data: data
  })
}

// 删除巡检异常
export function delCheckRecordAbnormal(id) {
  return request({
    url: '/zayy/checkRecordAbnormal/' + id,
    method: 'delete'
  })
}

export function getDetail(data) {
  return request({
    url: '/zayy/checkRecordAbnormal/getDetail',
    method: 'post',
    data: data
  })
}