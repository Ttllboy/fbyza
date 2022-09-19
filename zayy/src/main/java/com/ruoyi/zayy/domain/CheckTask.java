package com.ruoyi.zayy.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检任务对象 check_task
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public class CheckTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String taskId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 科室地点ID */
    @Excel(name = "科室地点ID")
    private String deptId;

    /** 完成情况0未完成1已完成 */
    @Excel(name = "完成情况0未完成1已完成")
    private Integer isNot;

    /** 发布状态0未发布1已发布 */
    @Excel(name = "发布状态0未发布1已发布")
    private Integer taskType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setReleaseTime(Date releaseTime) 
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime() 
    {
        return releaseTime;
    }
    public void setDeadline(Date deadline) 
    {
        this.deadline = deadline;
    }

    public Date getDeadline() 
    {
        return deadline;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setIsNot(Integer isNot) 
    {
        this.isNot = isNot;
    }

    public Integer getIsNot() 
    {
        return isNot;
    }
    public void setTaskType(Integer taskType) 
    {
        this.taskType = taskType;
    }

    public Integer getTaskType() 
    {
        return taskType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("userId", getUserId())
            .append("releaseTime", getReleaseTime())
            .append("deadline", getDeadline())
            .append("finishTime", getFinishTime())
            .append("deptId", getDeptId())
            .append("isNot", getIsNot())
            .append("taskType", getTaskType())
            .toString();
    }
}
