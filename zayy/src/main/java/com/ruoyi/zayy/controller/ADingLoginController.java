package com.ruoyi.zayy.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkoauth2_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.zayy.config.Config2RestTemplate;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import com.ruoyi.zayy.util.PinyinUtil;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Configuration
@EnableScheduling
@RestController
@RequestMapping("/ding")
public class ADingLoginController {

    public static String gettoken() throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//        System.out.println("token访问地址:" + RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getAccessToken());
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getAccessToken());
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(RuoYiConfig.getAppKey());
        request.setAppsecret(RuoYiConfig.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        System.out.println("gettoken(),response.getBody()"+response.getBody());
        String body = response.getBody();
        JSONObject bodyJson = JSONObject.parseObject(body);
        System.out.println("获取token成功:" + bodyJson.getString("access_token"));
        return bodyJson.getString("access_token");
//        com.aliyun.dingtalkoauth2_1_0.Client client = ADingLoginController.createClient();
//        com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest()
//                .setAppKey(RuoYiConfig.getAppKey())
//                .setAppSecret(RuoYiConfig.getAppSecret());
//        try {
//            GetAccessTokenResponse response =  client.getAccessToken(getAccessTokenRequest);
//            GetAccessTokenResponseBody body = response.getBody();
//            return body.getAccessToken();
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//            }
//
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//            }
//        }
//        return null;
    }

    @Autowired
    CommonMapper commonMapper;
    @Autowired
    private CheckUserMapper checkUserMapper;
    @Autowired
    CheckPlaceMapper checkPlaceMapper;

    //之前成功的方法
    @PostMapping("/getUserInfo")
    public JSONObject getUserInfo(@RequestBody JSONObject questJson) throws Exception {
        System.out.println("进入getUserInfo方法,请求参数:"+questJson);
        String accessToken = ADingLoginController.gettoken();
        String code = questJson.getString("code");
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getuserinfo");
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
        String placeId = questJson.getString("placeId");
//        System.out.println("地点ID:" + placeId);

        OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userGetByCodeResponse = rsp.getResult();
        String dingUserId = "";
        System.out.println("getUserInfo,rsp.getErrmsg():"+rsp.getErrmsg());
        try {
            dingUserId = userGetByCodeResponse.getUserid();
        } catch (Throwable throwable) {
            JSONObject reJson = new JSONObject();
            reJson.put("code", 400);
            reJson.put("msg", throwable);
            return reJson;
        }
        Long userId = null;
        Integer userRole = null;
        JSONObject getUserInfo = this.getAvatar(dingUserId, accessToken);
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
        System.out.println("getUserInfo,地点名称:" + placeName);
        List<HashMap> dingUsers = commonMapper.selectDingUserId(dingUserId);
        if (dingUsers.size() > 1) {
            //多账户
            JSONObject reJson = new JSONObject();
            reJson.put("code", 201);
            JSONArray reArray = new JSONArray();
            for (int i = 0; i < dingUsers.size(); i++) {
                HashMap dingUser = dingUsers.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", dingUser.get("id"));
                jsonObject.put("userRole", dingUser.get("user_role"));
                jsonObject.put("name", userGetByCodeResponse.getName());
                jsonObject.put("placeName", placeName);
                jsonObject.put("dingUserId", dingUser.get("ding_user_id"));
                jsonObject.put("avatar", avatar);
                reArray.add(jsonObject);
            }
            reJson.put("data", reArray);
            System.out.println("getUserInfo,多账户返回信息:" + reJson);
            return reJson;
        }
//        String avatar = this.getAvatar(dingUserId,accessToken);
        if (dingUsers.size() == 0) {
            //checkUser表里没有用户
            JSONObject reJson = new JSONObject();
            reJson.put("code", 300);
            reJson.put("dingUserId", dingUserId);
            reJson.put("msg", "请绑定用户");
            reJson.put("avatar", avatar);
            reJson.put("name", userGetByCodeResponse.getName());
            reJson.put("placeName", placeName);
            System.out.println("getUserInfo,绑定返回信息:" + reJson);
            return reJson;
//            CheckUser checkUser = new CheckUser();
//            checkUser.setDingUserId(dingUserId);
//            checkUserMapper.insertDingUser(checkUser);
//            userId = checkUser.getId();
//            userRole = checkUser.getUserRole();
        } else {
            HashMap dingUser = dingUsers.get(0);
            userId = (Long) dingUser.get("id");
            userRole = (Integer) dingUser.get("user_role");
        }
//        if(placeName == null){
//            JSONObject reJson = new JSONObject();
//            reJson.put("code",500);
//            reJson.put("msg","获取巡检地点失败");
//        }
        JSONObject reJson = new JSONObject();
        reJson.put("code", 200);
        reJson.put("msg", "登录成功");
        reJson.put("userId", userId);
        reJson.put("userRole", userRole);
        reJson.put("name", userGetByCodeResponse.getName());
        reJson.put("placeName", placeName);
        reJson.put("dingUserId", dingUserId);
        reJson.put("avatar", avatar);
        System.out.println("getUserInfo,登录返回信息:" + reJson);
        return reJson;
    }

    //绑定账号
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject jsonObject) throws Exception {
        System.out.println("login,进入绑定账号接口");
        System.out.println("login,请求参数:"+jsonObject);
        JSONObject reJson = new JSONObject();
        String code = jsonObject.getString("code");
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String placeId = jsonObject.getString("placeId");
        String dingUserId = jsonObject.getString("dingUserId");
        String avatar = jsonObject.getString("avatar");
        String name = jsonObject.getString("name");
        String accessToken = ADingLoginController.gettoken();
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetUserInfo());
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        OapiV2UserGetuserinfoResponse rsp = client.execute(req, accessToken);
        String errmsg = rsp.getErrmsg();
        if (!errmsg.equals("ok")) {
            reJson.put("code", 500);
            reJson.put("msg", "获取钉钉用户信息失败");
            return reJson;
        }

        OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userGetByCodeResponse = rsp.getResult();
        try {
            dingUserId = userGetByCodeResponse.getUserid();
        } catch (Throwable throwable) {
            reJson.put("code", 400);
            reJson.put("msg", throwable);
            return reJson;
        }
        Long userId = null;
        Integer userRole = null;
        JSONObject getUserInfo = this.getAvatar(dingUserId, accessToken);
        avatar = getUserInfo.getString("avatar");

        System.out.println("login,地点ID:" + placeId);
        CheckUser user = new CheckUser();
        user.setUserName(userName);
        List<CheckUser> list = checkUserMapper.loginSelect(user);
        System.out.println("login,checkuser信息:"+list.get(0).toString());
        if (list.size() > 0) {
            String password2 = list.get(0).getUserPassword();
            if (password.equals(password2)) {
                CheckUser updateDingId = list.get(0);
                if (updateDingId.getDingUserId() != null) {  //判断该用户是否已经绑定钉钉ID,如果已经绑定则不能再绑定
                    reJson.put("code", 300);
                    reJson.put("msg", "该账号已绑定");
                    return reJson;
                }
                updateDingId.setDingUserId(dingUserId);
                checkUserMapper.updateCheckUser(updateDingId);
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
                reJson.put("code", 200);
                reJson.put("msg", "登录成功");
                reJson.put("name", getUserInfo.getString("name"));
                reJson.put("userName", getUserInfo.getString("name"));
                reJson.put("placeName", placeName);
                reJson.put("userId", list.get(0).getId());
                reJson.put("userRole", list.get(0).getUserRole());
                reJson.put("dingUserId", dingUserId);
                reJson.put("avatar", avatar);
                System.out.println("login,返回信息:"+reJson);
                System.out.println("login,地点名称:" + placeName);
                return reJson;
            } else {
                reJson.put("code", 500);
                reJson.put("msg", "用户名或密码输入错误");
                return reJson;
            }
        } else {
            reJson.put("code", 500);
            reJson.put("msg", "用户名或密码输入错误");
            return reJson;
        }
    }

    @Autowired
    Config2RestTemplate config2RestTemplate;
    //预想另外写一套程序的方法,被否定了,直接用一套程序就ok了
