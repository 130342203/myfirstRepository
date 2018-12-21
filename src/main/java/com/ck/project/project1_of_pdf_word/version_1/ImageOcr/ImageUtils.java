package com.ck.project.project1_of_pdf_word.version_1.ImageOcr;

import java.awt.*;
import java.awt.image.BufferedImage;

//图像处理，纠正图像错位，还原为最接近正常的图像
public class ImageUtils {
    // 1、识别图像中存在的色素混杂程度，根据不同的图像组成做不同的处理：
    //  1.1、几乎纯文字，颜色少，内容与空白能轻易区分，适合二值化后做识别
    //  1.2、
    //打印图像像素点值
    public void printPixelValue(BufferedImage image){
        for (int i=0;i<image.getWidth();i++){
            for (int j=0;j<image.getHeight();j++){
                if (j==(image.getHeight()-1)){
                    System.out.println();
                }else{
                    int a = getValueFromColorOfRGB(image.getRGB(i,j));
                    //System.out.print(a+","+"\t");

                    /*if (a<300){
                        System.out.print(0+","+"\t");
                    }else {
                        System.out.print(1+","+"\t");
                    }*/
                }
            }
        }
    }
    //混合颜色RGB转分离RGB色
    public static int getValueFromColorOfRGB(int value){
        Color color = new Color(value);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return green+red+blue;
    }
    //简化的单子标记：圈住单字
    public BufferedImage simpleTagOfSingleWord(BufferedImage image){
        BufferedImage twoValueImage = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        for (int i=0;i<imageWidth;i++){
            for (int j=0;j<imageHeight;j++){

            }
        }
        return twoValueImage;
    }

}
