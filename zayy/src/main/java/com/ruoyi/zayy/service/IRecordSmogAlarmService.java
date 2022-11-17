package com.ruoyi.zayy.service;

import java.util.List;
import com.ruoyi.zayy.domain.RecordSmogAlarm;

/**
 * 烟感报警Service接口
 * 
 * @author ruoyi
 * @date 2022-11-15
 */
public interface IRecordSmogAlarmService 
{
    /**
     * 查询烟感报警
     * 
     * @param id 烟感报警主键
     * @return 烟感报警
     */
    public RecordSmogAlarm selectRecordSmogAlarmById(Long id);

    /**
     * 查询烟感报警列表
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 烟感报警集合
     */
    public List<RecordSmogAlarm> selectRecordSmogAlarmList(RecordSmogAlarm recordSmogAlarm);

    /**
     * 新增烟感报警
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 结果
     */
    public int insertRecordSmogAlarm(RecordSmogAlarm recordSmogAlarm);

    /**
     * 修改烟感报警
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 结果
     */
    public int updateRecordSmogAlarm(RecordSmogAlarm recordSmogAlarm);

    /**
     * 批量删除烟感报警
     * 
     * @param ids 需要删除的烟感报警主键集合
     * @return 结果
     */
    public int deleteRecordSmogAlarmByIds(Long[] ids);

    /**
     * 删除烟感报警信息
     * 
     * @param id 烟感报警主键
     * @return 结果
     */
    public int deleteRecordSmogAlarmById(Long id);
}
