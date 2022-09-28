package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.RecordAjMapper;
import com.ruoyi.zayy.domain.RecordAj;
import com.ruoyi.zayy.service.IRecordAjService;

/**
 * 安检记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-28
 */
@Service
public class RecordAjServiceImpl implements IRecordAjService 
{
    @Autowired
    private RecordAjMapper recordAjMapper;

    /**
     * 查询安检记录
     * 
     * @param id 安检记录主键
     * @return 安检记录
     */
    @Override
    public RecordAj selectRecordAjById(Long id)
    {
        return recordAjMapper.selectRecordAjById(id);
    }

    /**
     * 查询安检记录列表
     * 
     * @param recordAj 安检记录
     * @return 安检记录
     */
    @Override
    public List<RecordAj> selectRecordAjList(RecordAj recordAj)
    {
        return recordAjMapper.selectRecordAjList(recordAj);
    }

    /**
     * 新增安检记录
     * 
     * @param recordAj 安检记录
     * @return 结果
     */
    @Override
    public int insertRecordAj(RecordAj recordAj)
    {
        return recordAjMapper.insertRecordAj(recordAj);
    }

    /**
     * 修改安检记录
     * 
     * @param recordAj 安检记录
     * @return 结果
     */
    @Override
    public int updateRecordAj(RecordAj recordAj)
    {
        return recordAjMapper.updateRecordAj(recordAj);
    }

    /**
     * 批量删除安检记录
     * 
     * @param ids 需要删除的安检记录主键
     * @return 结果
     */
    @Override
    public int deleteRecordAjByIds(Long[] ids)
    {
        return recordAjMapper.deleteRecordAjByIds(ids);
    }

    /**
     * 删除安检记录信息
     * 
     * @param id 安检记录主键
     * @return 结果
     */
    @Override
    public int deleteRecordAjById(Long id)
    {
        return recordAjMapper.deleteRecordAjById(id);
    }
}
