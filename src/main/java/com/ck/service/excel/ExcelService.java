package com.ck.service.excel;

import com.ck.dao.entity.TUser;
import com.ck.dao.mapper.TUserMapper;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Version:1.0
 * @Author:chenkun
 * @Date:2020/7/17
 * @Content: 1、测试多线程写入excel
 * 2、测试大批量数据查询写入excel
 */
@Service
public class ExcelService {
    @Resource
    TUserMapper tUserMapper;

    //下载
    //多线程
    //写入操作
    //查询
    //计时-效率



    /*
    * excel写入
    * */
    public SXSSFWorkbook writeToExcel(SXSSFWorkbook workbook){
        if (workbook ==null){
            workbook = new SXSSFWorkbook();
        }
        workbook.setCompressTempFiles(true);
        workbook.createSheet();
        SXSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < 50; i++) {
            for (int z = 0; z < 10000; z++) {
                SXSSFRow row = sheet.createRow(i*10000+z);
                for (int j = 0; j < 10; j++) {
                    row.createCell(j).setCellValue("你好："+j);
                }
            }
        }
        return workbook;
    }

    /*
    * 保存excel到指定的路径
    * */
    public void saveExcel(SXSSFWorkbook workbook,String filePath,String fileName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println(">>>>>提供路径不存在，自动创建路径<<<<<");
            file.mkdirs();
        }
        File file1 = new File(filePath+File.separator+fileName);
        if (!file1.exists()){
            file1.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file1);
        workbook.write(outputStream);
        workbook.dispose();
        outputStream.flush();
        outputStream.close();
    }

    /*
    * 下载excel
    * */
    public void downloadExcel(HttpServletResponse response) throws IOException {
        File file ;
        String path = "C:\\Users\\chenkun\\Desktop\\testHdcts\\testExcel";
        String fileName = "test.xlsx";
        SXSSFWorkbook workbook = null;
        workbook = writeToExcel(workbook);
        saveExcel(workbook,path,fileName);
        file = new File(path+File.separator+fileName);
//        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
//        file = ResourceUtils.getFile("classpath:file/test/01-0-20200717151300055.xml");
        dowloadFile(response,file);
    }
    /*
    * 下载文件
    * */
    public void dowloadFile(HttpServletResponse response,File file) throws IOException {
        String name = file.getName();
        if (file.exists()){
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            response.setContentType("application/octet-stream;charset=UTF-8");
            InputStream ins = new FileInputStream(file);
            FileCopyUtils.copy(ins,response.getOutputStream());
        }
    }

    /*
     * 多线程
     * */

    class excelRunable implements Runnable{

        @Override
        public void run() {

        }
    }
    /*
    * 向表中写入测试数据
    * */
    public void insertTestData(){
        for (int x=1;x<100;x++){
            for (int y=0;y<1000;y++){
                TUser user = new TUser();
                user.setUserId(x*100 + y);
                user.setUserName("ceshi--"+(x+y));
                tUserMapper.insert(user);
            }
        }
    }
}
