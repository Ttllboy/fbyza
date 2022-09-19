package com.ruoyi.zayy.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.zayy.domain.CheckRecord;
import com.ruoyi.zayy.domain.RecordImg;
import com.ruoyi.zayy.mapper.CheckRecordMapper;
import com.ruoyi.zayy.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checkImgApi")
public class AImgController {

    @Autowired
    CheckRecordMapper checkRecordMapper;
    @Autowired
    CommonMapper commonMapper;

    @PostMapping("/profilePhotoUpload")
    public JSONObject profilePhotoUpload(@RequestParam("file") MultipartFile fileUpload,@RequestParam("recordId") String recordId){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名。也可以在这里添加判断语句，规定特定格式的图片才能上传，否则拒绝保存。
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //为了避免发生图片替换，这里使用了文件名重新生成
//        fileName = UUID.randomUUID()+suffixName;
        fileName = UUID.randomUUID()+".png";
        try {
            //将图片保存到文件夹里
            fileUpload.transferTo(new File(RuoYiConfig.getCheckImg()+"/"+fileName));
            JSONObject reJson = new JSONObject();
            reJson.put("msg",fileName);
            reJson.put("code",200);
            return reJson;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject reJson = new JSONObject();
            reJson.put("msg","图片上传错误");
            reJson.put("code",500);
            return reJson;
        }
    }

}
