package com.ruoyi.zayy.util;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.zayy.controller.ADingLoginController;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping("/asyncUtil")
public class AsyncUtil {
    @Autowired
    CheckUserMapper checkUserMapper;
    @Autowired
    CheckPlaceMapper checkPlaceMapper;
    @Autowired
    ADingLoginController aDingLoginController;
    @Autowired
    DataSyn dataSyn;


    @PostMapping("/sendAbnormalSyn")
    @Async("threadPoolTaskExecutor")
    public void sendAbnormalSyn(Integer abnormalLev,String checkPlace) throws Exception {//推送巡检异常事件
        System.out.println("abnormalLev:"+abnormalLev);
        switch (abnormalLev){
            case 0:{//黄色异常,推给科主任
//                System.out.println("黄色异常,推给科主任");
                CheckUser checkUser = new CheckUser();
                checkUser.setUserRole(2);
                List<CheckUser> list = checkUserMapper.selectCheckUserList(checkUser);
                Long placeId = checkPlaceMapper.selectCheckPlaceByPlaceId2(checkPlace);
                HashSet userIdsSet = dataSyn.includeUserdept(list, String.valueOf(placeId));
                List<Long> userIds = new ArrayList<>(userIdsSet);
//                System.out.println(userIds);
                List<String> dingUserIds = checkUserMapper.selectDingIdByIds(userIds);
                String dingUserIdsStr = dingUserIds.stream().collect(Collectors.joining(","));
//                System.out.println(dingUserIdsStr);
                String placeName = checkPlaceMapper.selectCheckPlaceByPlaceId(checkPlace);
//                System.out.println(placeName);
                String levName = getLevName(abnormalLev);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String nowDate = simpleDateFormat.format(date);
                String content = nowDate+placeName + "发生" + levName + "异常事件";
//                System.out.println(content);
                aDingLoginController.sendDingMessage(dingUserIdsStr,content);
                break;
            }
            case 1:{//橙色异常,推给科主任以及职能科室
//                System.out.println("橙色异常,推给科主任以及职能科室");
                List<CheckUser> list = checkUserMapper.selectCheckUserManager();
                List<CheckUser> list2 = checkUserMapper.selectZnksUserdept();
                Long placeId = checkPlaceMapper.selectCheckPlaceByPlaceId2(checkPlace);
                HashSet userIdsSet = dataSyn.includeUserdept(list, String.valueOf(placeId));
                for (int i = 0; i < list2.size(); i++) {
                    userIdsSet.add(list2.get(i).getId());
                }
                List<Long> userIds = new ArrayList<>(userIdsSet);
//                System.out.println(userIds);
                List<String> dingUserIds = checkUserMapper.selectDingIdByIds(userIds);
                String dingUserIdsStr = dingUserIds.stream().collect(Collectors.joining(","));
//                System.out.println(dingUserIdsStr);
                String placeName = checkPlaceMapper.selectCheckPlaceByPlaceId(checkPlace);
//                System.out.println(placeName);
                String levName = getLevName(abnormalLev);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String nowDate = simpleDateFormat.format(date);
                String content = nowDate+placeName + "发生" + levName + "异常事件";
//                System.out.println(content);
                aDingLoginController.sendDingMessage(dingUserIdsStr,content);
                break;
            } case 2:{//红色异常,推给科主任以及职能科室以及院领导
//                System.out.println("红色异常,推给科主任以及职能科室以及院领导");
                List<CheckUser> list = checkUserMapper.selectCheckUserAllManager();
                List<CheckUser> list2 = checkUserMapper.selectZnksUserdept();
                Long placeId = checkPlaceMapper.selectCheckPlaceByPlaceId2(checkPlace);
                HashSet userIdsSet = dataSyn.includeUserdept(list, String.valueOf(placeId));
                for (int i = 0; i < list2.size(); i++) {
                    userIdsSet.add(list2.get(i).getId());
                }
                List<Long> userIds = new ArrayList<>(userIdsSet);
//                System.out.println(userIds);
                List<String> dingUserIds = checkUserMapper.selectDingIdByIds(userIds);
                String dingUserIdsStr = dingUserIds.stream().collect(Collectors.joining(","));
//                System.out.println(dingUserIdsStr);
                String placeName = checkPlaceMapper.selectCheckPlaceByPlaceId(checkPlace);
//                System.out.println(placeName);
                String levName = getLevName(abnormalLev);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String nowDate = simpleDateFormat.format(date);
                String content = nowDate+placeName + "发生" + levName + "异常事件";
//                System.out.println(content);
                aDingLoginController.sendDingMessage(dingUserIdsStr,content);
                break;
            }
        }

    }
    public String getLevName(Integer abnormalLev){
        if(abnormalLev == 0){
            return "一般";
        }
        if(abnormalLev == 1){
            return "严重";
        }
        if(abnormalLev == 2){
            return "重大";
        }
        return null;
    }
}
