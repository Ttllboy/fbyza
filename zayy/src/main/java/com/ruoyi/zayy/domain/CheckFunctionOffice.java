package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 职能科室对象 check_function_office
 * 
 * @author ruoyi
 * @date 2022-10-26
 */
public class CheckFunctionOffice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 职能科室名称 */
    @Excel(name = "职能科室名称")
    private String officeName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOfficeName(String officeName) 
    {
        this.officeName = officeName;
    }

    public String getOfficeName() 
    {
        return officeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("officeName", getOfficeName())
            .toString();
    }
}
