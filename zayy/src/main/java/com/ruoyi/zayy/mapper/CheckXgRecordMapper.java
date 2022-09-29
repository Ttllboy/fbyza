package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckXgRecord;

/**
 * 巡更记录Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
public interface CheckXgRecordMapper 
{
    /**
     * 查询巡更记录
     * 
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    public CheckXgRecord selectCheckXgRecordById(Long id);

    /**
     * 查询巡更记录列表
     * 
     * @param checkXgRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<CheckXgRecord> selectCheckXgRecordList(CheckXgRecord checkXgRecord);

    /**
     * 新增巡更记录
     * 
     * @param checkXgRecord 巡更记录
     * @return 结果
     */
    public int insertCheckXgRecord(CheckXgRecord checkXgRecord);

    /**
     * 修改巡更记录
     * 
     * @param checkXgRecord 巡更记录
     * @return 结果
     */
    public int updateCheckXgRecord(CheckXgRecord checkXgRecord);

    /**
     * 删除巡更记录
     * 
     * @param id 巡更记录主键
     * @return 结果
     */
    public int deleteCheckXgRecordById(Long id);

    /**
     * 批量删除巡更记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckXgRecordByIds(Long[] ids);
}
