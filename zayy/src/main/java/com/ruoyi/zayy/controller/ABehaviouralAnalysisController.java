package com.ruoyi.zayy.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zayy.domain.CheckItem;
import com.ruoyi.zayy.domain.RecordAj;
import com.ruoyi.zayy.mapper.CheckItemMapper;
import com.ruoyi.zayy.mapper.RecordAjMapper;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/behaviouralAnalysis")
public class ABehaviouralAnalysisController {


    @Autowired
    private RecordAjMapper recordAjMapper;

    @PostMapping("/behaviouralDataSource")
    public JSONObject behaviouralDataSource(@RequestBody JSONObject questJson){
        try {
            Integer type = questJson.getInteger("type");
            RecordAj recordAj = new RecordAj();
            recordAj.setSecurityIp(questJson.getString("ip"));
            recordAj.setSecurityType(type);
            recordAj.setImgBase64(questJson.getString("img"));
            recordAj.setSecurityTime(new Date());
            try {
                recordAjMapper.insertRecordAj(recordAj);
                JSONObject reJson = new JSONObject();
                reJson.put("code", 200);
                reJson.put("msg", "success");
                return reJson;
            } catch (Throwable throwable) {
                JSONObject reJson = new JSONObject();
                reJson.put("code", 500);
                reJson.put("msg", "error");
                return reJson;
            }
        } catch (Throwable throwable) {
            System.out.println("报错信息：" + throwable);
            RecordAj recordAj = new RecordAj();
            recordAj.setImgBase64(String.valueOf(questJson));
            recordAj.setSecurityType(0);
            recordAjMapper.insertRecordAj(recordAj);
            JSONObject reJson = new JSONObject();
            reJson.put("code", 500);
            reJson.put("msg", throwable);
            return reJson;
        }
    }
    @Autowired
    CheckItemMapper checkItemMapper;
    @PostMapping("/test")
    public void test(@RequestBody String data){
        if(data.equals("0")){
            int a =1;
            int b = 0;
            int c = a/b;
        }
        if(data.equals("1")){
            List<CheckItem> list = checkItemMapper.selectCheckItemList(new CheckItem());
        }
    }



}
