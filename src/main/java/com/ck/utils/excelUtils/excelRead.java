package com.ck.utils.excelUtils;

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

public class excelRead {
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
