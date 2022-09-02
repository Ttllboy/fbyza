package com.ruoyi.zayy.domain;

import com.ruoyi.common.annotation.Excel;
import org.springframework.stereotype.Component;

@Component
public class RecordImg {
    /** ID */
    private Long id;

    /** 巡检记录ID */
    private Long recordId;

    /** 图片地址 */
    private String  itemImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }
}
