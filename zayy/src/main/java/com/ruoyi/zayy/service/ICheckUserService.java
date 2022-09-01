package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.CheckUser;

/**
 * 人员管理Service接口
 * 
 * @author jgkj
 * @date 2022-09-01
 */
public interface ICheckUserService 
{
    /**
     * 查询人员管理
     * 
     * @param id 人员管理主键
     * @return 人员管理
     */
    public CheckUser selectCheckUserById(Long id);

    /**
     * 查询人员管理列表
     * 
     * @param checkUser 人员管理
     * @return 人员管理集合
     */
    public List<CheckUser> selectCheckUserList(CheckUser checkUser);

    /**
     * 新增人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    public int insertCheckUser(CheckUser checkUser);

    /**
     * 修改人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    public int updateCheckUser(CheckUser checkUser);

    /**
     * 批量删除人员管理
     * 
     * @param ids 需要删除的人员管理主键集合
     * @return 结果
     */
    public int deleteCheckUserByIds(Long[] ids);

    /**
     * 删除人员管理信息
     * 
     * @param id 人员管理主键
     * @return 结果
     */
    public int deleteCheckUserById(Long id);
}
