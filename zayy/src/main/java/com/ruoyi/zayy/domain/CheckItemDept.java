package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检配置对象 check_item_dept
 * 
 * @author ruoyi
 * @date 2022-09-01
 */
public class CheckItemDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private Long deptName;

    /** 巡检内容 */
    @Excel(name = "巡检内容")
    private Long itemName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptName(Long deptName) 
    {
        this.deptName = deptName;
    }

    public Long getDeptName() 
    {
        return deptName;
    }
    public void setItemName(Long itemName) 
    {
        this.itemName = itemName;
    }

    public Long getItemName() 
    {
        return itemName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptName", getDeptName())
            .append("itemName", getItemName())
            .toString();
    }
}
