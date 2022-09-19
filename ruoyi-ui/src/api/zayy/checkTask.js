import request from '@/utils/request'

// 查询巡检任务列表
export function listCheckTask(query) {
  return request({
    url: '/zayy/checkTask/list',
    method: 'get',
    params: query
  })
}

// 查询巡检任务详细
export function getCheckTask(id) {
  return request({
    url: '/zayy/checkTask/' + id,
    method: 'get'
  })
}

// 新增巡检任务
export function addCheckTask(data) {
  return request({
    url: '/zayy/checkTask',
    method: 'post',
    data: data
  })
}

// 修改巡检任务
export function updateCheckTask(data) {
  return request({
    url: '/zayy/checkTask',
    method: 'put',
    data: data
  })
}

// 删除巡检任务
export function delCheckTask(id) {
  return request({
    url: '/zayy/checkTask/' + id,
    method: 'delete'
  })
}
