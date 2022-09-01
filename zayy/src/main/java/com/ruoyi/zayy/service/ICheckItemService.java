package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.CheckItem;

/**
 * 巡检项Service接口
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
public interface ICheckItemService 
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
     * 批量删除巡检项
     * 
     * @param ids 需要删除的巡检项主键集合
     * @return 结果
     */
    public int deleteCheckItemByIds(Long[] ids);

    /**
     * 删除巡检项信息
     * 
     * @param id 巡检项主键
     * @return 结果
     */
    public int deleteCheckItemById(Long id);
}
