package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckItem;
import org.springframework.stereotype.Component;

/**
 * 巡检项Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@Component
public interface CheckItemMapper 
{
    /**
     * 查询巡检项
     * 
     * @param id 巡检项主键
     * @return 巡检项
     */
    public CheckItem selectCheckItemById(Long id);

    /**
     * 查询巡检项列表
     * 
     * @param checkItem 巡检项
     * @return 巡检项集合
     */
    public List<CheckItem> selectCheckItemList(CheckItem checkItem);

    /**
     * 新增巡检项
     * 
     * @param checkItem 巡检项
     * @return 结果
     */
    public int insertCheckItem(CheckItem checkItem);

    /**
     * 修改巡检项
     * 
     * @param checkItem 巡检项
     * @return 结果
     */
    public int updateCheckItem(CheckItem checkItem);

    /**
     * 删除巡检项
     * 
     * @param id 巡检项主键
     * @return 结果
     */
    public int deleteCheckItemById(Long id);

    /**
     * 批量删除巡检项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckItemByIds(Long[] ids);
}
