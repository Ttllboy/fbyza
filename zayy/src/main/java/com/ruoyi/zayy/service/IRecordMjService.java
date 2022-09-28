package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.RecordMj;

/**
 * 门禁管理Service接口
 * 
 * @author ruoyi
 * @date 2022-09-27
 */
public interface IRecordMjService 
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
     * 批量删除门禁管理
     * 
     * @param ids 需要删除的门禁管理主键集合
     * @return 结果
     */
    public int deleteRecordMjByIds(Long[] ids);

    /**
     * 删除门禁管理信息
     * 
     * @param id 门禁管理主键
     * @return 结果
     */
    public int deleteRecordMjById(Long id);
}
