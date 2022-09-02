package com.ruoyi.zayy.domain;

import org.springframework.stereotype.Component;

@Component
public class RecordItem {
    /** ID */
    private Long id;

    /** 巡检记录ID */
    private String recordId;

    /** 图片地址 */
    private Long  itemId;

    //是否
    private Integer itemIf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemIf() {
        return itemIf;
    }

    public void setItemIf(Integer itemIf) {
        this.itemIf = itemIf;
    }
}
