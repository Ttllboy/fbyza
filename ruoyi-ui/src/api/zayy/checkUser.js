import request from '@/utils/request'

// 查询人员管理列表
export function listCheckUser(query) {
  return request({
    url: '/zayy/checkUser/list',
    method: 'get',
    params: query
  })
}

// 查询人员管理详细
export function getCheckUser(id) {
  return request({
    url: '/zayy/checkUser/' + id,
    method: 'get'
  })
}

// 新增人员管理
export function addCheckUser(data) {
  return request({
    url: '/zayy/checkUser',
    method: 'post',
    data: data
  })
}

// 修改人员管理
export function updateCheckUser(data) {
  return request({
    url: '/zayy/checkUser',
    method: 'put',
    data: data
  })
}

// 删除人员管理
export function delCheckUser(id) {
  return request({
    url: '/zayy/checkUser/' + id,
    method: 'delete'
  })
}
