package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.stereotype.Component;

/**
 * 巡检项对象 check_item
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
public class CheckItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 巡检内容 */
    @Excel(name = "巡检内容")
    private String itemName;

    /** 是否通用 */
    @Excel(name = "是否通用")
    private Integer tiemCommon;
    //巡检项异常
    private Integer itemAbnormal;
    //巡检项异常等级
    private Integer abnormalLev;
    //特殊科室
    private Integer specialOffice;

    public Integer getSpecialOffice() {
        return specialOffice;
    }

    public void setSpecialOffice(Integer specialOffice) {
        this.specialOffice = specialOffice;
    }

    public Integer getItemAbnormal() {
        return itemAbnormal;
    }

    public void setItemAbnormal(Integer itemAbnormal) {
        this.itemAbnormal = itemAbnormal;
    }

    public Integer getAbnormalLev() {
        return abnormalLev;
    }

    public void setAbnormalLev(Integer abnormalLev) {
        this.abnormalLev = abnormalLev;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setTiemCommon(Integer tiemCommon) 
    {
        this.tiemCommon = tiemCommon;
    }

    public Integer getTiemCommon() 
    {
        return tiemCommon;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("itemName", getItemName())
            .append("tiemCommon", getTiemCommon())
            .toString();
    }
}
