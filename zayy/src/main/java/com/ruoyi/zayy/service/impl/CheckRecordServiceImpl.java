package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckRecordMapper;
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.service.ICheckRecordService;

/**
 * 巡检记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@Service
public class CheckRecordServiceImpl implements ICheckRecordService 
{
    @Autowired
    private CheckRecordMapper checkRecordMapper;

    /**
     * 查询巡检记录
     * 
     * @param id 巡检记录主键
     * @return 巡检记录
     */
    @Override
    public CheckRecord selectCheckRecordById(Long id)
    {
        return checkRecordMapper.selectCheckRecordById(id);
    }

    /**
     * 查询巡检记录列表
     * 
     * @param checkRecord 巡检记录
     * @return 巡检记录
     */
    @Override
    public List<CheckRecord> selectCheckRecordList(CheckRecord checkRecord)
    {
        return checkRecordMapper.selectCheckRecordList(checkRecord);
    }

    /**
     * 新增巡检记录
     * 
     * @param checkRecord 巡检记录
     * @return 结果
     */
    @Override
    public int insertCheckRecord(CheckRecord checkRecord)
    {
        return checkRecordMapper.insertCheckRecord(checkRecord);
    }

    /**
     * 修改巡检记录
     * 
     * @param checkRecord 巡检记录
     * @return 结果
     */
    @Override
    public int updateCheckRecord(CheckRecord checkRecord)
    {
        return checkRecordMapper.updateCheckRecord(checkRecord);
    }

    /**
     * 批量删除巡检记录
     * 
     * @param ids 需要删除的巡检记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckRecordByIds(Long[] ids)
    {
        return checkRecordMapper.deleteCheckRecordByIds(ids);
    }

    /**
     * 删除巡检记录信息
     * 
     * @param id 巡检记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckRecordById(Long id)
    {
        return checkRecordMapper.deleteCheckRecordById(id);
    }
}
