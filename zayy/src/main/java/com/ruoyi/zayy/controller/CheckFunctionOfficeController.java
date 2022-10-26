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
import com.ruoyi.zayy.domain.CheckFunctionOffice;
import com.ruoyi.zayy.service.ICheckFunctionOfficeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职能科室Controller
 * 
 * @author ruoyi
 * @date 2022-10-26
 */
@RestController
@RequestMapping("/zayy/office")
public class CheckFunctionOfficeController extends BaseController
{
    @Autowired
    private ICheckFunctionOfficeService checkFunctionOfficeService;

    /**
     * 查询职能科室列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckFunctionOffice checkFunctionOffice)
    {
        startPage();
        List<CheckFunctionOffice> list = checkFunctionOfficeService.selectCheckFunctionOfficeList(checkFunctionOffice);
        return getDataTable(list);
    }

    /**
     * 导出职能科室列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:export')")
    @Log(title = "职能科室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckFunctionOffice checkFunctionOffice)
    {
        List<CheckFunctionOffice> list = checkFunctionOfficeService.selectCheckFunctionOfficeList(checkFunctionOffice);
        ExcelUtil<CheckFunctionOffice> util = new ExcelUtil<CheckFunctionOffice>(CheckFunctionOffice.class);
        util.exportExcel(response, list, "职能科室数据");
    }

    /**
     * 获取职能科室详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkFunctionOfficeService.selectCheckFunctionOfficeById(id));
    }

    /**
     * 新增职能科室
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:add')")
    @Log(title = "职能科室", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckFunctionOffice checkFunctionOffice)
    {
        return toAjax(checkFunctionOfficeService.insertCheckFunctionOffice(checkFunctionOffice));
    }

    /**
     * 修改职能科室
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:edit')")
    @Log(title = "职能科室", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckFunctionOffice checkFunctionOffice)
    {
        return toAjax(checkFunctionOfficeService.updateCheckFunctionOffice(checkFunctionOffice));
    }

    /**
     * 删除职能科室
     */
    @PreAuthorize("@ss.hasPermi('zayy:office:remove')")
    @Log(title = "职能科室", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkFunctionOfficeService.deleteCheckFunctionOfficeByIds(ids));
    }
}
