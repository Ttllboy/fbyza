import request from '@/utils/request'

// 查询巡检地点列表
export function listCheckPlace(query) {
  return request({
    url: '/zayy/checkPlace/list',
    method: 'get',
    params: query
  })
}

// 查询巡检地点详细
export function getCheckPlace(id) {
  return request({
    url: '/zayy/checkPlace/' + id,
    method: 'get'
  })
}

// 新增巡检地点
export function addCheckPlace(data) {
  return request({
    url: '/zayy/checkPlace',
    method: 'post',
    data: data
  })
}

// 修改巡检地点
export function updateCheckPlace(data) {
  return request({
    url: '/zayy/checkPlace',
    method: 'put',
    data: data
  })
}

// 删除巡检地点
export function delCheckPlace(id) {
  return request({
    url: '/zayy/checkPlace/' + id,
    method: 'delete'
  })
}
