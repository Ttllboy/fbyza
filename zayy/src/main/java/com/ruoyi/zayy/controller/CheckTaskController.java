package com.ruoyi.zayy.controller;

import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckTaskMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.zayy.domain.CheckTask;
import com.ruoyi.zayy.service.ICheckTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检任务Controller
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/zayy/checkTask")
public class CheckTaskController extends BaseController
{
    @Autowired
    private ICheckTaskService checkTaskService;

    @Autowired
    private CheckUserMapper checkUserMapper;

    /**
     * 查询巡检任务列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckTask checkTask)
    {
        startPage();
        List<CheckTask> list = checkTaskService.selectCheckTaskList(checkTask);
        return getDataTable(list);
    }

    /**
     * 导出巡检任务列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:export')")
    @Log(title = "巡检任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckTask checkTask)
    {
        List<CheckTask> list = checkTaskService.selectCheckTaskList(checkTask);
        ExcelUtil<CheckTask> util = new ExcelUtil<CheckTask>(CheckTask.class);
        util.exportExcel(response, list, "巡检任务数据");
    }

    /**
     * 获取巡检任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkTaskService.selectCheckTaskById(id));
    }

    /**
     * 新增巡检任务
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:add')")
    @Log(title = "巡检任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckTask checkTask)
    {
        return toAjax(checkTaskService.insertCheckTask(checkTask));
    }

    /**
     * 修改巡检任务
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:edit')")
    @Log(title = "巡检任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckTask checkTask)
    {
        return toAjax(checkTaskService.updateCheckTask(checkTask));
    }

    /**
     * 删除巡检任务
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkTask:remove')")
    @Log(title = "巡检任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkTaskService.deleteCheckTaskByIds(ids));
    }


    //发布任务
    @PostMapping("/releaseTask")
    public Integer releaseTask(@RequestBody JSONObject questJson){
        try{
            String userId = questJson.getString("userId");
            Date startDate = questJson.getDate("startDate");
            Date nowDate = new Date();
            Long startTime = startDate.getTime();
            Long nowTime = nowDate.getTime();
            Integer taskType;
            if(startTime < nowTime){
                //1已发布，0未发布
                taskType = 1;
            }else {
                taskType = 0;
            }
            Date endDate = questJson.getDate("endDate");
            if(userId.equals("0")){
                List<CheckUser> userList = checkUserMapper.selectCheckUserList(new CheckUser());
                for (int i = 0; i < userList.size(); i++) {
                    CheckUser user = userList.get(i);
                    String deptId = user.getUserDept();
                    if(!deptId.equals("")){
                        List<String> deptIds = Arrays.asList(deptId.split(","));
                        for (int j = 0; j < deptIds.size(); j++) {
                            CheckTask checkTask = new CheckTask();
                            String taskId = String.valueOf(UUID.randomUUID());
                            checkTask.setTaskId(taskId);
                            checkTask.setUserId(user.getId());
                            checkTask.setReleaseTime(startDate);
                            checkTask.setDeadline(endDate);
                            checkTask.setDeptId(deptIds.get(j));
                            checkTask.setIsNot(0);
                            checkTask.setTaskType(taskType);
                            checkTaskService.insertCheckTask(checkTask);
                        }
                    }
                }
            }else
            {
                List<String> userIds = Arrays.asList(userId.split(","));
                for (int i = 0; i < userIds.size(); i++) {
                    Long userId2 = Long.valueOf(userIds.get(i));
                    CheckUser user = checkUserMapper.selectCheckUserById(userId2);
                    String deptId = user.getUserDept();
                    if(!deptId.equals("")){
                        List<String> deptIds = Arrays.asList(deptId.split(","));
                        for (int j = 0; j < deptIds.size(); j++) {
                            CheckTask checkTask = new CheckTask();
                            String taskId = String.valueOf(UUID.randomUUID());
                            checkTask.setTaskId(taskId);
                            checkTask.setUserId(user.getId());
                            checkTask.setReleaseTime(startDate);
                            checkTask.setDeadline(endDate);
                            checkTask.setDeptId(deptIds.get(j));
                            checkTask.setIsNot(0);
                            checkTask.setTaskType(taskType);
                            checkTaskService.insertCheckTask(checkTask);
                        }
                    }
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Autowired
    private CheckTaskMapper checkTaskMapper;
    //按周期统计
    @PostMapping("/statsTask")
    public List<?> statsTask(@RequestBody JSONObject questJson){
        JSONObject reJson = new JSONObject();

        Date startDate = questJson.getDate("startDate");
        Date endDate = questJson.getDate("endDate");

        CheckTask task = new CheckTask();
        if(startDate != null){
            task.setStartDate(startDate);
        }
        if(endDate != null){
            task.setEndDate(endDate);
        }
        List<CheckTask> list = checkTaskMapper.selectCheckTaskStats(task);
        Integer checkTotal = list.size();

        Integer checkComplete = 0;
        for (int i = 0; i < list.size(); i++) {
            CheckTask task2 = list.get(i);
            Integer isNot = task2.getIsNot();
            if(isNot == 1){
                checkComplete++;
            }
        }
        String completePre = getChuFa(checkComplete,checkTotal);

        return null;
    }

    @PostMapping("/test")
    public List<String> getIntArray(String deptId){
        List<String> result = Arrays.asList(deptId.split(","));
        return result;
    }

    public String getChuFa(int a,int b){
        DecimalFormat df=new DecimalFormat("0.00");
        return df.format((float)a/b);
    }

}