//    @PostMapping("/getUserInfo")
//    public JSONObject getUserInfo(@RequestBody JSONObject questJson) throws Exception {
//        RestTemplate client = config2RestTemplate.restTemplate();
//        JSONObject dingJson = client.postForObject("http://192.168.7.243:8085/ding/getUserInfo", questJson, JSONObject.class);
//        Integer code = dingJson.getInteger("code");
//        if (code == 200){
//            String dingUserId = dingJson.getString("dingUserId");
//            String avatar = dingJson.getString("avatar");
//            HashMap dingUser = null;
//            Long userId = null;
//            Integer userRole = null;
//            dingUser = commonMapper.selectDingUserId(dingUserId);
//            if(dingUser == null){
//                //checkUser表里没有用户
//                CheckUser checkUser = new CheckUser();
//                checkUser.setDingUserId(dingUserId);
//                checkUserMapper.insertDingUser(checkUser);
//                userId = checkUser.getId();
//                userRole = checkUser.getUserRole();
//            }else {
//                userId = (Long) dingUser.get("id");
//            }
//            String placeId = questJson.getString("placeId");
//            String placeName = "";
//            if(placeId.length() > 0){
//                CheckPlace place = new CheckPlace();
//                place.setPlaceId(placeId);
//                List<CheckPlace> checkPlaces = checkPlaceMapper.selectCheckPlaceList(place);
//                if(checkPlaces.size() > 0){
//                    placeName = checkPlaces.get(0).getPlaceName();
//                }
//            }
//            JSONObject reJson = new JSONObject();
//            reJson.put("code",200);
//            reJson.put("userId",userId);
//            reJson.put("userRole",userRole);
//            reJson.put("name",dingJson.getString("name"));
//            reJson.put("placeName",placeName);
//            reJson.put("avatar",avatar);
//            return reJson;
//        }
//        return null;
//    }

    public static com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    //获取钉钉用户头像
    public JSONObject getAvatar(String userId, String access_token) {
        try {
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetAvatar());
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            String body = rsp.getBody();
            JSONObject bodyJson = JSONObject.parseObject(body);
            JSONObject result = bodyJson.getJSONObject("result");
            System.out.println("getAvatar()"+result);
//            return result.getString("avatar");
            return result;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/test")
    public void test(@RequestBody JSONObject questJson) throws Exception {
        CheckUser user = new CheckUser();
        Long userId = questJson.getLong("userId");
        user = checkUserMapper.selectCheckUserById(userId);
        if (user.getDingUserId() != null || !user.getDingUserId().equals("null")) {
            System.out.println("该账号已绑定");
        }
    }

//    @PostMapping("/login")
//    public JSONObject login(@RequestBody JSONObject questJson) throws Exception {
////        String url = "http://192.168.7.243:8086/ding/getUserInfo";
//        String url = "http://localhost:8086/ding/getUserInfo";
//        RestTemplate client = config2RestTemplate.restTemplate();
//        JSONObject dingJson = client.postForObject(url, questJson, JSONObject.class);
//        return dingJson;
//    }


    //登录第三方网站免登
//    public static com.aliyun.dingtalkoauth2_1_0.Client authClient() throws Exception {
//        Config config = new Config();
//        config.protocol = "https";
//        config.regionId = "central";
//        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
//    }
//    //接口地址：注意/auth与钉钉登录与分享的回调域名地址一致
//    @RequestMapping(value = "/auth", method = RequestMethod.GET)
//    public String getAccessToken(@RequestParam(value = "authCode")String authCode) throws Exception {
//        com.aliyun.dingtalkoauth2_1_0.Client client = authClient();
//        GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
//
//                //应用基础信息-应用信息的AppKey,请务必替换为开发的应用AppKey
//                .setClientId(RuoYiConfig.getDingBaseUrl()+RuoYiConfig.getAppKey())
//
//                //应用基础信息-应用信息的AppSecret，,请务必替换为开发的应用AppSecret
//                .setClientSecret(RuoYiConfig.getDingBaseUrl()+RuoYiConfig.getAppSecret())
//                .setCode(authCode)
//                .setGrantType("authorization_code");
//        GetUserTokenResponse getUserTokenResponse = client.getUserToken(getUserTokenRequest);
//        //获取用户个人token
//        String accessToken = getUserTokenResponse.getBody().getAccessToken();
//        return  getUserinfo(accessToken);
//
//    }
//    public static com.aliyun.dingtalkcontact_1_0.Client contactClient() throws Exception {
//        Config config = new Config();
//        config.protocol = "https";
//        config.regionId = "central";
//        return new com.aliyun.dingtalkcontact_1_0.Client(config);
//    }
//    /**
//     * 获取用户个人信息
//     * @param accessToken
//     * @return
//     * @throws Exception
//     */
//    public String getUserinfo(String accessToken) throws Exception {
//        com.aliyun.dingtalkcontact_1_0.Client client = contactClient();
//        GetUserHeaders getUserHeaders = new GetUserHeaders();
//        getUserHeaders.xAcsDingtalkAccessToken = accessToken;
//        //获取用户个人信息，如需获取当前授权人的信息，unionId参数必须传me
//        String me = JSON.toJSONString(client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody());
//        System.out.println(me);
//        return me;
//    }

    //钉钉发送消息
    @PostMapping("/sendDingMessage")
    public void sendDingMessage(String useridList, String content) throws Exception {
        String accessToken = ADingLoginController.gettoken();
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetMessage());
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.valueOf(RuoYiConfig.getAgentId()));
//        request.setUseridList("010632096848840165,01080454681421392211");
        request.setUseridList(useridList);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent("test123");
        msg.getText().setContent(content);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
        System.out.println(rsp.getBody());
    }

    @Autowired
    PinyinUtil pinyinUtil;

    //获取钉钉组织所有用户信息并更新到checkuser表
    @Scheduled(cron = "0 0 23 * * ?")
    @PostMapping("/getDingDeptIds")
    public void getDingDeptIds() throws Exception {
        String accessToken = ADingLoginController.gettoken();
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetDingDepts());
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(1L);
        req.setLanguage("zh_CN");
        OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
        JSONObject body = JSONObject.parseObject(rsp.getBody());
