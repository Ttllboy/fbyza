package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检地点对象 check_place
 * 
 * @author ruoyi
 * @date 2022-09-02
 */
public class CheckPlace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 巡检地点ID */
    @Excel(name = "巡检地点ID")
    private String placeId;

    /** 巡检地点名称 */
    @Excel(name = "巡检地点名称")
    private String placeName;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 巡检地点二维码 */
    @Excel(name = "巡检地点二维码")
    private String placeImg;
    //特殊科室
    private Integer specialOffice;

    public Integer getSpecialOffice() {
        return specialOffice;
    }

    public void setSpecialOffice(Integer specialOffice) {
        this.specialOffice = specialOffice;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlaceId(String placeId) 
    {
        this.placeId = placeId;
    }

    public String getPlaceId() 
    {
        return placeId;
    }
    public void setPlaceName(String placeName) 
    {
        this.placeName = placeName;
    }

    public String getPlaceName() 
    {
        return placeName;
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
    public void setPlaceImg(String placeImg) 
    {
        this.placeImg = placeImg;
    }

    public String getPlaceImg() 
    {
        return placeImg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("placeId", getPlaceId())
            .append("placeName", getPlaceName())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("placeImg", getPlaceImg())
            .toString();
    }
}
