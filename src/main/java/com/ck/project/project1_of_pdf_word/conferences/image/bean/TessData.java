package com.ck.project.project1_of_pdf_word.conferences.image.bean;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

/**
 * Created by Administrator on 2018-5-16 0016.
 */
public class TessData {
   /* private static ITesseract instance = null;
    static{
        instance = engRecognition();
    }
    private TessData (){}
    public static ITesseract getInstance() {
        return instance;
    }
    public static ITesseract engRecognition(){
        instance = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        instance.setLanguage("eng");//英文库识别数字比较准确
        return instance;
    }*/

    private volatile static ITesseract instance;
    private TessData(){}
    public static ITesseract getInstance(){
        if (instance == null) {
            synchronized (TessData.class) {
                if (instance == null) {
                    instance = new Tesseract();
                    File tessDataFolder = LoadLibs.extractTessResources("tessdata");
                    instance.setDatapath(tessDataFolder.getAbsolutePath());
                    instance.setLanguage("eng");//英文库识别数字比较准确
                }
            }
        }
        return instance;
    }
}
