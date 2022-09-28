package com.ruoyi.zayy.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.RecordMjMapper;
import com.ruoyi.zayy.domain.RecordMj;
import com.ruoyi.zayy.service.IRecordMjService;

/**
 * 门禁管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-27
 */
@Service
public class RecordMjServiceImpl implements IRecordMjService 
{
    @Autowired
    private RecordMjMapper recordMjMapper;

    /**
     * 查询门禁管理
     * 
     * @param id 门禁管理主键
     * @return 门禁管理
     */
    @Override
    public RecordMj selectRecordMjById(Long id)
    {
        return recordMjMapper.selectRecordMjById(id);
    }

    /**
     * 查询门禁管理列表
     * 
     * @param recordMj 门禁管理
     * @return 门禁管理
     */
    @Override
    public List<RecordMj> selectRecordMjList(RecordMj recordMj)
    {
        return recordMjMapper.selectRecordMjList(recordMj);
    }

    /**
     * 新增门禁管理
     * 
     * @param recordMj 门禁管理
     * @return 结果
     */
    @Override
    public int insertRecordMj(RecordMj recordMj)
    {
        recordMj.setCreateTime(DateUtils.getNowDate());
        return recordMjMapper.insertRecordMj(recordMj);
    }

    /**
     * 修改门禁管理
     * 
     * @param recordMj 门禁管理
     * @return 结果
     */
    @Override
    public int updateRecordMj(RecordMj recordMj)
    {
        return recordMjMapper.updateRecordMj(recordMj);
    }

    /**
     * 批量删除门禁管理
     * 
     * @param ids 需要删除的门禁管理主键
     * @return 结果
     */
    @Override
    public int deleteRecordMjByIds(Long[] ids)
    {
        return recordMjMapper.deleteRecordMjByIds(ids);
    }

    /**
     * 删除门禁管理信息
     * 
     * @param id 门禁管理主键
     * @return 结果
     */
    @Override
    public int deleteRecordMjById(Long id)
    {
        return recordMjMapper.deleteRecordMjById(id);
    }
}
