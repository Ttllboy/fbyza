package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.RecordImg2;

/**
 * 巡更图片Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-29
 */
public interface RecordImg2Mapper 
{
    /**
     * 查询巡更图片
     * 
     * @param id 巡更图片主键
     * @return 巡更图片
     */
    public RecordImg2 selectRecordImg2ById(Long id);

    /**
     * 查询巡更图片列表
     * 
     * @param recordImg2 巡更图片
     * @return 巡更图片集合
     */
    public List<RecordImg2> selectRecordImg2List(RecordImg2 recordImg2);

    /**
     * 新增巡更图片
     * 
     * @param recordImg2 巡更图片
     * @return 结果
     */
    public int insertRecordImg2(RecordImg2 recordImg2);

    /**
     * 修改巡更图片
     * 
     * @param recordImg2 巡更图片
     * @return 结果
     */
    public int updateRecordImg2(RecordImg2 recordImg2);

    /**
     * 删除巡更图片
     * 
     * @param id 巡更图片主键
     * @return 结果
     */
    public int deleteRecordImg2ById(Long id);

    /**
     * 批量删除巡更图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecordImg2ByIds(Long[] ids);
}
