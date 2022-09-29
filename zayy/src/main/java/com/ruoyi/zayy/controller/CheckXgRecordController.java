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
import com.ruoyi.zayy.domain.CheckXgRecord;
import com.ruoyi.zayy.service.ICheckXgRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更记录Controller
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/zayy/checkXgRecord")
public class CheckXgRecordController extends BaseController
{
    @Autowired
    private ICheckXgRecordService checkXgRecordService;

    /**
     * 查询巡更记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckXgRecord checkXgRecord)
    {
        startPage();
        List<CheckXgRecord> list = checkXgRecordService.selectCheckXgRecordList(checkXgRecord);
        return getDataTable(list);
    }

    /**
     * 导出巡更记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:export')")
    @Log(title = "巡更记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckXgRecord checkXgRecord)
    {
        List<CheckXgRecord> list = checkXgRecordService.selectCheckXgRecordList(checkXgRecord);
        ExcelUtil<CheckXgRecord> util = new ExcelUtil<CheckXgRecord>(CheckXgRecord.class);
        util.exportExcel(response, list, "巡更记录数据");
    }

    /**
     * 获取巡更记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkXgRecordService.selectCheckXgRecordById(id));
    }

    /**
     * 新增巡更记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:add')")
    @Log(title = "巡更记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckXgRecord checkXgRecord)
    {
        return toAjax(checkXgRecordService.insertCheckXgRecord(checkXgRecord));
    }

    /**
     * 修改巡更记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:edit')")
    @Log(title = "巡更记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckXgRecord checkXgRecord)
    {
        return toAjax(checkXgRecordService.updateCheckXgRecord(checkXgRecord));
    }

    /**
     * 删除巡更记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkXgRecord:remove')")
    @Log(title = "巡更记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkXgRecordService.deleteCheckXgRecordByIds(ids));
    }
}
