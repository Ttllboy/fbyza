package com.ruoyi.zayy.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
}