//        System.out.println(body);
        JSONObject result = body.getJSONObject("result");
        JSONArray deptIdList = result.getJSONArray("dept_id_list");

        for (int i = 0; i < deptIdList.size(); i++) {
            Long deptId = deptIdList.getLong(i);
            JSONArray deptIdList2 = getSonDeptIds(deptId, accessToken);
            if (deptIdList2.size() > 0) {
//                deptIdListReceive.add(deptIdList2);
                for (int j = 0; j < deptIdList2.size(); j++) {
                    deptIdList.add(deptIdList2.get(j));
                }
            }
        }
        System.out.println("所有部门id:" + deptIdList);
        JSONArray userArrayAll = new JSONArray();
        for (int i = 0; i < deptIdList.size(); i++) {
            Long deptId = deptIdList.getLong(i);
            JSONArray userArray = getUserDetail(deptId, accessToken);
            for (int j = 0; j < userArray.size(); j++) {
                userArrayAll.add(userArray.getJSONObject(j));
            }
        }
        HashSet userSet = new HashSet();
        JSONArray userArray = new JSONArray();
        for (int i = 0; i < userArrayAll.size(); i++) {
            JSONObject userDetail = userArrayAll.getJSONObject(i);
            String userid = userDetail.getString("userid");
            Integer setSize1 = userSet.size();//去重操作
            userSet.add(userid);
            Integer setSize2 = userSet.size();//去重操作
            if (setSize2 > setSize1) {
//                System.out.println(userArrayAll.getJSONObject(i));
                userArray.add(userDetail);
            }
        }
