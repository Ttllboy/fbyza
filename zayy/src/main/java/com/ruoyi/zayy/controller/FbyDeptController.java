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
import com.ruoyi.zayy.domain.FbyDept;
import com.ruoyi.zayy.service.IFbyDeptService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科室列表Controller
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/zayy/fbyDept")
public class FbyDeptController extends BaseController
{
    @Autowired
    private IFbyDeptService fbyDeptService;

    /**
     * 查询科室列表列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:list')")
    @GetMapping("/list")
    public TableDataInfo list(FbyDept fbyDept)
    {
        startPage();
        List<FbyDept> list = fbyDeptService.selectFbyDeptList(fbyDept);
        return getDataTable(list);
    }

    /**
     * 导出科室列表列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:export')")
    @Log(title = "科室列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FbyDept fbyDept)
    {
        List<FbyDept> list = fbyDeptService.selectFbyDeptList(fbyDept);
        ExcelUtil<FbyDept> util = new ExcelUtil<FbyDept>(FbyDept.class);
        util.exportExcel(response, list, "科室列表数据");
    }

    /**
     * 获取科室列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fbyDeptService.selectFbyDeptById(id));
    }

    /**
     * 新增科室列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:add')")
    @Log(title = "科室列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FbyDept fbyDept)
    {
        return toAjax(fbyDeptService.insertFbyDept(fbyDept));
    }

    /**
     * 修改科室列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:edit')")
    @Log(title = "科室列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FbyDept fbyDept)
    {
        return toAjax(fbyDeptService.updateFbyDept(fbyDept));
    }

    /**
     * 删除科室列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:fbyDept:remove')")
    @Log(title = "科室列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fbyDeptService.deleteFbyDeptByIds(ids));
    }
}
