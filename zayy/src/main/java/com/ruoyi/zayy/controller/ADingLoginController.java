package com.ruoyi.zayy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkoauth2_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.config.Config2RestTemplate;
import com.ruoyi.zayy.domain.*;
import com.ruoyi.zayy.mapper.CheckPlaceMapper;
import com.ruoyi.zayy.mapper.CheckUserMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/ding")
public class ADingLoginController {

    public static String gettoken() throws Exception {
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getAccessToken());
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(RuoYiConfig.getAppKey());
        request.setAppsecret(RuoYiConfig.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        System.out.println(response.getBody());
        String body = response.getBody();
        JSONObject bodyJson = JSONObject.parseObject(body);
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
//    @PostMapping("/getUserInfo")
    public JSONObject getUserInfoBefore(@RequestBody JSONObject questJson) throws Exception {
        String accessToken = ADingLoginController.gettoken();
        String code = questJson.getString("code");
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getuserinfo");
        DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getGetUserInfo());
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        OapiV2UserGetuserinfoResponse rsp = client.execute(req, accessToken);
        String errmsg = rsp.getErrmsg();
        if(!errmsg.equals("ok")){
            JSONObject reJson = new JSONObject();
            reJson.put("code",500);
            reJson.put("msg","获取钉钉用户信息失败");
        }

        OapiV2UserGetuserinfoResponse.UserGetByCodeResponse userGetByCodeResponse = rsp.getResult();
        String dingUserId = userGetByCodeResponse.getUserid();
        HashMap dingUser = null;
        Long userId = null;
        Integer userRole = null;
        dingUser = commonMapper.selectDingUserId(dingUserId);
        if(dingUser == null){
            //checkUser表里没有用户
            CheckUser checkUser = new CheckUser();
            checkUser.setDingUserId(dingUserId);
            checkUserMapper.insertDingUser(checkUser);
            userId = checkUser.getId();
            userRole = checkUser.getUserRole();
        }else {
            userId = (Long) dingUser.get("id");
        }

        String avatar = this.getAvatar(dingUserId,accessToken);

        String placeId = questJson.getString("placeId");
        CheckPlace place = new CheckPlace();
        place.setPlaceId(placeId);
        List<CheckPlace> list = checkPlaceMapper.selectCheckPlaceList(place);
        String placeName = null;
        if(list.size() > 0){
            placeName = list.get(0).getPlaceName();
        }
//        if(placeName == null){
//            JSONObject reJson = new JSONObject();
//            reJson.put("code",500);
//            reJson.put("msg","获取巡检地点失败");
//        }

        JSONObject reJson = new JSONObject();
        reJson.put("code",200);
        reJson.put("userId",userId);
        reJson.put("userRole",userRole);
        reJson.put("name",userGetByCodeResponse.getName());
        reJson.put("placeName",placeName);
        reJson.put("avatar",avatar);

        return reJson;
    }
    @Autowired
    Config2RestTemplate config2RestTemplate;
    @PostMapping("/getUserInfo")
    public JSONObject getUserInfo(@RequestBody JSONObject questJson) throws Exception {
        RestTemplate client = config2RestTemplate.restTemplate();
        JSONObject dingJson = client.postForObject("http://192.168.7.243:8085/ding/getUserInfo", questJson, JSONObject.class);
        Integer code = dingJson.getInteger("code");
        if (code == 200){
            String dingUserId = dingJson.getString("dingUserId");
            String avatar = dingJson.getString("avatar");
            HashMap dingUser = null;
            Long userId = null;
            Integer userRole = null;
            dingUser = commonMapper.selectDingUserId(dingUserId);
            if(dingUser == null){
                //checkUser表里没有用户
                CheckUser checkUser = new CheckUser();
                checkUser.setDingUserId(dingUserId);
                checkUserMapper.insertDingUser(checkUser);
                userId = checkUser.getId();
                userRole = checkUser.getUserRole();
            }else {
                userId = (Long) dingUser.get("id");
            }
            String placeId = questJson.getString("placeId");
            String placeName = "";
            if(placeId.length() > 0){
                CheckPlace place = new CheckPlace();
                place.setPlaceId(placeId);
                List<CheckPlace> checkPlaces = checkPlaceMapper.selectCheckPlaceList(place);
                if(checkPlaces.size() > 0){
                    placeName = checkPlaces.get(0).getPlaceName();
                }
            }
            JSONObject reJson = new JSONObject();
            reJson.put("code",200);
            reJson.put("userId",userId);
            reJson.put("userRole",userRole);
            reJson.put("name",dingJson.getString("name"));
            reJson.put("placeName",placeName);
            reJson.put("avatar",avatar);
            return reJson;
        }
        return null;
    }


    public static com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    //获取钉钉用户头像
    public String getAvatar(String userId,String access_token){
        try {
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            DingTalkClient client = new DefaultDingTalkClient(RuoYiConfig.getGetAvatar());
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            String body = rsp.getBody();
            JSONObject bodyJson = JSONObject.parseObject(body);
            JSONObject result = bodyJson.getJSONObject("result");
            return result.getString("avatar");
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/test")
    public Long test(@RequestBody JSONObject questJson) throws Exception {
        return null;
    }


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
//                .setClientId(RuoYiConfig.getAppKey())
//
//                //应用基础信息-应用信息的AppSecret，,请务必替换为开发的应用AppSecret
//                .setClientSecret(RuoYiConfig.getAppSecret())
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
}
