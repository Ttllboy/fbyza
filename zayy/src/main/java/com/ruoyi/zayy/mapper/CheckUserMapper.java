package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.CheckUser;

/**
 * 人员管理Mapper接口
 * 
 * @author jgkj
 * @date 2022-09-01
 */
public interface CheckUserMapper 
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
    public List<CheckUser> loginSelect(CheckUser checkUser);

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
     * 删除人员管理
     * 
     * @param id 人员管理主键
     * @return 结果
     */
    public int deleteCheckUserById(Long id);

    /**
     * 批量删除人员管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckUserByIds(Long[] ids);
}
