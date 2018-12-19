package com.ck.JavaWebExampleTest.test_public;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test {
    private static String ss = null;
    private static boolean aa = Boolean.parseBoolean(null);


    public static void main(String[] args) throws FileNotFoundException {
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
        int correctNumber2 = 0;
        int troublenumber2 = 0;
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<times.size();i++){
            try {
                if (times.get(i-1) == null){
                    continue;
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("找不到该时间"+times.get(i)+"的前一天");
                continue;
            }
            if (Double.parseDouble(opens.get(i)) > Double.parseDouble(opens.get(i-1))) {
                if (Double.parseDouble(hights.get(i)) >= Double.parseDouble(hights.get(i - 1))*1.0016 ) {
                    correctNumber1 += 1;
                    System.out.println("时间：" + times.get(i) + "  今开>昨开：" + opens.get(i) + " >" + opens.get(i - 1) + "  今高>昨高：" + hights.get(i) +" >"+hights.get(i-1));
                } else if(Double.parseDouble(hights.get(i))  < Double.parseDouble(hights.get(i - 1)) *1.0016){
                    troublenumber1 += 1;
                }
                if (Double.parseDouble(hights.get(i)) >= Double.parseDouble(opens.get(i))*1.0016){
                    correctNumber2 += 1;
                }else if(Double.parseDouble(hights.get(i)) < Double.parseDouble(opens.get(i))*1.0016){
                    troublenumber2 += 1;
                    list.add(i);
                }
            }
        }
        System.out.println("今开大于昨开，今高大于昨高概率："+"总数："+times.size()+"正数："+correctNumber1+"误数："+troublenumber1+"今开 > 昨开，今高 》 昨高的概率："+((double)correctNumber1/(correctNumber1+troublenumber1)));
        for (int i =0;i<list.size();i++){
            System.out.println("时间1：" + times.get(list.get(i)) + "  今开1：" + opens.get(list.get(i)) + "  昨开1：" + opens.get(list.get(i) - 1) + "  今高1：" + hights.get(list.get(i)) + "  昨高1：" +hights.get(list.get(i) -1));
        }
        System.out.println("今开大于昨开，今高大于今开概率："+"总数："+times.size()+"正数："+correctNumber2+"误数："+troublenumber2+"今开 > 昨开，今高 》 今开的概率："+((double)correctNumber2/(correctNumber2+troublenumber2)));

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
    }
    public static void readExcel() throws FileNotFoundException {
        FileInputStream is = new FileInputStream("D:\\aaa\\工作文档接收\\tower_sap数据参考.xlsx");
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(is);
            Workbook wb = new HSSFWorkbook(poifsFileSystem);
            Sheet sheet = wb.getSheetAt(0);
            for (int i=0;i<sheet.getLastRowNum();i++){
                for(int j=0;j<sheet.getRow(i).getLastCellNum();j++){
                    CellAddress cellAddress = new CellAddress(i,j);
                    Cell cell = (Cell) sheet.getCellComment(cellAddress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
