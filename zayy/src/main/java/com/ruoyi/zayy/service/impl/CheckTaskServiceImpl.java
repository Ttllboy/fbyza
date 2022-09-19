package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckTaskMapper;
import com.ruoyi.zayy.domain.CheckTask;
import com.ruoyi.zayy.service.ICheckTaskService;

/**
 * 巡检任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
@Service
public class CheckTaskServiceImpl implements ICheckTaskService 
{
    @Autowired
    private CheckTaskMapper checkTaskMapper;

    /**
     * 查询巡检任务
     * 
     * @param id 巡检任务主键
     * @return 巡检任务
     */
    @Override
    public CheckTask selectCheckTaskById(Long id)
    {
        return checkTaskMapper.selectCheckTaskById(id);
    }

    /**
     * 查询巡检任务列表
     * 
     * @param checkTask 巡检任务
     * @return 巡检任务
     */
    @Override
    public List<CheckTask> selectCheckTaskList(CheckTask checkTask)
    {
        return checkTaskMapper.selectCheckTaskList(checkTask);
    }

    /**
     * 新增巡检任务
     * 
     * @param checkTask 巡检任务
     * @return 结果
     */
    @Override
    public int insertCheckTask(CheckTask checkTask)
    {
        return checkTaskMapper.insertCheckTask(checkTask);
    }

    /**
     * 修改巡检任务
     * 
     * @param checkTask 巡检任务
     * @return 结果
     */
    @Override
    public int updateCheckTask(CheckTask checkTask)
    {
        return checkTaskMapper.updateCheckTask(checkTask);
    }

    /**
     * 批量删除巡检任务
     * 
     * @param ids 需要删除的巡检任务主键
     * @return 结果
     */
    @Override
    public int deleteCheckTaskByIds(Long[] ids)
    {
        return checkTaskMapper.deleteCheckTaskByIds(ids);
    }

    /**
     * 删除巡检任务信息
     * 
     * @param id 巡检任务主键
     * @return 结果
     */
    @Override
    public int deleteCheckTaskById(Long id)
    {
        return checkTaskMapper.deleteCheckTaskById(id);
    }
}