//        checkUserMapper.deleteStaff();//删除员工信息已经做了更新的方法了不需要删除
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = userArray.getJSONObject(i);
            System.out.println(jsonObject);
            String name = jsonObject.getString("name");
            name = clearBracket(name, '(', ')');
            name = clearBracket(name, '（', '）');
            String dingUserId = jsonObject.getString("userid");
            name = pinyinUtil.toPinyin(name);
            if (jsonObject.getString("job_number") != null) {
//                if (!jsonObject.getString("job_number").equals("")) {
                    CheckUser checkUser = new CheckUser(name, "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
                    try {
                        checkUserMapper.insertCheckUser(checkUser);
                    } catch (Throwable throwable1) {
                        checkUser = new CheckUser(name + jsonObject.getString("job_number"), "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
                        checkUserMapper.insertCheckUser(checkUser);
                    }
//                }
            }
        }
    }

    @PostMapping("/updateMobile")
    public void updateMobile() throws Exception {
        String accessToken = ADingLoginController.gettoken();
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetDingDepts());
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(1L);
        req.setLanguage("zh_CN");
        OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
        JSONObject body = JSONObject.parseObject(rsp.getBody());
//        System.out.println(body);
        JSONObject result = body.getJSONObject("result");
        JSONArray deptIdList = result.getJSONArray("dept_id_list");

        for (int i = 0; i < deptIdList.size(); i++) {
            Long deptId = deptIdList.getLong(i);
            JSONArray deptIdList2 = getSonDeptIds(deptId, accessToken);
            if (deptIdList2.size() > 0) {
//                deptIdListReceive.add(deptIdList2);
                for (int j = 0; j < deptIdList2.size(); j++) {
                    deptIdList.add(deptIdList2.get(j));
                }
            }
        }
        System.out.println("所有部门id:" + deptIdList);
        JSONArray userArrayAll = new JSONArray();
        for (int i = 0; i < deptIdList.size(); i++) {
            Long deptId = deptIdList.getLong(i);
            JSONArray userArray = getUserDetail(deptId, accessToken);
            for (int j = 0; j < userArray.size(); j++) {
                userArrayAll.add(userArray.getJSONObject(j));
            }
        }
        HashSet userSet = new HashSet();
        JSONArray userArray = new JSONArray();
        for (int i = 0; i < userArrayAll.size(); i++) {
            JSONObject userDetail = userArrayAll.getJSONObject(i);
            String userid = userDetail.getString("userid");
            Integer setSize1 = userSet.size();//去重操作
            userSet.add(userid);
            Integer setSize2 = userSet.size();//去重操作
            if (setSize2 > setSize1) {
//                System.out.println(userArrayAll.getJSONObject(i));
                userArray.add(userDetail);
            }
        }
