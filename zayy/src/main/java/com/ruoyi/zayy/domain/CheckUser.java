package com.ruoyi.zayy.domain;

import com.alibaba.fastjson2.JSONArray;
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

    public CheckUser(String userName, String userPassword, Integer userRole, String nickName, String userDept, Integer isStaff,String dingUserId,String jobNumber,String phone) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.nickName = nickName;
        this.userDept = userDept;
        this.isStaff = isStaff;
        this.dingUserId = dingUserId;
        this.jobNumber = jobNumber;
        this.phone = phone;
    }
    public CheckUser(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
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
    private Integer roleId;
    private Integer sysUserId;
    private JSONArray placeIdArray;
    private String  sysUserDept;
    private Integer isStaff;//1是员工
    private String  jobNumber;
    private String  phone;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Integer isStaff) {
        this.isStaff = isStaff;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public JSONArray getPlaceIdArray() {
        return placeIdArray;
    }

    public void setPlaceIdArray(JSONArray placeIdArray) {
        this.placeIdArray = placeIdArray;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getSysUserDept() {
        return sysUserDept;
    }

    public void setSysUserDept(String sysUserDept) {
        this.sysUserDept = sysUserDept;
    }

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
        return "CheckUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole=" + userRole +
                ", nickName='" + nickName + '\'' +
                ", userDept='" + userDept + '\'' +
                ", dingUserId='" + dingUserId + '\'' +
                ", officeId=" + officeId +
                ", roleId=" + roleId +
                ", sysUserId=" + sysUserId +
                ", placeIdArray=" + placeIdArray +
                ", sysUserDept='" + sysUserDept + '\'' +
                ", isStaff=" + isStaff +
                ", jobNumber='" + jobNumber + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
