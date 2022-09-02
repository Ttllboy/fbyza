package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckRecord;
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
