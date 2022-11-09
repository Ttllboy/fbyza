package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /** 验证码类型 */
    private static String captchaType;

//    巡检图片上传地址
    private static String checkImg;

    //网页地址
    private static String webUrl;

    //logo地址
    private static String logoUrl;
    private static String accessToken;
    private static String getUserInfo;
    private static String getAvatar;

    //服务器图片地址
    private static String apiImgUrl;
    private static String apiImgUrl2;
    private static String webImgUrl;
    private static String webImgUrl2;
    private static String appKey;
    private static String agentId;
    private static String appSecret;
    private static String apiServer;

    public static String getWebImgUrl2() {
        return webImgUrl2;
    }

    public void setWebImgUrl2(String webImgUrl2) {
        RuoYiConfig.webImgUrl2 = webImgUrl2;
    }

    public static String getWebImgUrl() {
        return webImgUrl;
    }

    public void setWebImgUrl(String webImgUrl) {
        RuoYiConfig.webImgUrl = webImgUrl;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        RuoYiConfig.accessToken = accessToken;
    }

    public static String getGetUserInfo() {
        return getUserInfo;
    }

    public void setGetUserInfo(String getUserInfo) {
        RuoYiConfig.getUserInfo = getUserInfo;
    }

    public static String getGetAvatar() {
        return getAvatar;
    }

    public void setGetAvatar(String getAvatar) {
        RuoYiConfig.getAvatar = getAvatar;
    }

    public static String getApiServer() {
        return apiServer;
    }

    public void setApiServer(String apiServer) {
        RuoYiConfig.apiServer = apiServer;
    }

    public static String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        RuoYiConfig.appKey = appKey;
    }

    public static String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        RuoYiConfig.agentId = agentId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        RuoYiConfig.appSecret = appSecret;
    }

    public static String getApiImgUrl() {
        return apiImgUrl;
    }

    public void setApiImgUrl(String apiImgUrl) {
        RuoYiConfig.apiImgUrl = apiImgUrl;
    }

    public static String getApiImgUrl2() {
        return apiImgUrl2;
    }

    public void setApiImgUrl2(String apiImgUrl2) {
        RuoYiConfig.apiImgUrl2 = apiImgUrl2;
    }

    public static String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        RuoYiConfig.logoUrl = logoUrl;
    }

    public static String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        RuoYiConfig.webUrl = webUrl;
    }

    public static String getCheckImg() {
        return checkImg;
    }

    public void setCheckImg(String checkImg) {
        this.checkImg = checkImg;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        RuoYiConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
