package com.ruoyi.zayy.mapper;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.zayy.domain.CheckTask;
import jdk.internal.org.objectweb.asm.Handle;

/**
 * 巡检任务Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public interface CheckTaskMapper 
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
    public List<HashMap> selectTaskListPage(CheckTask checkTask);
    public List<CheckTask> selectCheckTaskStats(CheckTask checkTask);

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
     * 删除巡检任务
     * 
     * @param id 巡检任务主键
     * @return 结果
     */
    public int deleteCheckTaskById(Long id);

    /**
     * 批量删除巡检任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckTaskByIds(Long[] ids);
}
