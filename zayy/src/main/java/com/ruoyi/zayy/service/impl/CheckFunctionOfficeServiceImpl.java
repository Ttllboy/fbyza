package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckFunctionOfficeMapper;
import com.ruoyi.zayy.domain.CheckFunctionOffice;
import com.ruoyi.zayy.service.ICheckFunctionOfficeService;

/**
 * 职能科室Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-26
 */
@Service
public class CheckFunctionOfficeServiceImpl implements ICheckFunctionOfficeService 
{
    @Autowired
    private CheckFunctionOfficeMapper checkFunctionOfficeMapper;

    /**
     * 查询职能科室
     * 
     * @param id 职能科室主键
     * @return 职能科室
     */
    @Override
    public CheckFunctionOffice selectCheckFunctionOfficeById(Long id)
    {
        return checkFunctionOfficeMapper.selectCheckFunctionOfficeById(id);
    }

    /**
     * 查询职能科室列表
     * 
     * @param checkFunctionOffice 职能科室
     * @return 职能科室
     */
    @Override
    public List<CheckFunctionOffice> selectCheckFunctionOfficeList(CheckFunctionOffice checkFunctionOffice)
    {
        return checkFunctionOfficeMapper.selectCheckFunctionOfficeList(checkFunctionOffice);
    }

    /**
     * 新增职能科室
     * 
     * @param checkFunctionOffice 职能科室
     * @return 结果
     */
    @Override
    public int insertCheckFunctionOffice(CheckFunctionOffice checkFunctionOffice)
    {
        return checkFunctionOfficeMapper.insertCheckFunctionOffice(checkFunctionOffice);
    }

    /**
     * 修改职能科室
     * 
     * @param checkFunctionOffice 职能科室
     * @return 结果
     */
    @Override
    public int updateCheckFunctionOffice(CheckFunctionOffice checkFunctionOffice)
    {
        return checkFunctionOfficeMapper.updateCheckFunctionOffice(checkFunctionOffice);
    }

    /**
     * 批量删除职能科室
     * 
     * @param ids 需要删除的职能科室主键
     * @return 结果
     */
    @Override
    public int deleteCheckFunctionOfficeByIds(Long[] ids)
    {
        return checkFunctionOfficeMapper.deleteCheckFunctionOfficeByIds(ids);
    }

    /**
     * 删除职能科室信息
     * 
     * @param id 职能科室主键
     * @return 结果
     */
    @Override
    public int deleteCheckFunctionOfficeById(Long id)
    {
        return checkFunctionOfficeMapper.deleteCheckFunctionOfficeById(id);
    }
}
