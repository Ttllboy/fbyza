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
import com.ruoyi.zayy.domain.CheckRecordAbnormal;
import com.ruoyi.zayy.service.ICheckRecordAbnormalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检异常Controller
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/zayy/checkRecordAbnormal")
public class CheckRecordAbnormalController extends BaseController
{
    @Autowired
    private ICheckRecordAbnormalService checkRecordAbnormalService;

    /**
     * 查询巡检异常列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckRecordAbnormal checkRecordAbnormal)
    {
        startPage();
        List<CheckRecordAbnormal> list = checkRecordAbnormalService.selectCheckRecordAbnormalList(checkRecordAbnormal);
        return getDataTable(list);
    }

    /**
     * 导出巡检异常列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:export')")
    @Log(title = "巡检异常", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckRecordAbnormal checkRecordAbnormal)
    {
        List<CheckRecordAbnormal> list = checkRecordAbnormalService.selectCheckRecordAbnormalList(checkRecordAbnormal);
        ExcelUtil<CheckRecordAbnormal> util = new ExcelUtil<CheckRecordAbnormal>(CheckRecordAbnormal.class);
        util.exportExcel(response, list, "巡检异常数据");
    }

    /**
     * 获取巡检异常详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkRecordAbnormalService.selectCheckRecordAbnormalById(id));
    }

    /**
     * 新增巡检异常
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:add')")
    @Log(title = "巡检异常", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckRecordAbnormal checkRecordAbnormal)
    {
        return toAjax(checkRecordAbnormalService.insertCheckRecordAbnormal(checkRecordAbnormal));
    }

    /**
     * 修改巡检异常
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:edit')")
    @Log(title = "巡检异常", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckRecordAbnormal checkRecordAbnormal)
    {
        return toAjax(checkRecordAbnormalService.updateCheckRecordAbnormal(checkRecordAbnormal));
    }

    /**
     * 删除巡检异常
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:remove')")
    @Log(title = "巡检异常", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkRecordAbnormalService.deleteCheckRecordAbnormalByIds(ids));
    }
}
