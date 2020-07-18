package com.ck.controller;

import com.ck.service.excel.ExcelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/7/17
 * @Content:
 */
@Controller
public class ExcelController {
    @Resource
    ExcelService excelService;

    @RequestMapping(value = "test/download")
    @ResponseBody
    public void test(HttpServletResponse response) throws IOException {
        excelService.insertTestData();
//        excelService.downloadExcel(response);
    }
}
