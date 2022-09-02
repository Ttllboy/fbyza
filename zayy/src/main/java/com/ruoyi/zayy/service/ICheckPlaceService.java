package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.CheckPlace;

/**
 * 巡检地点Service接口
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
public interface ICheckPlaceService 
{
    /**
     * 查询巡检地点
     * 
     * @param id 巡检地点主键
     * @return 巡检地点
     */
    public CheckPlace selectCheckPlaceById(Long id);

    /**
     * 查询巡检地点列表
     * 
     * @param checkPlace 巡检地点
     * @return 巡检地点集合
     */
    public List<CheckPlace> selectCheckPlaceList(CheckPlace checkPlace);

    /**
     * 新增巡检地点
     * 
     * @param checkPlace 巡检地点
     * @return 结果
     */
    public int insertCheckPlace(CheckPlace checkPlace);

    /**
     * 修改巡检地点
     * 
     * @param checkPlace 巡检地点
     * @return 结果
     */
    public int updateCheckPlace(CheckPlace checkPlace);

    /**
     * 批量删除巡检地点
     * 
     * @param ids 需要删除的巡检地点主键集合
     * @return 结果
     */
    public int deleteCheckPlaceByIds(Long[] ids);

    /**
     * 删除巡检地点信息
     * 
     * @param id 巡检地点主键
     * @return 结果
     */
    public int deleteCheckPlaceById(Long id);
}
