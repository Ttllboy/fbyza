package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckFunctionOffice;

/**
 * 职能科室Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-26
 */
public interface CheckFunctionOfficeMapper 
{
    /**
     * 查询职能科室
     * 
     * @param id 职能科室主键
     * @return 职能科室
     */
    public CheckFunctionOffice selectCheckFunctionOfficeById(Long id);

    /**
     * 查询职能科室列表
     * 
     * @param checkFunctionOffice 职能科室
     * @return 职能科室集合
     */
    public List<CheckFunctionOffice> selectCheckFunctionOfficeList(CheckFunctionOffice checkFunctionOffice);

    /**
     * 新增职能科室
     * 
     * @param checkFunctionOffice 职能科室
     * @return 结果
     */
    public int insertCheckFunctionOffice(CheckFunctionOffice checkFunctionOffice);

    /**
     * 修改职能科室
     * 
     * @param checkFunctionOffice 职能科室
     * @return 结果
     */
    public int updateCheckFunctionOffice(CheckFunctionOffice checkFunctionOffice);

    /**
     * 删除职能科室
     * 
     * @param id 职能科室主键
     * @return 结果
     */
    public int deleteCheckFunctionOfficeById(Long id);

    /**
     * 批量删除职能科室
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckFunctionOfficeByIds(Long[] ids);
}
