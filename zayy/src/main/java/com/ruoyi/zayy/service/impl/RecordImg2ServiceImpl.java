package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.RecordImg2Mapper;
import com.ruoyi.zayy.domain.RecordImg2;
import com.ruoyi.zayy.service.IRecordImg2Service;

/**
 * 巡更图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
@Service
public class RecordImg2ServiceImpl implements IRecordImg2Service 
{
    @Autowired
    private RecordImg2Mapper recordImg2Mapper;

    /**
     * 查询巡更图片
     * 
     * @param id 巡更图片主键
     * @return 巡更图片
     */
    @Override
    public RecordImg2 selectRecordImg2ById(Long id)
    {
        return recordImg2Mapper.selectRecordImg2ById(id);
    }

    /**
     * 查询巡更图片列表
     * 
     * @param recordImg2 巡更图片
     * @return 巡更图片
     */
    @Override
    public List<RecordImg2> selectRecordImg2List(RecordImg2 recordImg2)
    {
        return recordImg2Mapper.selectRecordImg2List(recordImg2);
    }

    /**
     * 新增巡更图片
     * 
     * @param recordImg2 巡更图片
     * @return 结果
     */
    @Override
    public int insertRecordImg2(RecordImg2 recordImg2)
    {
        return recordImg2Mapper.insertRecordImg2(recordImg2);
    }

    /**
     * 修改巡更图片
     * 
     * @param recordImg2 巡更图片
     * @return 结果
     */
    @Override
    public int updateRecordImg2(RecordImg2 recordImg2)
    {
        return recordImg2Mapper.updateRecordImg2(recordImg2);
    }

    /**
     * 批量删除巡更图片
     * 
     * @param ids 需要删除的巡更图片主键
     * @return 结果
     */
    @Override
    public int deleteRecordImg2ByIds(Long[] ids)
    {
        return recordImg2Mapper.deleteRecordImg2ByIds(ids);
    }

    /**
     * 删除巡更图片信息
     * 
     * @param id 巡更图片主键
     * @return 结果
     */
    @Override
    public int deleteRecordImg2ById(Long id)
    {
        return recordImg2Mapper.deleteRecordImg2ById(id);
    }
}
