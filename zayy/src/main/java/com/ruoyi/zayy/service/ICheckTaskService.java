package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.CheckTask;

/**
 * 巡检任务Service接口
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public interface ICheckTaskService 
{
    /**
     * 查询巡检任务
     * 
     * @param id 巡检任务主键
     * @return 巡检任务
     */
    public CheckTask selectCheckTaskById(Long id);

    /**
     * 查询巡检任务列表
     * 
     * @param checkTask 巡检任务
     * @return 巡检任务集合
     */
    public List<CheckTask> selectCheckTaskList(CheckTask checkTask);

    /**
     * 新增巡检任务
     * 
     * @param checkTask 巡检任务
     * @return 结果
     */
    public int insertCheckTask(CheckTask checkTask);

    /**
     * 修改巡检任务
     * 
     * @param checkTask 巡检任务
     * @return 结果
     */
    public int updateCheckTask(CheckTask checkTask);

    /**
     * 批量删除巡检任务
     * 
     * @param ids 需要删除的巡检任务主键集合
     * @return 结果
     */
    public int deleteCheckTaskByIds(Long[] ids);

    /**
     * 删除巡检任务信息
     * 
     * @param id 巡检任务主键
     * @return 结果
     */
    public int deleteCheckTaskById(Long id);
}
