package com.ruoyi.zayy.domain;

import java.util.Date;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
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

    /** 异常等级 */
    @Excel(name = "异常等级")
    private Integer abnormalLev;

    /** 事件状态0处理中1已办结2超时未办 */
    @Excel(name = "事件状态0处理中1已办结2超时未办")
    private Integer eventType;

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
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("recordTime", getRecordTime())
            .append("checkPlace", getCheckPlace())
            .append("recordId", getRecordId())
            .append("checkContent", getCheckContent())
            .append("handleMethod", getHandleMethod())
            .append("handleResult", getHandleResult())
            .append("abnormalLev", getAbnormalLev())
            .append("eventType", getEventType())
            .toString();
    }
}
