package com.ruoyi.zayy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.mapper.CheckRecordMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
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
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.service.ICheckRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检记录Controller
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@RestController
@RequestMapping("/zayy/checkRecord")
public class CheckRecordController extends BaseController
{
    @Autowired
    private ICheckRecordService checkRecordService;

    @Autowired
    CheckRecordMapper checkRecordMapper;

    @Autowired
    CommonMapper commonMapper;

    /**
     * 查询巡检记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckRecord checkRecord)
    {
        startPage();
        List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
        return getDataTable(list);
    }

    /**
     * 导出巡检记录列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:export')")
    @Log(title = "巡检记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckRecord checkRecord)
    {
        List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
        ExcelUtil<CheckRecord> util = new ExcelUtil<CheckRecord>(CheckRecord.class);
        util.exportExcel(response, list, "巡检记录数据");
    }

    /**
     * 获取巡检记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkRecordService.selectCheckRecordById(id));
    }

    /**
     * 新增巡检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:add')")
    @Log(title = "巡检记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckRecord checkRecord)
    {
        String recordId = String.valueOf(UUID.randomUUID());
        checkRecord.setRecordId(recordId);
        return toAjax(checkRecordService.insertCheckRecord(checkRecord));
    }

    /**
     * 修改巡检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:edit')")
    @Log(title = "巡检记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckRecord checkRecord)
    {
        return toAjax(checkRecordService.updateCheckRecord(checkRecord));
    }

    /**
     * 删除巡检记录
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecord:remove')")
    @Log(title = "巡检记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkRecordService.deleteCheckRecordByIds(ids));
    }

    @PostMapping("/getDetail")
    public JSONObject getDetail(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<HashMap> recordList = checkRecordMapper.selectCheckRecordByRecordId(record);
        HashMap map = recordList.get(0);
        List<HashMap> recordItems = commonMapper.selectRecordItems(recordId);
        List<HashMap> recordImgs = commonMapper.selectRecordImgs((Long) map.get("id"));
        for (int i = 0; i < recordImgs.size(); i++) {
            HashMap map2 = recordImgs.get(i);
            String placeImg = (String) recordImgs.get(i).get("item_img");
            map2.put("item_img", RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("record",recordList.get(0));
        reJson.put("items",recordItems);
        reJson.put("imgs",recordImgs);

        return reJson;
    }
}
