package com.ruoyi.zayy.mapper;

import com.ruoyi.zayy.domain.CheckItemDept;
import com.ruoyi.zayy.domain.RecordImg;
import com.ruoyi.zayy.domain.RecordItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface CommonMapper {

    public List<HashMap> selectCheckItem(@Param("userId") Long userId);
    public void insertRecordImg(RecordImg recordImg);
    public void insertRecordItem(RecordItem recordItem);
}
