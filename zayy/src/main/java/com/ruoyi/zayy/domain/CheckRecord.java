package com.ruoyi.zayy.domain;

import java.util.Date;

import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检记录对象 check_record
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
public class CheckRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 巡检员ID */
    @Excel(name = "巡检员ID")
    private Long userId;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    /** 巡检地点 */
    @Excel(name = "巡检地点")
    private String checkPlace;

    /** 巡检记录ID */
    @Excel(name = "巡检记录ID")
    private String recordId;

    /** 详情描述 */
    @Excel(name = "详情描述")
    private String checkContent;
    //巡检类型
    private Integer checkType;

    private Integer roleId;
    private Integer sysUserId;
    private String  sysUserDept;
    private Long sysUserOfficeId;
    private JSONArray placeIdArray;
    private String placeName;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserDept() {
        return sysUserDept;
    }

    public void setSysUserDept(String sysUserDept) {
        this.sysUserDept = sysUserDept;
    }

    public Long getSysUserOfficeId() {
        return sysUserOfficeId;
    }

    public void setSysUserOfficeId(Long sysUserOfficeId) {
        this.sysUserOfficeId = sysUserOfficeId;
    }

    public JSONArray getPlaceIdArray() {
        return placeIdArray;
    }

    public void setPlaceIdArray(JSONArray placeIdArray) {
        this.placeIdArray = placeIdArray;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
    }
    public void setCheckPlace(String checkPlace) 
    {
        this.checkPlace = checkPlace;
    }

    public String getCheckPlace() 
    {
        return checkPlace;
    }
    public void setRecordId(String recordId) 
    {
        this.recordId = recordId;
    }

    public String getRecordId() 
    {
        return recordId;
    }
    public void setCheckContent(String checkContent) 
    {
        this.checkContent = checkContent;
    }

    public String getCheckContent() 
    {
        return checkContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("recordTime", getRecordTime())
            .append("checkPlace", getCheckPlace())
            .append("recordId", getRecordId())
            .append("checkContent", getCheckContent())
            .toString();
    }
}
