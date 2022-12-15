import request from '@/utils/request'

// 查询烟感报警列表
export function listRecordSmogAlarm(query) {
  return request({
    url: '/zayy/recordSmogAlarm/list',
    method: 'get',
    params: query
  })
}

// 查询烟感报警详细
export function getRecordSmogAlarm(id) {
  return request({
    url: '/zayy/recordSmogAlarm/' + id,
    method: 'get'
  })
}

// 新增烟感报警
export function addRecordSmogAlarm(data) {
  return request({
    url: '/zayy/recordSmogAlarm',
    method: 'post',
    data: data
  })
}

// 修改烟感报警
export function updateRecordSmogAlarm(data) {
  return request({
    url: '/zayy/recordSmogAlarm',
    method: 'put',
    data: data
  })
}

// 删除烟感报警
export function delRecordSmogAlarm(id) {
  return request({
    url: '/zayy/recordSmogAlarm/' + id,
    method: 'delete'
  })
}
