package com.ruoyi.zayy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡更图片对象 record_img2
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
public class RecordImg2 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 巡更记录ID */
    @Excel(name = "巡更记录ID")
    private Long recordId;

    /** 巡更图片 */
    @Excel(name = "巡更图片")
    private String itemImg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setItemImg(String itemImg) 
    {
        this.itemImg = itemImg;
    }

    public String getItemImg() 
    {
        return itemImg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("recordId", getRecordId())
            .append("itemImg", getItemImg())
            .toString();
    }
}
