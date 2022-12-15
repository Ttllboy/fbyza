package com.ruoyi.zayy.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.domain.CheckRecordAbnormal;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
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
        Integer roleId = checkRecord.getRoleId();
        String userDept = checkRecord.getSysUserDept();
        switch (roleId){
            case 1:  //超级管理员可以看到所有的巡检记录
            case 6:  //院领导可以看到所有巡检记录
            case 2: {  //管理员可以看到所有的巡检记录
                startPage();
                List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
                return getDataTable(list);
            }
            case 7:{  //巡检员可以看到自己的巡检记录
                if(userDept.equals("0")){
                    startPage();
                    List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
                    return getDataTable(list);
                }else {
                    startPage();
                    List<CheckRecord> list = checkRecordMapper.selectBackByUserId(checkRecord);
                    return getDataTable(list);
                }

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
                    checkRecord.setPlaceIdArray(placeIdArray);
                    startPage();
                    List<CheckRecord> list = checkRecordMapper.selectBackByKszrLists(checkRecord);
                    return getDataTable(list);
            }else {
                    startPage();
                    List<CheckRecord> list = checkRecordMapper.selectBackByKszr(checkRecord);
                    return getDataTable(list);
                }
            }
            case 5:{  //职能科室可以看到自己所属科室的记录以及分发给他的科室
                if(userDept.equals("0")){
                    //如果科室为0就可以看到全部记录
                    startPage();
                    List<CheckRecord> list = checkRecordService.selectCheckRecordList(checkRecord);
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
                        checkRecord.setPlaceIdArray(placeIdArray);
                        //筛选并集
                        startPage();
                        List<CheckRecord> list = checkRecordMapper.selectBackByZnksLists(checkRecord);
                        return getDataTable(list);
                    }else {
                        startPage();
                        List<CheckRecord> list = checkRecordMapper.selectBackByZnks(checkRecord);
                        return getDataTable(list);
                    }
                }
            }
        }
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

    @Autowired
    private CheckPlaceMapper checkPlaceMapper;

    //获取日 & 月报表
    @PostMapping("/getForm")
    public JSONArray getForm(@RequestBody JSONObject questJson) throws ParseException {
        System.out.println("进入getForm方法");
        System.out.println(questJson);
//        Integer roleId = questJson.getInteger("roleId");
//        Long userId = questJson.getLong("userId");
//        String userDept = questJson.getString("userDept");
//        Long officeId = questJson.getLong("officeId");
        Integer formType = questJson.getInteger("formType");
        Date startDate = questJson.getDate("date");
        Date endDate = new Date(startDate.getTime() + (24 * 60 * 60 * 1000));
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setStartDate(startDate);
        checkRecord.setEndDate(endDate);
        JSONArray reArray = new JSONArray();
        if (formType == 0) {  //日报表
            List<CheckRecord> list = checkRecordMapper.selectCheckRecordDayAll(checkRecord);
            HashSet placeSet = new HashSet();
            for (int i = 0; i < list.size(); i++) {
                CheckRecord recordItem = list.get(i);
                JSONObject item = new JSONObject();
                placeSet.add(recordItem.getCheckPlace());
                item.put("checkPlace", recordItem.getCheckPlace());
                item.put("placeName", recordItem.getPlaceName());
                item.put("isCheck", 1);
                reArray.add(item);
            }
            List<CheckPlace> placeList = checkPlaceMapper.selectCheckPlaceList(new CheckPlace());
            for (int i = 0; i < placeList.size(); i++) {
                String placeId = placeList.get(i).getPlaceId();
                Integer setSize1 = placeSet.size();
                placeSet.add(placeId);
                Integer setSize2 = placeSet.size();
                if(setSize2 > setSize1){
                    JSONObject item = new JSONObject();
                    item.put("checkPlace", placeList.get(i).getPlaceId());
                    item.put("placeName", placeList.get(i).getPlaceName());
                    item.put("isCheck", 0);
                    reArray.add(item);
                }
            }
            return reArray;
//            switch (roleId) {
//                case 1:  //超级管理员可以看到所有的巡检报表
//                case 6:  //院领导可以看到所有巡检报表
//                case 2: {  //管理员可以看到所有的巡检报表
//                    List<CheckRecord> list = checkRecordMapper.selectCheckRecordDayAll(checkRecord);
//                    for (int i = 0; i < list.size(); i++) {
//                        CheckRecord recordItem = list.get(i);
//                        JSONObject item = new JSONObject();
//                        item.put("checkPlace",recordItem.getCheckPlace());
//                        item.put("placeName",recordItem.getPlaceName());
//                        item.put("isCheck",1);
//                        item.put("notCheck",0);
//                        reArray.add(item);
//                    }
//                    return reArray;
//                }
//                case 7:{  //巡检员可以看到自己的巡检记录
//                    checkRecord.setUserId(userId);
//                    List<CheckRecord> list = checkRecordMapper.selectCheckRecordDayXjy(checkRecord);
//                    for (int i = 0; i < list.size(); i++) {
//                        CheckRecord recordItem = list.get(i);
//                        JSONObject item = new JSONObject();
//                        item.put("checkPlace",recordItem.getCheckPlace());
//                        item.put("placeName",recordItem.getPlaceName());
//                        item.put("isCheck",1);
//                        item.put("notCheck",0);
//                        reArray.add(item);
//                    }
//                    return reArray;
//                }
//            }
        }else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String startDay = df.format(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)) - 1, 1);
            String firstDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("第一天："+firstDayOfMonth);

            //这里先设置要获取月份的下月的第一天
            calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)), 1);
            //这里将日期值减去一天，从而获取到要求的月份最后一天
