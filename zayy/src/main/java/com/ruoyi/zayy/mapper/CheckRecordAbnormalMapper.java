package com.ruoyi.zayy.mapper;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.zayy.domain.CheckRecordAbnormal;

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
    public List<HashMap> selectCheckAbnormalList(CheckRecordAbnormal checkRecordAbnormal);
    public List<HashMap> selectCheckAbnormalByRecordId(CheckRecordAbnormal checkRecordAbnormal);
    public HashMap selectCheckAbnormalByRecordId2(CheckRecordAbnormal checkRecordAbnormal);

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
}
