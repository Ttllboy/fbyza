import request from '@/utils/request'

// 查询职能科室列表
export function listOffice(query) {
  return request({
    url: '/zayy/office/list',
    method: 'get',
    params: query
  })
}

// 查询职能科室详细
export function getOffice(id) {
  return request({
    url: '/zayy/office/' + id,
    method: 'get'
  })
}

// 新增职能科室
export function addOffice(data) {
  return request({
    url: '/zayy/office',
    method: 'post',
    data: data
  })
}

// 修改职能科室
export function updateOffice(data) {
  return request({
    url: '/zayy/office',
    method: 'put',
    data: data
  })
}

// 删除职能科室
export function delOffice(id) {
  return request({
    url: '/zayy/office/' + id,
    method: 'delete'
  })
}
