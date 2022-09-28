package com.ruoyi.zayy.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 门禁管理对象 record_mj
 * 
 * @author ruoyi
 * @date 2022-09-27
 */
public class RecordMj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNo;

    /** 通道类型 */
    @Excel(name = "通道类型")
    private Long accessType;

    /** 人脸图片 */
    @Excel(name = "人脸图片")
    private String userFaceImg;

    /** 姓名 */
    @Excel(name = "姓名")
    private String userName;

    /** 身份证 */
    @Excel(name = "身份证")
    private String userIdcard;

    /** 体温 */
    @Excel(name = "体温")
    private String userTemp;

    /** 健康码 */
    @Excel(name = "健康码")
    private String userHealthcode;

    /** 开门方式 */
    @Excel(name = "开门方式")
    private Long openType;

    /** 开门结果 */
    @Excel(name = "开门结果")
    private Long openResult;

    /** 通过时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通过时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accessTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeviceNo(String deviceNo) 
    {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() 
    {
        return deviceNo;
    }
    public void setAccessType(Long accessType) 
    {
        this.accessType = accessType;
    }

    public Long getAccessType() 
    {
        return accessType;
    }
    public void setUserFaceImg(String userFaceImg) 
    {
        this.userFaceImg = userFaceImg;
    }

    public String getUserFaceImg() 
    {
        return userFaceImg;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setUserIdcard(String userIdcard) 
    {
        this.userIdcard = userIdcard;
    }

    public String getUserIdcard() 
    {
        return userIdcard;
    }
    public void setUserTemp(String userTemp) 
    {
        this.userTemp = userTemp;
    }

    public String getUserTemp() 
    {
        return userTemp;
    }
    public void setUserHealthcode(String userHealthcode) 
    {
        this.userHealthcode = userHealthcode;
    }

    public String getUserHealthcode() 
    {
        return userHealthcode;
    }
    public void setOpenType(Long openType) 
    {
        this.openType = openType;
    }

    public Long getOpenType() 
    {
        return openType;
    }
    public void setOpenResult(Long openResult) 
    {
        this.openResult = openResult;
    }

    public Long getOpenResult() 
    {
        return openResult;
    }
    public void setAccessTime(Date accessTime) 
    {
        this.accessTime = accessTime;
    }

    public Date getAccessTime() 
    {
        return accessTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceNo", getDeviceNo())
            .append("accessType", getAccessType())
            .append("userFaceImg", getUserFaceImg())
            .append("userName", getUserName())
            .append("userIdcard", getUserIdcard())
            .append("userTemp", getUserTemp())
            .append("userHealthcode", getUserHealthcode())
            .append("openType", getOpenType())
            .append("openResult", getOpenResult())
            .append("accessTime", getAccessTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
