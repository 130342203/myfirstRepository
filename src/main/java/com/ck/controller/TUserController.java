package com.ck.controller;

import com.ck.config.ConfigConstants;
import com.ck.dao.entity.TUser;
import com.ck.dao.mapper.TUserMapper;
import com.ck.dao.mapper.basicMapper.BasicMapper;
import com.ck.destDao.mapper.destTuserMapper;
import com.ck.framework.ContextUtil;
import com.ck.framework.SpringContextHolder;
import com.ck.framework.context.AppContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Controller
public class TUserController {
    @Resource
    private TUserMapper tUserMapper;
    @Resource
    private destTuserMapper destTuserMapper;
    @Resource
    BasicMapper basicMapper;

    @RequestMapping(value = "test/count")
    @ResponseBody
    public int test() throws IOException {
        AppContext context = ContextUtil.getAppContext();
        /*TUser tuser = tUserMapper.selectOneTest();
        Map map = new HashMap();
        map.put("userId","1");
        TUser  tuser1 = tUserMapper.selectXmlTest(map);
        tuser1.setPassword("123");
        int a = destTuserMapper.selectTest();
        List<TUser> tusers = tUserMapper.selectAll();
        *//*事务*//*
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        PlatformTransactionManager transactionManager = SpringContextHolder.getBean(ConfigConstants.PRIMARY_TRANSACTION_MANAGER);
        TransactionStatus status = transactionManager.getTransaction(definition);
        tUserMapper.updateByPrimaryKeySelective(tuser1);
        transactionManager.commit(status);
        return tUserMapper.selectCountTest();*/
  /*      URL url = new URL("http://attachment.bireturn.com/2018-10/188091330bde4b2db8435fb1b6accab6.xls");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        HttpServletResponse response = context.getHttpServletResponse();
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/octet-stream");
        //response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" +"111.xls" );
        FileCopyUtils.copy(inputStream, outputStream);*/
        String path = "G:\\\\test.xls";
        // 2、保存到临时文件
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len = 0;
        // 输出的文件流保存到本地文件
        OutputStream os = null;
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(path));
        HttpServletResponse response = context.getHttpServletResponse();
        response.setContentType("application/force-download");
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment; filename=" + new String("111.xls".getBytes("UTF-8"), "ISO8859-1"));
        /*while ((len = br.read(bs)) > 0)
            outputStream.write(bs, 0, len);
        br.close();
        outputStream.flush();
        outputStream.close();*/
        //InputStream ins = br.
        FileCopyUtils.copy(br, outputStream);
        return 0;
    }

    /*@RequestMapping(value = "/")
    @ResponseBody
    public String login(){
        return "redirect:/welcome.html";
    }*/

   /* @RequestMapping(value = "excel/update")
    @ResponseBody
    public void excelUpdate() throws FileNotFoundException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\20180816.xlsx";
        FileInputStream is = new FileInputStream(filePath);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            Map <String,List> result = new HashMap<>();
            List<String> matnrs = new ArrayList();
            List<String> troublematnrs = new ArrayList();
            List<String> sitecodes = new ArrayList();
            List<String> troublesitecodes = new ArrayList();
            List<String> plantypes = new ArrayList();
            List<String> troubleplantypes = new ArrayList();
            for (int i=0;i<sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                String matnr = getInputString(row.getCell(0));
                String sitecode = getInputString(row.getCell(1));
                String planType = getInputString(row.getCell(2));
                if (matnr!=null && sitecode != null && planType !=null){
                    matnrs.add(matnr);
                    sitecodes.add(sitecode);
                    plantypes.add(planType);
                }else {
                    troublematnrs.add(matnr);
                    troublesitecodes.add(sitecode);
                    troubleplantypes.add(planType);
                }
            }
            System.out.println("-------------------正确--------------------------");
            for (int i=0;i<matnrs.size();i++){
                System.out.println(matnrs.get(i) + "\t" + sitecodes.get(i) +"\t"+ plantypes.get(i));
            }
            System.out.println("-------------------错误--------------------------");
            for (int i=0;i<troublematnrs.size();i++){
                System.out.println(troublematnrs.get(i) + "\t" + troublesitecodes.get(i) +"\t"+ troubleplantypes.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getInputString(Cell cell) {
        String cellValue = "";
        if (cell != null && !cell.equals("")) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    cellValue = cell.getNumericCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellValue = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        } else {
            cellValue = null;
        }
        return cellValue;
    }*/
   @RequestMapping(value = "calcu")
   @ResponseBody
   public void calcu() throws FileNotFoundException {
       FileInputStream fileIn = new FileInputStream("H://test.txt");
       String txt = null;
       try {
           while (true){
               byte[] bytes = new byte[2048];
               int n = fileIn.read(bytes);
               System.out.println(new String(bytes));
               txt = txt + new String(bytes);
               if (n <= 0){
                   break;
               }
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
       String[] datas = txt.split(",");
       List<String> times = new ArrayList<>();
       List<String> opens = new ArrayList<>();
       List<String> closes = new ArrayList<>();
       List<String> lowers = new ArrayList<>();
       List<String> hights = new ArrayList<>();
       for (int i=0;i<datas.length;i++){
           if (i % 5 == 0){
               int timeBegin = datas[i].indexOf("[\"");
               int timeEnd = datas[i].lastIndexOf("\"");
               times.add(datas[i].substring(timeBegin + 2,timeEnd));
           }
           if (i % 5 == 1){
               opens.add(datas[i]);
           }
           if (i % 5 == 2){
               closes.add(datas[i]);
           }
           if (i % 5 == 3){
               lowers.add(datas[i]);
           }
           if (i % 5 == 4){
               hights.add(datas[i].substring(0,datas[i].indexOf("]")));
           }
       }
       int correctNumber1 = 0;
       int troublenumber1 = 0;
       for (int i=0;i<times.size();i++){
           try {
               if (times.get(i-1) == null){
                   continue;
               }
           }catch (ArrayIndexOutOfBoundsException e){
               System.out.println("找不到该时间"+times.get(i)+"的前一天");
               continue;
           }
           if (Double.parseDouble(hights.get(i)) > Double.parseDouble(opens.get(i))){
               if (Double.parseDouble(opens.get(i)) > Double.parseDouble(opens.get(i-1))){
                   correctNumber1 += 1;
               }else {
                   troublenumber1 += 1;
                   System.out.println("时间："+times.get(i)+"\t今日开盘："+opens.get(i)+"\t昨日开盘："+opens.get(i-1)+"\t今日最高："+hights.get(i));
               }
           }
       }
       System.out.println("今日最高价高于开盘价时，呈涨势的概率为"+((double)correctNumber1/(correctNumber1+troublenumber1)));

   }




}
