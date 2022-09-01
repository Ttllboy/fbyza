import request from '@/utils/request'

// 查询巡检配置列表
export function listCheckItemDept(query) {
  return request({
    url: '/zayy/checkItemDept/list',
    method: 'get',
    params: query
  })
}

// 查询巡检配置详细
export function getCheckItemDept(id) {
  return request({
    url: '/zayy/checkItemDept/' + id,
    method: 'get'
  })
}

// 新增巡检配置
export function addCheckItemDept(data) {
  return request({
    url: '/zayy/checkItemDept',
    method: 'post',
    data: data
  })
}

// 修改巡检配置
export function updateCheckItemDept(data) {
  return request({
    url: '/zayy/checkItemDept',
    method: 'put',
    data: data
  })
}

// 删除巡检配置
export function delCheckItemDept(id) {
  return request({
    url: '/zayy/checkItemDept/' + id,
    method: 'delete'
  })
}
