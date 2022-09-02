package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.CheckItemDept;

/**
 * 巡检配置Service接口
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
public interface ICheckItemDeptService 
{
    /**
     * 查询巡检配置
     * 
     * @param id 巡检配置主键
     * @return 巡检配置
     */
    public CheckItemDept selectCheckItemDeptById(Long id);

    /**
     * 查询巡检配置列表
     * 
     * @param checkItemDept 巡检配置
     * @return 巡检配置集合
     */
    public List<CheckItemDept> selectCheckItemDeptList(CheckItemDept checkItemDept);

    /**
     * 新增巡检配置
     * 
     * @param checkItemDept 巡检配置
     * @return 结果
     */
    public int insertCheckItemDept(CheckItemDept checkItemDept);

    /**
     * 修改巡检配置
     * 
     * @param checkItemDept 巡检配置
     * @return 结果
     */
    public int updateCheckItemDept(CheckItemDept checkItemDept);

    /**
     * 批量删除巡检配置
     * 
     * @param ids 需要删除的巡检配置主键集合
     * @return 结果
     */
    public int deleteCheckItemDeptByIds(Long[] ids);

    /**
     * 删除巡检配置信息
     * 
     * @param id 巡检配置主键
     * @return 结果
     */
    public int deleteCheckItemDeptById(Long id);
}
