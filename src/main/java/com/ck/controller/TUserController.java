package com.ck.controller;

import com.ck.dao.entity.tuser;
import com.ck.dao.mapper.TUserMapper;
import com.ck.destDao.mapper.destTuserMapper;
import com.ck.framework.ContextUtil;
import com.ck.framework.context.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Controller
public class TUserController {
    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private destTuserMapper destTuserMapper;

    @RequestMapping(value = "test/count")
    @ResponseBody
    public int test(){
        AppContext context = ContextUtil.getAppContext();
        tuser tuser = tUserMapper.selectOneTest();
        Map map = new HashMap();
        map.put("userId","1");
        tuser  tuser1 = tUserMapper.selectXmlTest(map);
        tuser1.setPassword("123");
        int a = destTuserMapper.selectTest();
        return tUserMapper.selectCountTest();
    }

    /*@RequestMapping(value = "/")
    @ResponseBody
    public String login(){
        return "redirect:/welcome.html";
    }*/
}
