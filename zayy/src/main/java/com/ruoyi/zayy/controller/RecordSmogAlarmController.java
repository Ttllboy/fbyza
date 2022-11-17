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
import com.ruoyi.zayy.domain.RecordSmogAlarm;
import com.ruoyi.zayy.service.IRecordSmogAlarmService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 烟感报警Controller
 * 
 * @author ruoyi
 * @date 2022-11-15
 */
@RestController
@RequestMapping("/zayy/recordSmogAlarm")
public class RecordSmogAlarmController extends BaseController
{
    @Autowired
    private IRecordSmogAlarmService recordSmogAlarmService;

    /**
     * 查询烟感报警列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(RecordSmogAlarm recordSmogAlarm)
    {
        startPage();
        List<RecordSmogAlarm> list = recordSmogAlarmService.selectRecordSmogAlarmList(recordSmogAlarm);
        return getDataTable(list);
    }

    /**
     * 导出烟感报警列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:export')")
    @Log(title = "烟感报警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordSmogAlarm recordSmogAlarm)
    {
        List<RecordSmogAlarm> list = recordSmogAlarmService.selectRecordSmogAlarmList(recordSmogAlarm);
        ExcelUtil<RecordSmogAlarm> util = new ExcelUtil<RecordSmogAlarm>(RecordSmogAlarm.class);
        util.exportExcel(response, list, "烟感报警数据");
    }

    /**
     * 获取烟感报警详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(recordSmogAlarmService.selectRecordSmogAlarmById(id));
    }

    /**
     * 新增烟感报警
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:add')")
    @Log(title = "烟感报警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecordSmogAlarm recordSmogAlarm)
    {
        return toAjax(recordSmogAlarmService.insertRecordSmogAlarm(recordSmogAlarm));
    }

    /**
     * 修改烟感报警
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:edit')")
    @Log(title = "烟感报警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecordSmogAlarm recordSmogAlarm)
    {
        return toAjax(recordSmogAlarmService.updateRecordSmogAlarm(recordSmogAlarm));
    }

    /**
     * 删除烟感报警
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordSmogAlarm:remove')")
    @Log(title = "烟感报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(recordSmogAlarmService.deleteRecordSmogAlarmByIds(ids));
    }
}
