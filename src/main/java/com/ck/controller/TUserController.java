package com.ck.controller;

import com.ck.dao.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/6/11.
 */
@Controller
public class TUserController {
    @Autowired
    private TUserMapper tUserMapper;

    @RequestMapping(value = "test/count")
    @ResponseBody
    public int test(){
        return tUserMapper.selectCountTest();
    }

 /*   @RequestMapping(value = "/")
    @ResponseBody
    public String login(){
        return "redirect:/welcome.html";
    }*/
}
