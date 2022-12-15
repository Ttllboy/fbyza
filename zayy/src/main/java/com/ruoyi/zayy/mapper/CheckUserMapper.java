package com.ruoyi.zayy.mapper;

import java.util.List;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.zayy.domain.CheckUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 人员管理Mapper接口
 * 
 * @author jgkj
 * @date 2022-09-01
 */
@Component
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
    public List<CheckUser> selectCheckUserAll();
    public List<CheckUser> selectCheckUserXj();
    public List<CheckUser> selectCheckUserDirector();
    public List<CheckUser> selectCheckUserManager();
    public List<CheckUser> selectZnksUserdept();
    public List<CheckUser> selectCheckUserAllManager();
    public List<CheckUser> selectBackByUserId(CheckUser checkUser);
    public List<CheckUser> selectBackByKszrList(CheckUser checkUser);
    public List<CheckUser> selectBackByKszr(CheckUser checkUser);
    public List<CheckUser> loginSelect(CheckUser checkUser);
    public int deleteSysuser(@Param("userNameArray") JSONArray userNameArray);
    public List<String> selectDingIdByIds(@Param("sendUserIds") List<Long> sendUserIds);
    public CheckUser selectUserByDingId(@Param("dingUserId")String dingUserId);

    /**
     * 新增人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    public int insertCheckUser(CheckUser checkUser);
    public long insertDingUser(CheckUser checkUser);

    /**
     * 修改人员管理
     * 
     * @param checkUser 人员管理
     * @return 结果
     */
    public int updateCheckUser(CheckUser checkUser);
    public int updateSysUser(SysUser sysUser);
    public int updateStaff(CheckUser checkUser);
    public int updateStaffMobile(CheckUser checkUser);

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
    public void deleteStaff();
    public List<CheckUser> selectBackByUserIds(Long[] ids);
}
