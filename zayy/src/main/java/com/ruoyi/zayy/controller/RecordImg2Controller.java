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
import com.ruoyi.zayy.domain.RecordImg2;
import com.ruoyi.zayy.service.IRecordImg2Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更图片Controller
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/zayy/checkImg2")
public class RecordImg2Controller extends BaseController
{
    @Autowired
    private IRecordImg2Service recordImg2Service;

    /**
     * 查询巡更图片列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:list')")
    @GetMapping("/list")
    public TableDataInfo list(RecordImg2 recordImg2)
    {
        startPage();
        List<RecordImg2> list = recordImg2Service.selectRecordImg2List(recordImg2);
        return getDataTable(list);
    }

    /**
     * 导出巡更图片列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:export')")
    @Log(title = "巡更图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordImg2 recordImg2)
    {
        List<RecordImg2> list = recordImg2Service.selectRecordImg2List(recordImg2);
        ExcelUtil<RecordImg2> util = new ExcelUtil<RecordImg2>(RecordImg2.class);
        util.exportExcel(response, list, "巡更图片数据");
    }

    /**
     * 获取巡更图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(recordImg2Service.selectRecordImg2ById(id));
    }

    /**
     * 新增巡更图片
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:add')")
    @Log(title = "巡更图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RecordImg2 recordImg2)
    {
        return toAjax(recordImg2Service.insertRecordImg2(recordImg2));
    }

    /**
     * 修改巡更图片
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:edit')")
    @Log(title = "巡更图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RecordImg2 recordImg2)
    {
        return toAjax(recordImg2Service.updateRecordImg2(recordImg2));
    }

    /**
     * 删除巡更图片
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkImg2:remove')")
    @Log(title = "巡更图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(recordImg2Service.deleteRecordImg2ByIds(ids));
    }
}
