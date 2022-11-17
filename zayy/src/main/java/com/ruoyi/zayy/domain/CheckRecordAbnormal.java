package com.ruoyi.zayy.domain;

import java.util.Date;

import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检异常对象 check_record_abnormal
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
public class CheckRecordAbnormal extends BaseEntity
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

    /** 处理方法 */
    @Excel(name = "处理方法")
    private String handleMethod;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;
    @Excel(name = "异常备注")
    private String remark;
    @Excel(name = "异常特殊备注")
    private String remarkSpecial;
    private String handleFlow;
    private String functionOffice;
    private String handleCurrent;
    private String placeName;





    /** 异常等级 */
    @Excel(name = "异常等级")
    private Integer abnormalLev;

    /** 事件状态0处理中1已办结2超时未办 */
    @Excel(name = "事件状态0处理中1已办结2超时未办")
    private Integer eventType;

    private Date startDate;
    private Date endDate;

    private Integer roleId;
    private Integer sysUserId;
    private String  sysUserDept;
    private Long sysUserOfficeId;
    private JSONArray placeIdArray;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public JSONArray getPlaceIdArray() {
        return placeIdArray;
    }

    public void setPlaceIdArray(JSONArray placeIdArray) {
        this.placeIdArray = placeIdArray;
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

    public String getHandleFlow() {
        return handleFlow;
    }

    public void setHandleFlow(String handleFlow) {
        this.handleFlow = handleFlow;
    }

    public String getFunctionOffice() {
        return functionOffice;
    }

    public void setFunctionOffice(String functionOffice) {
        this.functionOffice = functionOffice;
    }

    public String getHandleCurrent() {
        return handleCurrent;
    }

    public void setHandleCurrent(String handleCurrent) {
        this.handleCurrent = handleCurrent;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

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
    public void setHandleMethod(String handleMethod) 
    {
        this.handleMethod = handleMethod;
    }

    public String getHandleMethod() 
    {
        return handleMethod;
    }
    public void setHandleResult(String handleResult) 
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult() 
    {
        return handleResult;
    }
    public void setAbnormalLev(Integer abnormalLev) 
    {
        this.abnormalLev = abnormalLev;
    }

    public Integer getAbnormalLev() 
    {
        return abnormalLev;
    }
    public void setEventType(Integer eventType) 
    {
        this.eventType = eventType;
    }

    public Integer getEventType() 
    {
        return eventType;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkSpecial() {
        return remarkSpecial;
    }

    public void setRemarkSpecial(String remarkSpecial) {
        this.remarkSpecial = remarkSpecial;
    }

    @Override
    public String toString() {
        return "CheckRecordAbnormal{" +
                "id=" + id +
                ", userId=" + userId +
                ", recordTime=" + recordTime +
                ", checkPlace='" + checkPlace + '\'' +
                ", recordId='" + recordId + '\'' +
                ", checkContent='" + checkContent + '\'' +
                ", handleMethod='" + handleMethod + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", remark='" + remark + '\'' +
                ", remarkSpecial='" + remarkSpecial + '\'' +
                ", handleFlow='" + handleFlow + '\'' +
                ", functionOffice='" + functionOffice + '\'' +
                ", handleCurrent='" + handleCurrent + '\'' +
                ", abnormalLev=" + abnormalLev +
                ", eventType=" + eventType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", roleId=" + roleId +
                ", sysUserId=" + sysUserId +
                ", sysUserDept='" + sysUserDept + '\'' +
                ", sysUserOfficeId=" + sysUserOfficeId +
                '}';
    }
}
