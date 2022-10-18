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
 * @date 2022-10-18
 */
public class RecordAj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 安检机IP */
    @Excel(name = "安检机IP")
    private String securityIp;

    /** 报警类型 */
    @Excel(name = "报警类型")
    private Integer securityType;

    /** 图片base64 */
    @Excel(name = "图片base64")
    private String imgBase64;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date securityTime;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgPath;

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
    public void setSecurityType(Integer securityType) 
    {
        this.securityType = securityType;
    }

    public Integer getSecurityType() 
    {
        return securityType;
    }
    public void setImgBase64(String imgBase64) 
    {
        this.imgBase64 = imgBase64;
    }

    public String getImgBase64() 
    {
        return imgBase64;
    }
    public void setSecurityTime(Date securityTime) 
    {
        this.securityTime = securityTime;
    }

    public Date getSecurityTime() 
    {
        return securityTime;
    }
    public void setImgPath(String imgPath) 
    {
        this.imgPath = imgPath;
    }

    public String getImgPath() 
    {
        return imgPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("securityIp", getSecurityIp())
            .append("securityType", getSecurityType())
            .append("imgBase64", getImgBase64())
            .append("securityTime", getSecurityTime())
            .append("imgPath", getImgPath())
            .toString();
    }
}
