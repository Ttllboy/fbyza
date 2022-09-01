package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.service.ICheckUserService;

/**
 * 人员管理Service业务层处理
 * 
 * @author jgkj
 * @date 2022-09-01
 */
@Service
public class CheckUserServiceImpl implements ICheckUserService 
{
    @Autowired
    private CheckUserMapper checkUserMapper;

    /**
     * 查询人员管理
     * 
     * @param id 人员管理主键
     * @return 人员管理
     */
    @Override
    public CheckUser selectCheckUserById(Long id)
    {
        return checkUserMapper.selectCheckUserById(id);
    }

    /**
     * 查询人员管理列表
     * 
     * @param checkUser 人员管理
     * @return 人员管理
     */
    @Override
    public List<CheckUser> selectCheckUserList(CheckUser checkUser)
    {
        return checkUserMapper.selectCheckUserList(checkUser);
    }

    /**
     * 新增人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    @Override
    public int insertCheckUser(CheckUser checkUser)
    {
        return checkUserMapper.insertCheckUser(checkUser);
    }

    /**
     * 修改人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    @Override
    public int updateCheckUser(CheckUser checkUser)
    {
        return checkUserMapper.updateCheckUser(checkUser);
    }

    /**
     * 批量删除人员管理
     * 
     * @param ids 需要删除的人员管理主键
     * @return 结果
     */
    @Override
    public int deleteCheckUserByIds(Long[] ids)
    {
        return checkUserMapper.deleteCheckUserByIds(ids);
    }

    /**
     * 删除人员管理信息
     * 
     * @param id 人员管理主键
     * @return 结果
     */
    @Override
    public int deleteCheckUserById(Long id)
    {
        return checkUserMapper.deleteCheckUserById(id);
    }
}
