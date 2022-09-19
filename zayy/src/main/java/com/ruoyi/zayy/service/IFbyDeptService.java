package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.FbyDept;

/**
 * 科室列表Service接口
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public interface IFbyDeptService 
{
    /**
     * 查询科室列表
     * 
     * @param id 科室列表主键
     * @return 科室列表
     */
    public FbyDept selectFbyDeptById(Long id);

    /**
     * 查询科室列表列表
     * 
     * @param fbyDept 科室列表
     * @return 科室列表集合
     */
    public List<FbyDept> selectFbyDeptList(FbyDept fbyDept);

    /**
     * 新增科室列表
     * 
     * @param fbyDept 科室列表
     * @return 结果
     */
    public int insertFbyDept(FbyDept fbyDept);

    /**
     * 修改科室列表
     * 
     * @param fbyDept 科室列表
     * @return 结果
     */
    public int updateFbyDept(FbyDept fbyDept);

    /**
     * 批量删除科室列表
     * 
     * @param ids 需要删除的科室列表主键集合
     * @return 结果
     */
    public int deleteFbyDeptByIds(Long[] ids);

    /**
     * 删除科室列表信息
     * 
     * @param id 科室列表主键
     * @return 结果
     */
    public int deleteFbyDeptById(Long id);
}
