package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.RecordAj;

/**
 * 安检记录Service接口
 * 
 * @author ruoyi
 * @date 2022-09-28
 */
public interface IRecordAjService 
{
    /**
     * 查询安检记录
     * 
     * @param id 安检记录主键
     * @return 安检记录
     */
    public RecordAj selectRecordAjById(Long id);

    /**
     * 查询安检记录列表
     * 
     * @param recordAj 安检记录
     * @return 安检记录集合
     */
    public List<RecordAj> selectRecordAjList(RecordAj recordAj);

    /**
     * 新增安检记录
     * 
     * @param recordAj 安检记录
     * @return 结果
     */
    public int insertRecordAj(RecordAj recordAj);

    /**
     * 修改安检记录
     * 
     * @param recordAj 安检记录
     * @return 结果
     */
    public int updateRecordAj(RecordAj recordAj);

    /**
     * 批量删除安检记录
     * 
     * @param ids 需要删除的安检记录主键集合
     * @return 结果
     */
    public int deleteRecordAjByIds(Long[] ids);

    /**
     * 删除安检记录信息
     * 
     * @param id 安检记录主键
     * @return 结果
     */
    public int deleteRecordAjById(Long id);
}
