package com.ruoyi.zayy.mapper;

import com.ruoyi.zayy.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface CommonMapper  {

    public List<HashMap> selectCheckItem(@Param("userId") Long userId);
    public List<HashMap> selectRecordItems(@Param("recordId") String recordId);
    public List<HashMap> selectAbnormalItems(@Param("recordId") String recordId);
    public List<HashMap> selectRecordImgs(@Param("recordId") Long recordId);
    public List<HashMap> selectAbnormalImgs(@Param("recordId") Long recordId);
    public HashMap selectDingUserId(@Param("dingUserId") String dingUserId);
    public void insertRecordImg(RecordImg recordImg);
    public void insertRecordImg2(RecordImg2 recordImg);
    public void insertRecordItem(RecordItem recordItem);
    public List<HashMap> selectPlaceId(Long[] ids);
    public int getRoleConfig();
    public int getCheckAbnormalAllCount();
    public int getCheckAbnormalAlreadyCount();
    public int getCheckAbnormalMonthAllCount(CheckRecordAbnormal checkRecordAbnormal);
    public int getCheckAbnormalMonthAlreadyCount(CheckRecordAbnormal checkRecordAbnormal);
    public int getCheckAbnormalDayAllCount(CheckRecordAbnormal checkRecordAbnormal);
    public int getCheckAbnormalDayAlreadyCount(CheckRecordAbnormal checkRecordAbnormal);
}
