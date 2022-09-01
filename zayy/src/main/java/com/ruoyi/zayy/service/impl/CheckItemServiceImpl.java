package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckItemMapper;
import com.ruoyi.zayy.domain.CheckItem;
import com.ruoyi.zayy.service.ICheckItemService;

/**
 * 巡检项Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
@Service
public class CheckItemServiceImpl implements ICheckItemService 
{
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 查询巡检项
     * 
     * @param id 巡检项主键
     * @return 巡检项
     */
    @Override
    public CheckItem selectCheckItemById(Long id)
    {
        return checkItemMapper.selectCheckItemById(id);
    }

    /**
     * 查询巡检项列表
     * 
     * @param checkItem 巡检项
     * @return 巡检项
     */
    @Override
    public List<CheckItem> selectCheckItemList(CheckItem checkItem)
    {
        return checkItemMapper.selectCheckItemList(checkItem);
    }

    /**
     * 新增巡检项
     * 
     * @param checkItem 巡检项
     * @return 结果
     */
    @Override
    public int insertCheckItem(CheckItem checkItem)
    {
        return checkItemMapper.insertCheckItem(checkItem);
    }

    /**
     * 修改巡检项
     * 
     * @param checkItem 巡检项
     * @return 结果
     */
    @Override
    public int updateCheckItem(CheckItem checkItem)
    {
        return checkItemMapper.updateCheckItem(checkItem);
    }

    /**
     * 批量删除巡检项
     * 
     * @param ids 需要删除的巡检项主键
     * @return 结果
     */
    @Override
    public int deleteCheckItemByIds(Long[] ids)
    {
        return checkItemMapper.deleteCheckItemByIds(ids);
    }

    /**
     * 删除巡检项信息
     * 
     * @param id 巡检项主键
     * @return 结果
     */
    @Override
    public int deleteCheckItemById(Long id)
    {
        return checkItemMapper.deleteCheckItemById(id);
    }
}
