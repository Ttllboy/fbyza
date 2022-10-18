package com.ruoyi.zayy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ding")
public class ADingLoginController {

    @PostMapping("/login")
    public void login(){
        System.out.println("login");

    }
}
