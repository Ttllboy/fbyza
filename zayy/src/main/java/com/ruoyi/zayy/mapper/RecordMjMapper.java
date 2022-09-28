package com.ruoyi.zayy.mapper;

import java.util.List;
import com.ruoyi.zayy.domain.RecordMj;

/**
 * 门禁管理Mapper接口
 * 
 * @author ruoyi
 * @date 2022-09-27
 */
public interface RecordMjMapper 
{
    /**
     * 查询门禁管理
     * 
     * @param id 门禁管理主键
     * @return 门禁管理
     */
    public RecordMj selectRecordMjById(Long id);

    /**
     * 查询门禁管理列表
     * 
     * @param recordMj 门禁管理
     * @return 门禁管理集合
     */
    public List<RecordMj> selectRecordMjList(RecordMj recordMj);

    /**
     * 新增门禁管理
     * 
     * @param recordMj 门禁管理
     * @return 结果
     */
    public int insertRecordMj(RecordMj recordMj);

    /**
     * 修改门禁管理
     * 
     * @param recordMj 门禁管理
     * @return 结果
     */
    public int updateRecordMj(RecordMj recordMj);

    /**
     * 删除门禁管理
     * 
     * @param id 门禁管理主键
     * @return 结果
     */
    public int deleteRecordMjById(Long id);

    /**
     * 批量删除门禁管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecordMjByIds(Long[] ids);
}
