package com.ruoyi.zayy.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安检记录对象 record_aj
 * 
 * @author ruoyi
 * @date 2022-09-28
 */
public class RecordAj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 安检机IP */
    @Excel(name = "安检机IP")
    private String securityIp;

    /** 安检机编号 */
    @Excel(name = "安检机编号")
    private String securityNum;

    /** 图片 */
    @Excel(name = "图片")
    private String securityImg;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date securityTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSecurityIp(String securityIp) 
    {
        this.securityIp = securityIp;
    }

    public String getSecurityIp() 
    {
        return securityIp;
    }
    public void setSecurityNum(String securityNum) 
    {
        this.securityNum = securityNum;
    }

    public String getSecurityNum() 
    {
        return securityNum;
    }
    public void setSecurityImg(String securityImg) 
    {
        this.securityImg = securityImg;
    }

    public String getSecurityImg() 
    {
        return securityImg;
    }
    public void setSecurityTime(Date securityTime) 
    {
        this.securityTime = securityTime;
    }

    public Date getSecurityTime() 
    {
        return securityTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("securityIp", getSecurityIp())
            .append("securityNum", getSecurityNum())
            .append("securityImg", getSecurityImg())
            .append("securityTime", getSecurityTime())
            .toString();
    }
}
