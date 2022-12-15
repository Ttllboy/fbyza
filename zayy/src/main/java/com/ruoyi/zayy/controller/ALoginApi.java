package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckPlace;
import com.ruoyi.zayy.domain.CheckUser;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import com.ruoyi.zayy.util.PinyinUtil;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/loginApi")
public class ALoginApi {

    public final static ExpiringMap<String,String> mobileCodeMap = ExpiringMap.builder()
            .expiration(360, TimeUnit.SECONDS)
            .expirationPolicy(ExpirationPolicy.CREATED)
            .variableExpiration()
            .build();

    @Autowired
    private CheckUserMapper checkUserMapper;
    @Autowired
    CheckPlaceMapper checkPlaceMapper;

    //用账号密码登录
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject jsonObject){
        JSONObject reJson = new JSONObject();
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String placeId = jsonObject.getString("placeId");
//        Integer loginType = jsonObject.getInteger("loginType");
        CheckUser user = new CheckUser();
        user.setUserName(userName);
        List<CheckUser> list = checkUserMapper.loginSelect(user);
        if(list.size() > 0){
            String password2 = list.get(0).getUserPassword();
            if(password.equals(password2)){
                String placeName = "";
                if(placeId != null){
                    if(placeId.length() > 0){
                        CheckPlace place = new CheckPlace();
                        place.setPlaceId(placeId);
                        List<CheckPlace> checkPlaces = checkPlaceMapper.selectCheckPlaceList(place);
                        if(checkPlaces.size() > 0){
                            placeName = checkPlaces.get(0).getPlaceName();
                        }
                    }
                }
                reJson.put("code",200);
                reJson.put("msg","登录成功");
                reJson.put("userName",list.get(0).getNickName());
                reJson.put("placeName",placeName);
                reJson.put("userId",list.get(0).getId());
                reJson.put("userRole",list.get(0).getUserRole());
                return reJson;
            }else{
                    reJson.put("code",500);
                    reJson.put("msg","账号或密码输入错误");
                    return reJson;
            }
        }else {
                reJson.put("code",500);
                reJson.put("msg","账号或密码输入错误");
                return reJson;
        }
    }

    @Autowired
    CommonMapper commonMapper;
    @Autowired
    ADingLoginController aDingLoginController;

    @PostMapping("/loginByNumber")//用员工工号登录
    public JSONObject loginByNumber(@RequestBody JSONObject jsonObject) throws Exception {
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String placeId = jsonObject.getString("placeId");
        String accessToken = aDingLoginController.gettoken();
        String code = jsonObject.getString("code");
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetUserInfo());
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        OapiV2UserGetuserinfoResponse rsp = client.execute(req, accessToken);
        String errmsg = rsp.getErrmsg();
        if (!errmsg.equals("ok")) {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 500);
            reJson.put("msg", "获取钉钉用户信息失败");
            return reJson;
        }
        System.out.println("地点ID:" + placeId);
        OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userGetByCodeResponse = rsp.getResult();
        String dingUserId = "";
        System.out.println(rsp.getErrmsg());
        try {
            dingUserId = userGetByCodeResponse.getUserid();
        } catch (Throwable throwable) {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 400);
            reJson.put("msg", throwable);
            return reJson;
        }
        JSONObject getUserInfo = aDingLoginController.getAvatar(dingUserId, accessToken);
        String name = getUserInfo.getString("name");
        name = aDingLoginController.clearBracket(name,'(',')');
        name = aDingLoginController.clearBracket(name,'（', '）');

        String jobNumber = getUserInfo.getString("job_number");
        String phone = getUserInfo.getString("mobile");
        System.out.println("员工工号:"+jobNumber+",员工姓名:"+name);
        System.out.println("输入的员工工号:"+userName+",输入的员工姓名:"+password);
        if(userName.equals(jobNumber)){
            System.out.println("员工工号相同");
            if(name.equals(password)){
                System.out.println("员工姓名相同");
            }else {
                JSONObject reJson = new JSONObject();
                reJson.put("code", 500);
                reJson.put("msg", "员工工号或姓名输入错误");
                return reJson;
            }
        }else {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 500);
            reJson.put("msg", "员工工号或姓名输入错误");
            return reJson;
        }
        System.out.println("员工工号与姓名匹配,验证通过");

        String avatar = getUserInfo.getString("avatar");
        String placeName = null;
        if (placeId != null) {
            if (placeId.length() > 0) {
                CheckPlace place = new CheckPlace();
                place.setPlaceId(placeId);
                List<CheckPlace> checkPlaces = checkPlaceMapper.selectCheckPlaceList(place);
                if (checkPlaces.size() > 0) {
                    placeName = checkPlaces.get(0).getPlaceName();
                }
            }
        }

        CheckUser user = checkUserMapper.selectUserByDingId(dingUserId);
        if(user == null){
            System.out.println("未查找到用户,新增一个");
            String dingUserId2 = insertCheckUser(getUserInfo);
            user = checkUserMapper.selectUserByDingId(dingUserId2);
        }
        System.out.println("地点名称:" + placeName);
        JSONObject reJson = new JSONObject();
        reJson.put("code",200);
        reJson.put("msg","登录成功");
        reJson.put("userName",name);
        reJson.put("placeName",placeName);
        reJson.put("userId",user.getId());
        reJson.put("userRole",user.getUserRole());
        reJson.put("dingUserId", dingUserId);
        reJson.put("avatar", avatar);
        System.out.println("登录返回信息:" + reJson);
        return reJson;
    }
    @Autowired
    PinyinUtil pinyinUtil;
    public String insertCheckUser(JSONObject jsonObject){
        String name = jsonObject.getString("name");
        name = aDingLoginController.clearBracket(name, '(', ')');
        name = aDingLoginController.clearBracket(name, '（', '）');
        name = pinyinUtil.toPinyin(name);
        String dingUserId = jsonObject.getString("userid");
        CheckUser checkUser = new CheckUser(name, "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
        try {
            checkUserMapper.insertCheckUser(checkUser);
        } catch (Throwable throwable1) {
            checkUser = new CheckUser(name + jsonObject.getString("job_number"), "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
            checkUserMapper.insertCheckUser(checkUser);
        }
        return dingUserId;
    }

////其他相关API:
//        // 1、查看剩余过期时间
//        map.getExpectedExpiration("kobe");
//        // 2、查看设置过期时间
//        map.getExpiration("curry");
//        // 3、设置过期时间
//        map.setExpiration("curry",5,TimeUnit.SECONDS);
//        // 4、重置过期时间
//        map.resetExpiration("curry");

    @PostMapping("/mobileSecurityCode")//发送验证码
    public JSONObject mobileSecurityCode(JSONObject questJson) throws Exception {
        String accessToken = aDingLoginController.gettoken();
        String code = questJson.getString("code");
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetUserInfo());
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        OapiV2UserGetuserinfoResponse rsp = client.execute(req, accessToken);
        String errmsg = rsp.getErrmsg();
        if (!errmsg.equals("ok")) {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 500);
            reJson.put("msg", "获取钉钉用户信息失败");
            return reJson;
        }

        OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userGetByCodeResponse = rsp.getResult();
        String dingUserId = "";
        System.out.println(rsp.getErrmsg());
        try {
            dingUserId = userGetByCodeResponse.getUserid();
        } catch (Throwable throwable) {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 400);
            reJson.put("msg", throwable);
            return reJson;
        }
        JSONObject getUserInfo = aDingLoginController.getAvatar(dingUserId, accessToken);
        String phone = getUserInfo.getString("mobile");
        String mobileCode = String.valueOf((int)((Math.random()*9+1)*1000));
        mobileCodeMap.put(phone,mobileCode);
        sendPhoneCode(phone,mobileCode);
        JSONObject reJson = new JSONObject();
        reJson.put("code", 200);
        reJson.put("msg", "已向手机号"+phone+"发送验证码,5分钟内有效");
        return reJson;
    }

    @PostMapping("/testCode")
    public void getCode(@RequestBody JSONObject questJson){
        String phone = questJson.getString("phone");
        String code = mobileCodeMap.get(phone);
        try{
            System.out.println("验证码:"+code+",剩余过期时间:"+mobileCodeMap.getExpectedExpiration(phone));

        }catch (Exception e){
//            System.out.println(e);
            System.out.println("手机号:"+phone+"的验证码已过期");
        }
    }

    @PostMapping("/sendPhoneCode")
    public void sendPhoneCode(String phone,String mobileCode) throws RemoteException {
        System.out.println("进入短信方法");
        // 短信
//        Sms sms = new Sms("http://172.16.2.100:9080/OpenMasService");
//        String[] destinationAddresses = new String[]{phone};
//        String message= mobileCode + "(智安巡检账号绑定验证码)，此验证码只用于绑定账号，提供给他人可能导致账号被他人绑定，请勿转发。";
//        String extendCode = "9"; //自定义扩展代码（模块）
//        String ApplicationID= "API-221129-R68u2Dst4";
//        String Password = "v06yAom81hDt";
//        //发送短信
//        String GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
//        System.out.println(GateWayid);
//        //获取上行短信 方法一
//        SmsMessage mo = sms.GetMessage(GateWayid);
//        if(mo !=null){
//            System.out.println(mo.toString());
//        }

    }


    @PostMapping("/test")
    public void test(@RequestBody JSONObject jsonObject){
        String str1 = jsonObject.getString("str1");
        String str2 = jsonObject.getString("str2");
        if(str1.equals(str2)){
            System.out.println(1);
        }else System.out.println(2);
    }

}
