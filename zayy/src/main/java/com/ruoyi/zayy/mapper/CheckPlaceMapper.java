package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckPlace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 巡检地点Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
@Component
public interface CheckPlaceMapper 
{
    /**
     * 查询巡检地点
     * 
     * @param id 巡检地点主键
     * @return 巡检地点
     */
    public CheckPlace selectCheckPlaceById(Long id);
    public String selectCheckPlaceByPlaceId(@Param("placeId") String placeId);
    public Long selectCheckPlaceByPlaceId2(@Param("placeId") String placeId);
    public List<Long> selectNoCheckPlaceList(@Param("placeList")List<String> placeList);

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
     * 删除巡检地点
     * 
     * @param id 巡检地点主键
     * @return 结果
     */
    public int deleteCheckPlaceById(Long id);

    /**
     * 批量删除巡检地点
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckPlaceByIds(Long[] ids);
}
