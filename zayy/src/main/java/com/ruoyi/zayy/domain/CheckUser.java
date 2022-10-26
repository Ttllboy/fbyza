package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人员管理对象 check_user
 * 
 * @author jgkj
 * @date 2022-09-01
 */
public class CheckUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public CheckUser(String userName, String userPassword, Integer userRole, String nickName, String userDept) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.nickName = nickName;
        this.userDept = userDept;
    }

    public CheckUser() {
    }

    /** ID */
    private Long id;

    /** 巡检用户名 */
    @Excel(name = "巡检用户名")
    private String userName;

    /** 巡检用户密码 */
    @Excel(name = "巡检用户密码")
    private String userPassword;

    /** 巡检用户角色 */
    @Excel(name = "巡检用户角色")
    private Integer userRole;

    /** 巡检用户名称 */
    @Excel(name = "巡检用户名称")
    private String nickName;

    /** 巡检用户科室 */
    @Excel(name = "巡检用户科室")
    private String userDept;
    private String dingUserId;
    private Long officeId;

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setUserPassword(String userPassword) 
    {
        this.userPassword = userPassword;
    }

    public String getUserPassword() 
    {
        return userPassword;
    }
    public void setUserRole(Integer userRole) 
    {
        this.userRole = userRole;
    }

    public Integer getUserRole() 
    {
        return userRole;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setUserDept(String userDept)
    {
        this.userDept = userDept;
    }

    public String getUserDept()
    {
        return userDept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("userPassword", getUserPassword())
            .append("userRole", getUserRole())
            .append("nickName", getNickName())
            .append("userDept", getUserDept())
            .toString();
    }
}
