package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.FbyDeptMapper;
import com.ruoyi.zayy.domain.FbyDept;
import com.ruoyi.zayy.service.IFbyDeptService;

/**
 * 科室列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@Service
public class FbyDeptServiceImpl implements IFbyDeptService 
{
    @Autowired
    private FbyDeptMapper fbyDeptMapper;

    /**
     * 查询科室列表
     * 
     * @param id 科室列表主键
     * @return 科室列表
     */
    @Override
    public FbyDept selectFbyDeptById(Long id)
    {
        return fbyDeptMapper.selectFbyDeptById(id);
    }

    /**
     * 查询科室列表列表
     * 
     * @param fbyDept 科室列表
     * @return 科室列表
     */
    @Override
    public List<FbyDept> selectFbyDeptList(FbyDept fbyDept)
    {
        return fbyDeptMapper.selectFbyDeptList(fbyDept);
    }

    /**
     * 新增科室列表
     * 
     * @param fbyDept 科室列表
     * @return 结果
     */
    @Override
    public int insertFbyDept(FbyDept fbyDept)
    {
        return fbyDeptMapper.insertFbyDept(fbyDept);
    }

    /**
     * 修改科室列表
     * 
     * @param fbyDept 科室列表
     * @return 结果
     */
    @Override
    public int updateFbyDept(FbyDept fbyDept)
    {
        return fbyDeptMapper.updateFbyDept(fbyDept);
    }

    /**
     * 批量删除科室列表
     * 
     * @param ids 需要删除的科室列表主键
     * @return 结果
     */
    @Override
    public int deleteFbyDeptByIds(Long[] ids)
    {
        return fbyDeptMapper.deleteFbyDeptByIds(ids);
    }

    /**
     * 删除科室列表信息
     * 
     * @param id 科室列表主键
     * @return 结果
     */
    @Override
    public int deleteFbyDeptById(Long id)
    {
        return fbyDeptMapper.deleteFbyDeptById(id);
    }
}
