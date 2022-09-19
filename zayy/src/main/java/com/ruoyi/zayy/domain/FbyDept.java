package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科室列表对象 fby_dept
 * 
 * @author ruoyi
 * @date 2022-09-19
 */
public class FbyDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String deptName;

    /** 科室地点ID */
    @Excel(name = "科室地点ID")
    private String deptId;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 巡检地点二维码 */
    @Excel(name = "巡检地点二维码")
    private String deptImg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setDeptImg(String deptImg) 
    {
        this.deptImg = deptImg;
    }

    public String getDeptImg() 
    {
        return deptImg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptName", getDeptName())
            .append("deptId", getDeptId())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("deptImg", getDeptImg())
            .toString();
    }
}
