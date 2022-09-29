package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckXgRecordMapper;
import com.ruoyi.zayy.domain.CheckXgRecord;
import com.ruoyi.zayy.service.ICheckXgRecordService;

/**
 * 巡更记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
@Service
public class CheckXgRecordServiceImpl implements ICheckXgRecordService 
{
    @Autowired
    private CheckXgRecordMapper checkXgRecordMapper;

    /**
     * 查询巡更记录
     * 
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    @Override
    public CheckXgRecord selectCheckXgRecordById(Long id)
    {
        return checkXgRecordMapper.selectCheckXgRecordById(id);
    }

    /**
     * 查询巡更记录列表
     * 
     * @param checkXgRecord 巡更记录
     * @return 巡更记录
     */
    @Override
    public List<CheckXgRecord> selectCheckXgRecordList(CheckXgRecord checkXgRecord)
    {
        return checkXgRecordMapper.selectCheckXgRecordList(checkXgRecord);
    }

    /**
     * 新增巡更记录
     * 
     * @param checkXgRecord 巡更记录
     * @return 结果
     */
    @Override
    public int insertCheckXgRecord(CheckXgRecord checkXgRecord)
    {
        return checkXgRecordMapper.insertCheckXgRecord(checkXgRecord);
    }

    /**
     * 修改巡更记录
     * 
     * @param checkXgRecord 巡更记录
     * @return 结果
     */
    @Override
    public int updateCheckXgRecord(CheckXgRecord checkXgRecord)
    {
        return checkXgRecordMapper.updateCheckXgRecord(checkXgRecord);
    }

    /**
     * 批量删除巡更记录
     * 
     * @param ids 需要删除的巡更记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckXgRecordByIds(Long[] ids)
    {
        return checkXgRecordMapper.deleteCheckXgRecordByIds(ids);
    }

    /**
     * 删除巡更记录信息
     * 
     * @param id 巡更记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckXgRecordById(Long id)
    {
        return checkXgRecordMapper.deleteCheckXgRecordById(id);
    }
}
