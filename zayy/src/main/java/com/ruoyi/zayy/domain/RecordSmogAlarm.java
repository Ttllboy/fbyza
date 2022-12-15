package com.ruoyi.zayy.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 烟感报警对象 record_smog_alarm
 * 
 * @author ruoyi
 * @date 2022-11-15
 */
public class RecordSmogAlarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 事件ID */
    @Excel(name = "事件ID")
    private String eventId;

    /** 事件源编号 */
    @Excel(name = "事件源编号")
    private String srcIndex;

    /** 事件源类型 */
    @Excel(name = "事件源类型")
    private String srcType;

    /** 事件源名称 */
    @Excel(name = "事件源名称")
    private String srcName;

    /** 事件类型 */
    @Excel(name = "事件类型")
    private Long eventType;

    /** 事件状态 */
    @Excel(name = "事件状态")
    private Long status;

    /** 脉冲超时时间 */
    @Excel(name = "脉冲超时时间")
    private Long timeout;

    /** 事件发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "事件发生时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date happenTime;

    /** 事件发生的事件源父设备 */
    @Excel(name = "事件发生的事件源父设备")
    private String srcParentIdex;
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
    public void setEventId(String eventId) 
    {
        this.eventId = eventId;
    }

    public String getEventId() 
    {
        return eventId;
    }
    public void setSrcIndex(String srcIndex) 
    {
        this.srcIndex = srcIndex;
    }

    public String getSrcIndex() 
    {
        return srcIndex;
    }
    public void setSrcType(String srcType) 
    {
        this.srcType = srcType;
    }

    public String getSrcType() 
    {
        return srcType;
    }
    public void setSrcName(String srcName) 
    {
        this.srcName = srcName;
    }

    public String getSrcName() 
    {
        return srcName;
    }
    public void setEventType(Long eventType) 
    {
        this.eventType = eventType;
    }

    public Long getEventType() 
    {
        return eventType;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setTimeout(Long timeout) 
    {
        this.timeout = timeout;
    }

    public Long getTimeout() 
    {
        return timeout;
    }
    public void setHappenTime(Date happenTime) 
    {
        this.happenTime = happenTime;
    }

    public Date getHappenTime() 
    {
        return happenTime;
    }
    public void setSrcParentIdex(String srcParentIdex) 
    {
        this.srcParentIdex = srcParentIdex;
    }

    public String getSrcParentIdex() 
    {
        return srcParentIdex;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", eventId='" + eventId + '\'' +
                ", srcIndex='" + srcIndex + '\'' +
                ", srcType='" + srcType + '\'' +
                ", srcName='" + srcName + '\'' +
                ", eventType=" + eventType +
                ", status=" + status +
                ", timeout=" + timeout +
                ", happenTime=" + happenTime +
                ", srcParentIdex='" + srcParentIdex + '\'' +
                '}';
    }
}
