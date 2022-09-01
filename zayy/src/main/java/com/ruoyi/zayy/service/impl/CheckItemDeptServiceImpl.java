package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckItemDeptMapper;
import com.ruoyi.zayy.domain.CheckItemDept;
import com.ruoyi.zayy.service.ICheckItemDeptService;

/**
 * 巡检配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@Service
public class CheckItemDeptServiceImpl implements ICheckItemDeptService 
{
    @Autowired
    private CheckItemDeptMapper checkItemDeptMapper;

    /**
     * 查询巡检配置
     * 
     * @param id 巡检配置主键
     * @return 巡检配置
     */
    @Override
    public CheckItemDept selectCheckItemDeptById(Long id)
    {
        return checkItemDeptMapper.selectCheckItemDeptById(id);
    }

    /**
     * 查询巡检配置列表
     * 
     * @param checkItemDept 巡检配置
     * @return 巡检配置
     */
    @Override
    public List<CheckItemDept> selectCheckItemDeptList(CheckItemDept checkItemDept)
    {
        return checkItemDeptMapper.selectCheckItemDeptList(checkItemDept);
    }

    /**
     * 新增巡检配置
     * 
     * @param checkItemDept 巡检配置
     * @return 结果
     */
    @Override
    public int insertCheckItemDept(CheckItemDept checkItemDept)
    {
        return checkItemDeptMapper.insertCheckItemDept(checkItemDept);
    }

    /**
     * 修改巡检配置
     * 
     * @param checkItemDept 巡检配置
     * @return 结果
     */
    @Override
    public int updateCheckItemDept(CheckItemDept checkItemDept)
    {
        return checkItemDeptMapper.updateCheckItemDept(checkItemDept);
    }

    /**
     * 批量删除巡检配置
     * 
     * @param ids 需要删除的巡检配置主键
     * @return 结果
     */
    @Override
    public int deleteCheckItemDeptByIds(Long[] ids)
    {
        return checkItemDeptMapper.deleteCheckItemDeptByIds(ids);
    }

    /**
     * 删除巡检配置信息
     * 
     * @param id 巡检配置主键
     * @return 结果
     */
    @Override
    public int deleteCheckItemDeptById(Long id)
    {
        return checkItemDeptMapper.deleteCheckItemDeptById(id);
    }
}
