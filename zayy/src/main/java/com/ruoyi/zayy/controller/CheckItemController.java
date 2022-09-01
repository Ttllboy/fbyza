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
import com.ruoyi.zayy.domain.CheckItem;
import com.ruoyi.zayy.service.ICheckItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检项Controller
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/zayy/checkItem")
public class CheckItemController extends BaseController
{
    @Autowired
    private ICheckItemService checkItemService;

    /**
     * 查询巡检项列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckItem checkItem)
    {
        startPage();
        List<CheckItem> list = checkItemService.selectCheckItemList(checkItem);
        return getDataTable(list);
    }

    /**
     * 导出巡检项列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:export')")
    @Log(title = "巡检项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckItem checkItem)
    {
        List<CheckItem> list = checkItemService.selectCheckItemList(checkItem);
        ExcelUtil<CheckItem> util = new ExcelUtil<CheckItem>(CheckItem.class);
        util.exportExcel(response, list, "巡检项数据");
    }

    /**
     * 获取巡检项详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkItemService.selectCheckItemById(id));
    }

    /**
     * 新增巡检项
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:add')")
    @Log(title = "巡检项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckItem checkItem)
    {
        return toAjax(checkItemService.insertCheckItem(checkItem));
    }

    /**
     * 修改巡检项
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:edit')")
    @Log(title = "巡检项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckItem checkItem)
    {
        return toAjax(checkItemService.updateCheckItem(checkItem));
    }

    /**
     * 删除巡检项
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkItem:remove')")
    @Log(title = "巡检项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkItemService.deleteCheckItemByIds(ids));
    }
}
