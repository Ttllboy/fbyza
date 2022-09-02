package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.service.ICheckPlaceService;

/**
 * 巡检地点Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@Service
public class CheckPlaceServiceImpl implements ICheckPlaceService 
{
    @Autowired
    private CheckPlaceMapper checkPlaceMapper;

    /**
     * 查询巡检地点
     * 
     * @param id 巡检地点主键
     * @return 巡检地点
     */
    @Override
    public CheckPlace selectCheckPlaceById(Long id)
    {
        return checkPlaceMapper.selectCheckPlaceById(id);
    }

    /**
     * 查询巡检地点列表
     * 
     * @param checkPlace 巡检地点
     * @return 巡检地点
     */
    @Override
    public List<CheckPlace> selectCheckPlaceList(CheckPlace checkPlace)
    {
        return checkPlaceMapper.selectCheckPlaceList(checkPlace);
    }

    /**
     * 新增巡检地点
     * 
     * @param checkPlace 巡检地点
     * @return 结果
     */
    @Override
    public int insertCheckPlace(CheckPlace checkPlace)
    {
        return checkPlaceMapper.insertCheckPlace(checkPlace);
    }

    /**
     * 修改巡检地点
     * 
     * @param checkPlace 巡检地点
     * @return 结果
     */
    @Override
    public int updateCheckPlace(CheckPlace checkPlace)
    {
        return checkPlaceMapper.updateCheckPlace(checkPlace);
    }

    /**
     * 批量删除巡检地点
     * 
     * @param ids 需要删除的巡检地点主键
     * @return 结果
     */
    @Override
    public int deleteCheckPlaceByIds(Long[] ids)
    {
        return checkPlaceMapper.deleteCheckPlaceByIds(ids);
    }

    /**
     * 删除巡检地点信息
     * 
     * @param id 巡检地点主键
     * @return 结果
     */
    @Override
    public int deleteCheckPlaceById(Long id)
    {
        return checkPlaceMapper.deleteCheckPlaceById(id);
    }
}
