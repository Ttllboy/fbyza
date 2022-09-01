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
import com.ruoyi.zayy.domain.CheckItemDept;
import com.ruoyi.zayy.service.ICheckItemDeptService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检配置Controller
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/zayy/checkItemDept")
public class CheckItemDeptController extends BaseController
{
    @Autowired
    private ICheckItemDeptService checkItemDeptService;

    /**
     * 查询巡检配置列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckItemDept checkItemDept)
    {
        startPage();
        List<CheckItemDept> list = checkItemDeptService.selectCheckItemDeptList(checkItemDept);
        return getDataTable(list);
    }

    /**
     * 导出巡检配置列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:export')")
    @Log(title = "巡检配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckItemDept checkItemDept)
    {
        List<CheckItemDept> list = checkItemDeptService.selectCheckItemDeptList(checkItemDept);
        ExcelUtil<CheckItemDept> util = new ExcelUtil<CheckItemDept>(CheckItemDept.class);
        util.exportExcel(response, list, "巡检配置数据");
    }

    /**
     * 获取巡检配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkItemDeptService.selectCheckItemDeptById(id));
    }

    /**
     * 新增巡检配置
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:add')")
    @Log(title = "巡检配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckItemDept checkItemDept)
    {
        return toAjax(checkItemDeptService.insertCheckItemDept(checkItemDept));
    }

    /**
     * 修改巡检配置
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:edit')")
    @Log(title = "巡检配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckItemDept checkItemDept)
    {
        return toAjax(checkItemDeptService.updateCheckItemDept(checkItemDept));
    }

    /**
     * 删除巡检配置
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItemDept:remove')")
    @Log(title = "巡检配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkItemDeptService.deleteCheckItemDeptByIds(ids));
    }
}
