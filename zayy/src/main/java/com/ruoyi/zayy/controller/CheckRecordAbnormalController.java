package com.ruoyi.zayy.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.mapper.CheckRecordAbnormalMapper;
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
    @Autowired
    private CheckRecordAbnormalMapper checkRecordAbnormalMapper;

    /**
     * 查询巡检异常列表
     */
    @PreAuthorize("@ss.hasPermi('zayy:checkRecordAbnormal:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckRecordAbnormal checkRecordAbnormal)
    {
        System.out.println(checkRecordAbnormal);
        Integer roleId = checkRecordAbnormal.getRoleId();
        String userDept = checkRecordAbnormal.getSysUserDept();
        Long officeId = checkRecordAbnormal.getSysUserOfficeId();
        System.out.println(roleId);
        switch (roleId){
            case 1:  //超级管理员可以看到所有的巡检记录
            case 6:  //院领导可以看到所有巡检记录
            case 2: {  //管理员可以看到所有的巡检记录
                startPage();
                List<CheckRecordAbnormal> list = checkRecordAbnormalService.selectCheckRecordAbnormalList(checkRecordAbnormal);
                return getDataTable(list);
            }
            case 7:{  //巡检员可以看到自己的巡检记录
                startPage();
                List<CheckRecordAbnormal> list = checkRecordAbnormalMapper.selectAbnormalByUserId(checkRecordAbnormal);
                return getDataTable(list);
            }
            case 3:{  //科室主任可以看到他科室的巡检记录
                if(userDept.contains(",")){
                    String[] placeIdsStr = userDept.split(",");
                    Long[] placeIds = new Long[placeIdsStr.length];
                    for (int i = 0; i < placeIdsStr.length; i++) {
                        placeIds[i] = Long.valueOf(placeIdsStr[i]);
                    }
                    List<HashMap> maps = commonMapper.selectPlaceId(placeIds);
                    JSONArray placeIdArray = new JSONArray();

                    for (int i = 0; i < maps.size(); i++) {
                        placeIdArray.add(maps.get(i).get("place_id"));
                    }
                    checkRecordAbnormal.setPlaceIdArray(placeIdArray);
                    startPage();
                    List<CheckRecordAbnormal> list = checkRecordAbnormalMapper.selectAbnormalByKszrLists(checkRecordAbnormal);
                    return getDataTable(list);
                }else {
                    startPage();
                    List<CheckRecordAbnormal> list = checkRecordAbnormalMapper.selectAbnormalByKszr(checkRecordAbnormal);
                    return getDataTable(list);
                }
            }
            case 5:{  //职能科室可以看到自己所属科室的记录以及分发给他的科室
                if(userDept.equals("0")){
                    //如果科室为0就可以看到全部记录
                    startPage();
                    List<CheckRecordAbnormal> list = checkRecordAbnormalService.selectCheckRecordAbnormalList(checkRecordAbnormal);
                    return getDataTable(list);
                }else {
                    //否则就显示所属科室的全部记录以及分发给他的科室
                    if (userDept.contains(",")) {
                        String[] placeIdsStr = userDept.split(",");
                        Long[] placeIds = new Long[placeIdsStr.length];
                        for (int i = 0; i < placeIdsStr.length; i++) {
                            placeIds[i] = Long.valueOf(placeIdsStr[i]);
                        }
                        List<HashMap> maps = commonMapper.selectPlaceId(placeIds);
                        JSONArray placeIdArray = new JSONArray();

                        for (int i = 0; i < maps.size(); i++) {
                            placeIdArray.add(maps.get(i).get("place_id"));
                        }
                        checkRecordAbnormal.setPlaceIdArray(placeIdArray);
                        //筛选并集
                        startPage();
                        List<CheckRecordAbnormal> list = checkRecordAbnormalMapper.selectAbnormalByZnksLists(checkRecordAbnormal);
                        return getDataTable(list);
                    }else {
                        startPage();
                        List<CheckRecordAbnormal> list = checkRecordAbnormalMapper.selectAbnormalByZnks(checkRecordAbnormal);
                        return getDataTable(list);
                    }
                }
            }
        }
        return null;
//        startPage();
//        List<CheckRecordAbnormal> list = checkRecordAbnormalService.selectCheckRecordAbnormalList(checkRecordAbnormal);
//        return getDataTable(list);
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
//    public AjaxResult edit(@RequestBody JSONObject questJson)
    {
        System.out.println("进入巡检异常修改");
        System.out.println(checkRecordAbnormal);
        System.out.println(checkRecordAbnormal.getRoleId());
        System.out.println(checkRecordAbnormal.getAbnormalLev());
        if(!ifAuthorize(checkRecordAbnormal.getAbnormalLev(),checkRecordAbnormal.getRoleId())){
            return AjaxResult.error("无权限修改数据");
        }

//        CheckRecordAbnormal checkRecordAbnormal = new CheckRecordAbnormal();
//        checkRecordAbnormal.setRemark(questJson.getString("remark"));
//        checkRecordAbnormal.setId(questJson.getLong("id"));
//        checkRecordAbnormal.setUserId(questJson.getLong("userId"));
//        checkRecordAbnormal.setRecordTime(questJson.getDate("recordTime"));
//        checkRecordAbnormal.setCheckPlace(questJson.getString("checkPlace"));
//        checkRecordAbnormal.setRecordId(questJson.getString("recordId"));
//        checkRecordAbnormal.setCheckContent(questJson.getString("checkContent"));
//        checkRecordAbnormal.setRemarkSpecial(questJson.getString("remarkSpecial"));
//        checkRecordAbnormal.setAbnormalLev(questJson.getInteger("abnormalLev"));
//        checkRecordAbnormal.setEventType(questJson.getInteger("eventType"));
        return toAjax(checkRecordAbnormalService.updateCheckRecordAbnormal(checkRecordAbnormal));
    }

    //是否拥有修改权限
    public boolean ifAuthorize(Integer abnormalLev,Integer roleId){
        if(abnormalLev == 0){
            if(roleId == 3 || roleId == 4 || roleId == 5 || roleId == 6 || roleId == 1 || roleId == 2){
                System.out.println("黄色预警");
                return true;
            }else {
                return false;
            }
        }
        if(abnormalLev == 1){
            if(roleId == 3 || roleId == 4 || roleId == 5 || roleId == 6 || roleId == 1 || roleId == 2){
                System.out.println("橙色预警");
                return true;
            }else {
                return false;
            }
        }
        if(abnormalLev == 2){
            if(roleId == 3 || roleId == 4 || roleId == 5 || roleId == 6 || roleId == 1 || roleId == 2){
                System.out.println("红色预警");
                return true;
            }else {
                return false;
            }
        }
        return true;
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
    @Autowired
    CheckRecordMapper checkRecordMapper;
    @Autowired
    CommonMapper commonMapper;
    @PostMapping("/getDetail")
    public JSONObject getDetail(@RequestBody JSONObject questJson){
        String recordId = questJson.getString("recordId");
        CheckRecordAbnormal abnormal = new CheckRecordAbnormal();
        abnormal.setRecordId(recordId);
        List<HashMap> recordList = checkRecordAbnormalMapper.selectCheckAbnormalByRecordId(abnormal);
        HashMap map = recordList.get(0);

        CheckRecord record = new CheckRecord();
        record.setRecordId(recordId);
        List<HashMap> recordList2 = checkRecordMapper.selectCheckRecordByRecordId(record);
        HashMap recordMap = recordList2.get(0);

        List<HashMap> recordItems = commonMapper.selectRecordItems(recordId);
        List<HashMap> recordAbnormalItems = commonMapper.selectAbnormalItems(recordId);
        List<HashMap> recordImgs = commonMapper.selectRecordImgs((Long) recordMap.get("id"));
        List<HashMap> abnormalImgs = commonMapper.selectAbnormalImgs((Long) recordMap.get("id"));
        for (int i = 0; i < recordImgs.size(); i++) {
            HashMap map2 = recordImgs.get(i);
            String placeImg = (String) recordImgs.get(i).get("item_img");
            map2.put("item_img", RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        for (int i = 0; i < abnormalImgs.size(); i++) {
            HashMap map3 = abnormalImgs.get(i);
            String placeImg = (String) abnormalImgs.get(i).get("item_img");
            map3.put("item_img",RuoYiConfig.getApiImgUrl()+"/"+placeImg);
        }
        JSONObject reJson = new JSONObject();
        reJson.put("record",recordList.get(0));
        reJson.put("items",recordItems);
        reJson.put("abnormalItems",recordAbnormalItems);
        reJson.put("imgs",recordImgs);
        reJson.put("abnormalImgs",abnormalImgs);
        return reJson;
    }
}
