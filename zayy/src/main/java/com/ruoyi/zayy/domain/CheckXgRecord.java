package com.ruoyi.zayy.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡更记录对象 check_xg_record
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
public class CheckXgRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 巡更员ID */
    @Excel(name = "巡更员ID")
    private Long userId;

    /** 巡更时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡更时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordTime;

    /** 巡更地点 */
    @Excel(name = "巡更地点")
    private String checkPlace;

    /** 巡更记录ID */
    @Excel(name = "巡更记录ID")
    private String recordId;

    /** 详情描述 */
    @Excel(name = "详情描述")
    private String checkContent;

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