//        calendar.add(Calendar.DATE, -1);
            String lastDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("最后一天："+lastDayOfMonth);

            startDate = df.parse(firstDayOfMonth);
            endDate = df.parse(lastDayOfMonth);
            checkRecord.setStartDate(startDate);
            checkRecord.setEndDate(endDate);
            List<HashMap> list = checkRecordMapper.selectCheckRecordMonthAll(checkRecord);
            HashSet placeSet = new HashSet();
            for (int i = 0; i < list.size(); i++) {
                HashMap recordItem = list.get(i);
                JSONObject item = new JSONObject();
                System.out.println(recordItem);
                placeSet.add(recordItem.get("check_place"));
                item.put("checkPlace", recordItem.get("check_place"));
                item.put("placeName", recordItem.get("place_name"));
                item.put("isCheck", recordItem.get("count(*)"));
                reArray.add(item);
            }
            List<CheckPlace> placeList = checkPlaceMapper.selectCheckPlaceList(new CheckPlace());
            for (int i = 0; i < placeList.size(); i++) {
                String placeId = placeList.get(i).getPlaceId();
                Integer setSize1 = placeSet.size();
                placeSet.add(placeId);
                Integer setSize2 = placeSet.size();
                if(setSize2 > setSize1){
                    JSONObject item = new JSONObject();
                    item.put("checkPlace", placeList.get(i).getPlaceId());
                    item.put("placeName", placeList.get(i).getPlaceName());
                    item.put("isCheck", 0);
                    reArray.add(item);
                }
            }
            return reArray;
        }
    }

    @PostMapping("/getRecordList")
    public List<HashMap> getRecordList(@RequestBody JSONObject questJson) throws Exception {//点击数字,查看月报表的记录list
        Date startDate = questJson.getDate("date");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = df.format(startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)) - 1, 1);
        String firstDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("第一天："+firstDayOfMonth);

        //这里先设置要获取月份的下月的第一天
        calendar.set(Integer.parseInt(startDay.substring(0,4)), Integer.parseInt(startDay.substring(5,7)), 1);
        //这里将日期值减去一天，从而获取到要求的月份最后一天
//        calendar.add(Calendar.DATE, -1);
        String lastDayOfMonth = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
//        System.out.println("最后一天："+lastDayOfMonth);

        startDate = df.parse(firstDayOfMonth);
        Date endDate = df.parse(lastDayOfMonth);
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setStartDate(startDate);
        checkRecord.setEndDate(endDate);
        return checkRecordMapper.selectMonthRecordList(checkRecord);
    }



}
