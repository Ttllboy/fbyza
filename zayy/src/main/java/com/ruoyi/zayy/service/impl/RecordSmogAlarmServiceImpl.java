package com.ruoyi.zayy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zayy.mapper.RecordSmogAlarmMapper;
import com.ruoyi.zayy.domain.RecordSmogAlarm;
import com.ruoyi.zayy.service.IRecordSmogAlarmService;

/**
 * 烟感报警Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-15
 */
@Service
public class RecordSmogAlarmServiceImpl implements IRecordSmogAlarmService 
{
    @Autowired
    private RecordSmogAlarmMapper recordSmogAlarmMapper;

    /**
     * 查询烟感报警
     * 
     * @param id 烟感报警主键
     * @return 烟感报警
     */
    @Override
    public RecordSmogAlarm selectRecordSmogAlarmById(Long id)
    {
        return recordSmogAlarmMapper.selectRecordSmogAlarmById(id);
    }

    /**
     * 查询烟感报警列表
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 烟感报警
     */
    @Override
    public List<RecordSmogAlarm> selectRecordSmogAlarmList(RecordSmogAlarm recordSmogAlarm)
    {
        return recordSmogAlarmMapper.selectRecordSmogAlarmList(recordSmogAlarm);
    }

    /**
     * 新增烟感报警
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 结果
     */
    @Override
    public int insertRecordSmogAlarm(RecordSmogAlarm recordSmogAlarm)
    {
        return recordSmogAlarmMapper.insertRecordSmogAlarm(recordSmogAlarm);
    }

    /**
     * 修改烟感报警
     * 
     * @param recordSmogAlarm 烟感报警
     * @return 结果
     */
    @Override
    public int updateRecordSmogAlarm(RecordSmogAlarm recordSmogAlarm)
    {
        return recordSmogAlarmMapper.updateRecordSmogAlarm(recordSmogAlarm);
    }

    /**
     * 批量删除烟感报警
     * 
     * @param ids 需要删除的烟感报警主键
     * @return 结果
     */
    @Override
    public int deleteRecordSmogAlarmByIds(Long[] ids)
    {
        return recordSmogAlarmMapper.deleteRecordSmogAlarmByIds(ids);
    }

    /**
     * 删除烟感报警信息
     * 
     * @param id 烟感报警主键
     * @return 结果
     */
    @Override
    public int deleteRecordSmogAlarmById(Long id)
    {
        return recordSmogAlarmMapper.deleteRecordSmogAlarmById(id);
    }
}
