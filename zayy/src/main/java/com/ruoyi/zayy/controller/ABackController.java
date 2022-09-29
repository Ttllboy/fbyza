package com.ruoyi.zayy.controller;


import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.zayy.domain.RecordAj;
import com.ruoyi.zayy.mapper.RecordAjMapper;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/backApi")
public class ABackController {

    @Autowired
    private RecordAjMapper recordAjMapper;

    @PostMapping("/ajInsert")
    public Integer ajInsert(@RequestBody JSONObject questJson){
        String securityIp = questJson.getString("securityIp");
        String securityNum = questJson.getString("securityNum");
        String securityImg = questJson.getString("securityImg");
        Date securityTime = questJson.getDate("securityTime");
        RecordAj recordAj = new RecordAj();
        recordAj.setSecurityIp(securityIp);
        recordAj.setSecurityNum(securityNum);
        recordAj.setSecurityImg(securityImg);
        recordAj.setSecurityTime(securityTime);
        recordAjMapper.insertRecordAj(recordAj);
        return 1;
    }

}
