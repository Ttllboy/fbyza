import request from '@/utils/request'

// 查询巡检项列表
export function listCheckItem(query) {
  return request({
    url: '/zayy/checkItem/list',
    method: 'get',
    params: query
  })
}

// 查询巡检项详细
export function getCheckItem(id) {
  return request({
    url: '/zayy/checkItem/' + id,
    method: 'get'
  })
}

// 新增巡检项
export function addCheckItem(data) {
  return request({
    url: '/zayy/checkItem',
    method: 'post',
    data: data
  })
}

// 修改巡检项
export function updateCheckItem(data) {
  return request({
    url: '/zayy/checkItem',
    method: 'put',
    data: data
  })
}

// 删除巡检项
export function delCheckItem(id) {
  return request({
    url: '/zayy/checkItem/' + id,
    method: 'delete'
  })
}