//        checkUserMapper.deleteStaff();//删除员工信息已经做了更新的方法了不需要删除
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = userArray.getJSONObject(i);
//            System.out.println(jsonObject);
            if(jsonObject.getString("mobile") == null){
                System.out.println("姓名:"+jsonObject.getString("name")+"查不到手机号");
            }else {
                System.out.println("姓名:"+jsonObject.getString("name")+",手机号:"+jsonObject.getString("mobile"));
            }
//            System.out.println("姓名:"+jsonObject.getString("name")+",手机号:"+jsonObject.getString("mobile"));
            String name = jsonObject.getString("name");
            name = clearBracket(name, '(', ')');
            name = clearBracket(name, '（', '）');
            String dingUserId = jsonObject.getString("userid");
            name = pinyinUtil.toPinyin(name);
            if (jsonObject.getString("job_number") != null) {
//                if (!jsonObject.getString("job_number").equals("")) {
                CheckUser checkUser = new CheckUser(name, "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
                try {
                    checkUserMapper.insertCheckUser(checkUser);
                } catch (Throwable throwable1) {
                    checkUser = new CheckUser(name + jsonObject.getString("job_number"), "Jxfby@123", 0, jsonObject.getString("name"), "0", 1, dingUserId, jsonObject.getString("job_number"), jsonObject.getString("mobile"));
                    try{
                        checkUserMapper.insertCheckUser(checkUser);
                    }catch (Throwable throwable2){
                        checkUserMapper.updateStaffMobile(checkUser);
                    }
                }
//                }
            }
        }
    }

//    @PostMapping("/根据工号更新信息")

    @PostMapping("/test2")
    public String test2(String name) {
        name = clearBracket(name, '(', ')');
        name = clearBracket(name, '（', '）');
        return name;
    }

    public String clearBracket(String context, char left, char right) {
        int head = context.indexOf(left);
        if (head == -1) {
            return context;
        } else {
            int next = head + 1;
            int count = 1;
            do {
                if (context.charAt(next) == left) {
                    count++;
                } else if (context.charAt(next) == right) {
                    count--;
                }
                next++;
                if (count == 0) {
                    String temp = context.substring(head, next);
                    context = context.replace(temp, "");
                    head = context.indexOf(left);
                    next = head + 1;
                    count = 1;
                }
            } while (head != -1);
        }
        return context;
    }


    //获取子部门的部门ID
    public JSONArray getSonDeptIds(Long deptId, String accessToken) throws ApiException {
//        System.out.println("请求部门ID为:" + deptId);
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetDingDepts());
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(deptId);
        req.setLanguage("zh_CN");
        OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
        JSONObject body = JSONObject.parseObject(rsp.getBody());
//            System.out.println(body);
        JSONObject result = body.getJSONObject("result");
//        System.out.println(body);
        JSONArray deptIdList = result.getJSONArray("dept_id_list");
        return deptIdList;
    }

    //根据部门ID获取所有员工信息
    @PostMapping("/getUserDetail")
    public JSONArray getUserDetail(Long deptId, String accessToken) throws Exception {
        boolean has_more = true;
        Long cursor = 0L;
        JSONArray userArray = new JSONArray();
        do {
            DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl() + RuoYiConfig.getGetDingUserDetail());
            OapiV2UserListRequest req = new OapiV2UserListRequest();
            req.setDeptId(deptId);
            req.setCursor(cursor);
            req.setSize(100L);
            req.setOrderField("modify_desc");
            req.setContainAccessLimit(false);
            req.setLanguage("zh_CN");
            OapiV2UserListResponse rsp = client.execute(req, accessToken);
            JSONObject body = JSONObject.parseObject(rsp.getBody());
            JSONObject result = body.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                userArray.add(list.getJSONObject(i));
            }
            has_more = result.getBoolean("has_more");
            if (has_more) {
                cursor = result.getLong("next_cursor");
            }
//            System.out.println(rsp.getBody());
        } while (has_more);
        return userArray;
    }

    @PostMapping("/jsapiTicket")//获得jsapiTicket
    public String jsapiTicket(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getDingBaseUrl()+"/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setHttpMethod("GET");
        OapiGetJsapiTicketResponse rsp = client.execute(req, accessToken);
        System.out.println("jsapiTicket():"+rsp.getBody());
        JSONObject body = JSONObject.parseObject(rsp.getBody());
        return body.getString("ticket");
    }
}
