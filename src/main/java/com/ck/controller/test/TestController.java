package com.ck.controller.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ck.dao.entity.City;
import com.ck.dao.mapper.CityMapper;
import com.ck.framework.ContextUtil;
import com.ck.framework.context.AppContext;
import com.ck.framework.utils.crypto.DesFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/4/3
 * @Content:
 */
@Controller
public class TestController {

    @Resource
    CityMapper cityMapper;

    @RequestMapping(value = "test/city")
    @ResponseBody
    public Object test() throws IOException {
        AppContext context = ContextUtil.getAppContext();
        List<City> cities = cityMapper.selectAll();
        return cities;
    }

    @RequestMapping(value = "test/getData",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object testgetData() throws IOException {
        AppContext context = ContextUtil.getAppContext();
        HttpServletResponse response = context.getHttpServletResponse();
        response.setContentType("text/html;charset=UTF-8");
        Map map = context.getRequestParamMap();
        String jsonString= JSON.toJSONString(map);
        System.out.println(jsonString);
        Map<String,String> result = new HashMap<>();
        result.put("code","9");
        result.put("data",new String("数据保存完成".getBytes(),"UTF-8"));
        result.put("message","完成");
        String json_str = new String(JSONObject.toJSONString(result).getBytes(),"UTF-8");
        json_str = DesFactory.encrypt(json_str,"a03b6fe1ajikongshujuchuanshu","%Td(8@3b");
        return json_str;
    }
}
