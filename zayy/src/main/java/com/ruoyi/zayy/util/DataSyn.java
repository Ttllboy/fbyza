package com.ruoyi.zayy.util;

import com.ruoyi.zayy.controller.ADingLoginController;
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckRecordMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;


@Configuration
@EnableScheduling
@RequestMapping("/dataSyn")
public class DataSyn {

    @Autowired
    CheckRecordMapper checkRecordMapper;
    @Autowired
    CheckPlaceMapper checkPlaceMapper;
    @Autowired
    CheckUserMapper checkUserMapper;
    @Autowired
    ADingLoginController aDingLoginController;

    @Scheduled(cron = "0 0 22 * * ?")
    @PostMapping("/pushToInspector")
    public void pushToInspector() throws Exception {//每天22点推送给未巡检的巡检员
//        System.out.println("每天22点推送给未巡检的巡检员");
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate = cal1.getTime();
        Date endDate = new Date(startDate.getTime() + (24*60*60*1000));
        CheckRecord record = new CheckRecord();
        record.setStartDate(startDate);
        record.setEndDate(endDate);
        List<String> placeList = checkRecordMapper.selectDayAlreayXj(record);//当天巡检的巡检地点UUID
        List<Long> noCheckPlaceList = checkPlaceMapper.selectNoCheckPlaceList(placeList);//未巡检地点的ID
        List<CheckUser> checkUserList = checkUserMapper.selectCheckUserXj();//所有含有钉钉ID的巡检人员
//        System.out.println(placeList);
//        System.out.println(noCheckPlaceList);
        HashSet sendUserIdsSet = new HashSet();

        for (int i = 0; i < noCheckPlaceList.size(); i++) {//
//            System.out.println(includeUserdept(checkUserList, String.valueOf(noCheckPlaceList.get(i))));
            HashSet noCheckUserIdsSet = includeUserdept(checkUserList,String.valueOf(noCheckPlaceList.get(i)));
            List<Long> noCheckUserIds = new ArrayList<Long>(noCheckUserIdsSet);
            for (int j = 0; j < noCheckUserIds.size(); j++) {
                sendUserIdsSet.add(noCheckUserIds.get(j));
            }
        }
        List<Long> sendUserIds = new ArrayList<>(sendUserIdsSet);
//        System.out.println(sendUserIds);
        List<String> dingUserIds = checkUserMapper.selectDingIdByIds(sendUserIds);
//        System.out.println(dingUserIds);
        String dingUserIdsStr = dingUserIds.stream().collect(Collectors.joining(","));
//        System.out.println(dingUserIdsStr);
        aDingLoginController.sendDingMessage(dingUserIdsStr,"您尚未完成当日巡检");
    }

    public HashSet includeUserdept(List<CheckUser> checkUserList,String userDept){
        HashSet reSet = new HashSet();
        for (int i = 0; i < checkUserList.size(); i++) {
            CheckUser checkUser = checkUserList.get(i);
            String dept = checkUser.getUserDept();
            if(dept.contains(",")){ //查出来的多科室
                String[] placeIdsStr = dept.split(",");
                for (int j = 0; j < placeIdsStr.length; j++) {
                    if(userDept.equals(placeIdsStr[j])){
                        reSet.add(checkUser.getId());
                    }
                }
            }else {
                if(userDept.equals(dept)){
                    reSet.add(checkUser.getId());
                }
            }
        }
        return reSet;
    }

    @Scheduled(cron = "0 30 23 * * ?")
    @PostMapping("/sendToDirector")
    public void sendToDirector() throws Exception {
        System.out.println("每天23.30点推送给未巡检的巡检主任以及保卫科主任");
        Date now = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date startDate = cal1.getTime();
        Date endDate = new Date(startDate.getTime() + (24*60*60*1000));
        CheckRecord record = new CheckRecord();
        record.setStartDate(startDate);
        record.setEndDate(endDate);
        List<String> placeList = checkRecordMapper.selectDayAlreayXj(record);//当天巡检的巡检地点UUID
        List<Long> noCheckPlaceList = checkPlaceMapper.selectNoCheckPlaceList(placeList);//未巡检地点的ID
        List<CheckUser> checkUserList = checkUserMapper.selectCheckUserDirector();//所有含有钉钉ID的巡检科主任以及保卫科主任
//        System.out.println(placeList);
//        System.out.println(noCheckPlaceList);
        HashSet sendUserIdsSet = new HashSet();
        for (int i = 0; i < noCheckPlaceList.size(); i++) {//
//            System.out.println(includeUserdept(checkUserList, String.valueOf(noCheckPlaceList.get(i))));
            HashSet noCheckUserIdsSet = includeUserdept(checkUserList,String.valueOf(noCheckPlaceList.get(i)));
            List<Long> noCheckUserIds = new ArrayList<Long>(noCheckUserIdsSet);
            for (int j = 0; j < noCheckUserIds.size(); j++) {
                sendUserIdsSet.add(noCheckUserIds.get(j));
            }
        }
        List<Long> sendUserIds = new ArrayList<>(sendUserIdsSet);
        System.out.println(sendUserIds);
        List<String> dingUserIds = checkUserMapper.selectDingIdByIds(sendUserIds);
//        System.out.println(dingUserIds);
        String dingUserIdsStr = dingUserIds.stream().collect(Collectors.joining(","));
        System.out.println(dingUserIdsStr);
        aDingLoginController.sendDingMessage(dingUserIdsStr,"您的科室尚未完成当日巡检");
    }
}
