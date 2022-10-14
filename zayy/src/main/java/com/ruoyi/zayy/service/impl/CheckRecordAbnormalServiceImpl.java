package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckRecordAbnormalMapper;
import com.ruoyi.zayy.domain.CheckRecordAbnormal;
import com.ruoyi.zayy.service.ICheckRecordAbnormalService;

/**
 * 巡检异常Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
@Service
public class CheckRecordAbnormalServiceImpl implements ICheckRecordAbnormalService 
{
    @Autowired
    private CheckRecordAbnormalMapper checkRecordAbnormalMapper;

    /**
     * 查询巡检异常
     * 
     * @param id 巡检异常主键
     * @return 巡检异常
     */
    @Override
    public CheckRecordAbnormal selectCheckRecordAbnormalById(Long id)
    {
        return checkRecordAbnormalMapper.selectCheckRecordAbnormalById(id);
    }

    /**
     * 查询巡检异常列表
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 巡检异常
     */
    @Override
    public List<CheckRecordAbnormal> selectCheckRecordAbnormalList(CheckRecordAbnormal checkRecordAbnormal)
    {
        return checkRecordAbnormalMapper.selectCheckRecordAbnormalList(checkRecordAbnormal);
    }

    /**
     * 新增巡检异常
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 结果
     */
    @Override
    public int insertCheckRecordAbnormal(CheckRecordAbnormal checkRecordAbnormal)
    {
        return checkRecordAbnormalMapper.insertCheckRecordAbnormal(checkRecordAbnormal);
    }

    /**
     * 修改巡检异常
     * 
     * @param checkRecordAbnormal 巡检异常
     * @return 结果
     */
    @Override
    public int updateCheckRecordAbnormal(CheckRecordAbnormal checkRecordAbnormal)
    {
        return checkRecordAbnormalMapper.updateCheckRecordAbnormal(checkRecordAbnormal);
    }

    /**
     * 批量删除巡检异常
     * 
     * @param ids 需要删除的巡检异常主键
     * @return 结果
     */
    @Override
    public int deleteCheckRecordAbnormalByIds(Long[] ids)
    {
        return checkRecordAbnormalMapper.deleteCheckRecordAbnormalByIds(ids);
    }

    /**
     * 删除巡检异常信息
     * 
     * @param id 巡检异常主键
     * @return 结果
     */
    @Override
    public int deleteCheckRecordAbnormalById(Long id)
    {
        return checkRecordAbnormalMapper.deleteCheckRecordAbnormalById(id);
    }
}
