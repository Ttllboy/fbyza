package com.ruoyi.zayy.mapper;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.zayy.domain.CheckRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 巡检记录Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@Component
public interface CheckRecordMapper 
{
    /**
     * 查询巡检记录
     * 
     * @param id 巡检记录主键
     * @return 巡检记录
     */
    public CheckRecord selectCheckRecordById(Long id);

    /**
     * 查询巡检记录列表
     * 
     * @param checkRecord 巡检记录
     * @return 巡检记录集合
     */
    public List<CheckRecord> selectCheckRecordList(CheckRecord checkRecord);
    public List<HashMap> selectMonthRecordList(CheckRecord checkRecord);
    public List<CheckRecord> selectBackByUserId(CheckRecord checkRecord);
    public List<CheckRecord> selectBackByKszrLists(CheckRecord checkRecord);
    public List<CheckRecord> selectBackByKszr(CheckRecord checkRecord);
    public List<CheckRecord> selectBackByZnksLists(CheckRecord checkRecord);
    public List<CheckRecord> selectBackByZnks(CheckRecord checkRecord);
    public List<CheckRecord> selectCheckRecordDayAll(CheckRecord checkRecord);
    public List<HashMap> selectCheckRecordMonthAll(CheckRecord checkRecord);
    public List<CheckRecord> selectCheckRecordDayXjy(CheckRecord checkRecord);
    public List<HashMap> selectCheckRecordNameList(CheckRecord checkRecord);
    public List<HashMap> selectRecordKszrLists(@Param("check_place") JSONArray check_place);
    public List<HashMap> selectRecordKszrList(CheckRecord checkRecord);
    public List<HashMap> selectRecordAll();
    public List<HashMap> selectRecordZnksLists(@Param("check_place")JSONArray check_place, @Param("officeId")Long officeId);
    public List<HashMap> selectRecordZnksList(@Param("check_place")String check_place, @Param("officeId")Long officeId);
    public List<HashMap> selectCheckRecordByRecordId(CheckRecord checkRecord);
    public List<String> selectDayAlreayXj(CheckRecord checkRecord);

    /**
     * 新增巡检记录
     * 
     * @param checkRecord 巡检记录
     * @return 结果
     */
    public int insertCheckRecord(CheckRecord checkRecord);

    /**
     * 修改巡检记录
     * 
     * @param checkRecord 巡检记录
     * @return 结果
     */
    public int updateCheckRecord(CheckRecord checkRecord);

    /**
     * 删除巡检记录
     * 
     * @param id 巡检记录主键
     * @return 结果
     */
    public int deleteCheckRecordById(Long id);

    /**
     * 批量删除巡检记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckRecordByIds(Long[] ids);
}
