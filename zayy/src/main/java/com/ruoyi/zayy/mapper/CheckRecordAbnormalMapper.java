package com.ruoyi.zayy.mapper;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.zayy.domain.CheckRecordAbnormal;
import org.apache.ibatis.annotations.Param;

/**
 * 巡检异常Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
public interface CheckRecordAbnormalMapper 
{
    /**
     * 查询巡检异常
     * 
     * @param id 巡检异常主键
     * @return 巡检异常
     */
    public CheckRecordAbnormal selectCheckRecordAbnormalById(Long id);

    /**
     * 查询巡检异常列表
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 巡检异常集合
     */
    public List<CheckRecordAbnormal> selectCheckRecordAbnormalList(CheckRecordAbnormal checkRecordAbnormal);
    public List<HashMap> selectCheckRecordAbnormalListCockpitApi(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAbnormalByUserId(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAbnormalByKszrLists(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAbnormalByKszr(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAbnormalByZnksLists(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAbnormalByZnks(CheckRecordAbnormal checkRecordAbnormal);
    public List<CheckRecordAbnormal> selectAll();
    public List<HashMap> selectCheckAbnormalList(CheckRecordAbnormal checkRecordAbnormal);
    public List<HashMap> selectCheckAbnormalByRecordId(CheckRecordAbnormal checkRecordAbnormal);
    public HashMap selectCheckAbnormalByRecordId2(CheckRecordAbnormal checkRecordAbnormal);
    public List<HashMap> selectAbnormalDay(CheckRecordAbnormal checkRecordAbnormal);

    /**
     * 新增巡检异常
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 结果
     */
    public int insertCheckRecordAbnormal(CheckRecordAbnormal checkRecordAbnormal);

    /**
     * 修改巡检异常
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 结果
     */
    public int updateCheckRecordAbnormal(CheckRecordAbnormal checkRecordAbnormal);
    public int updateCheckRecordAbnormalByRecordId(CheckRecordAbnormal checkRecordAbnormal);

    /**
     * 删除巡检异常
     * 
     * @param id 巡检异常主键
     * @return 结果
     */
    public int deleteCheckRecordAbnormalById(Long id);

    /**
     * 批量删除巡检异常
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckRecordAbnormalByIds(Long[] ids);
    public List<HashMap> selectByPlaceId(@Param("check_place")JSONArray check_place, @Param("eventType")Integer eventType);
    public List<HashMap> selectOfficeAbnormalList(@Param("check_place")JSONArray check_place, @Param("eventType")Integer eventType, @Param("officeId")Long officeId);
    public List<HashMap> selectOfficeAbnormal(@Param("check_place")String check_place, @Param("eventType")Integer eventType, @Param("officeId")Long officeId);
    public List<HashMap> selectOfficeList(@Param("record_id")String record_id, @Param("office_id")Integer office_id);
}
