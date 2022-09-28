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
import com.ruoyi.zayy.domain.RecordMj;
import com.ruoyi.zayy.service.IRecordMjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 门禁管理Controller
 * 
 * @author ruoyi
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/zayy/recordMj")
public class RecordMjController extends BaseController
{
    @Autowired
    private IRecordMjService recordMjService;

    /**
     * 查询门禁管理列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:list')")
    @GetMapping("/list")
    public TableDataInfo list(RecordMj recordMj)
    {
        startPage();
        List<RecordMj> list = recordMjService.selectRecordMjList(recordMj);
        return getDataTable(list);
    }

    /**
     * 导出门禁管理列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:export')")
    @Log(title = "门禁管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordMj recordMj)
    {
        List<RecordMj> list = recordMjService.selectRecordMjList(recordMj);
        ExcelUtil<RecordMj> util = new ExcelUtil<RecordMj>(RecordMj.class);
        util.exportExcel(response, list, "门禁管理数据");
    }

    /**
     * 获取门禁管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(recordMjService.selectRecordMjById(id));
    }

    /**
     * 新增门禁管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:add')")
    @Log(title = "门禁管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecordMj recordMj)
    {
        return toAjax(recordMjService.insertRecordMj(recordMj));
    }

    /**
     * 修改门禁管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:edit')")
    @Log(title = "门禁管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecordMj recordMj)
    {
        return toAjax(recordMjService.updateRecordMj(recordMj));
    }

    /**
     * 删除门禁管理
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordMj:remove')")
    @Log(title = "门禁管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(recordMjService.deleteRecordMjByIds(ids));
    }
}
