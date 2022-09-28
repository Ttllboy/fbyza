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
import com.ruoyi.zayy.domain.RecordAj;
import com.ruoyi.zayy.service.IRecordAjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安检记录Controller
 * 
 * @author ruoyi
 * @date 2022-09-28
 */
@RestController
@RequestMapping("/zayy/recordAj")
public class RecordAjController extends BaseController
{
    @Autowired
    private IRecordAjService recordAjService;

    /**
     * 查询安检记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:list')")
    @GetMapping("/list")
    public TableDataInfo list(RecordAj recordAj)
    {
        startPage();
        List<RecordAj> list = recordAjService.selectRecordAjList(recordAj);
        return getDataTable(list);
    }

    /**
     * 导出安检记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:export')")
    @Log(title = "安检记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordAj recordAj)
    {
        List<RecordAj> list = recordAjService.selectRecordAjList(recordAj);
        ExcelUtil<RecordAj> util = new ExcelUtil<RecordAj>(RecordAj.class);
        util.exportExcel(response, list, "安检记录数据");
    }

    /**
     * 获取安检记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(recordAjService.selectRecordAjById(id));
    }

    /**
     * 新增安检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:add')")
    @Log(title = "安检记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecordAj recordAj)
    {
        return toAjax(recordAjService.insertRecordAj(recordAj));
    }

    /**
     * 修改安检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:edit')")
    @Log(title = "安检记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecordAj recordAj)
    {
        return toAjax(recordAjService.updateRecordAj(recordAj));
    }

    /**
     * 删除安检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:recordAj:remove')")
    @Log(title = "安检记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(recordAjService.deleteRecordAjByIds(ids));
    }
}
