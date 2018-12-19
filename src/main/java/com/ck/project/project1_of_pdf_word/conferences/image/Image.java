package com.ck.project.project1_of_pdf_word.conferences.image;

import com.ck.project.project1_of_pdf_word.conferences.image.bean.Line;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.TessData;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-5-16 0016.
 */
public class Image {

//    public static int getThreshold(BufferedImage img, int[][] gray){
//        img.get
//        int[] histData = new int[width * height];
//        // Calculate histogram
//        for (int x = 0; x < width; x++){
//            for (int y = 0; y < height; y++){
//                int red = 0xFF & gray[x][y];
//                histData[red]++;
//            }
//        }
//        // Total number of pixels
//        int pixelNumber = size;
//
//        float sum = 0;
//        for (int t = 0; t < 256; t++)
//            sum += t * histData[t];
//
//        float sumB = 0;
//        int wB = 0;
//        int wF = 0;
//
//        float varMax = 0;
//        int threshold = 0;
//
//        for (int t = 0; t < 256; t++){
//            wB += histData[t]; // Weight Background
//            if (wB == 0)
//                continue;
//
//            wF = pixelNumber - wB; // Weight Foreground
//            if (wF == 0)
//                break;
//
//            sumB += (float) (t * histData[t]);
//
//            float mB = sumB / wB; // Mean Background
//            float mF = (sum - sumB) / wF; // Mean Foreground
//
//            // Calculate Between Class Variance
//            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
//
//            // Check if new maximum found
//            if (varBetween > varMax){
//                varMax = varBetween;
//                threshold = t;
//            }
//        }
//        return threshold;
//    }

    /**
     * 黑化.
     * @param image 读取后图片
     */

    public static BufferedImage  grayImage(BufferedImage image,int threshold) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        //二值化图片
        BufferedImage twoValueImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isTarget(image.getRGB(x, y), threshold)) {
                    twoValueImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    twoValueImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return twoValueImage ;
    }

    /**
     * 图片二值化
     */
    public static BufferedImage  twoValueImage(BufferedImage img,int threshold) throws IOException {
        //二值化图片
        BufferedImage twoValueImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (isTarget(img.getRGB(x, y), threshold)) {
                    twoValueImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    twoValueImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return twoValueImage ;
    }

    public static Boolean isTarget(int imageRGB, int targetThreshold){
        final Color color = new Color(imageRGB);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        if (red + green + blue <= targetThreshold) {
            return true;
        }
        return false;
}

    public static BufferedImage grayImage(BufferedImage image) throws IOException{
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        grayImage.getGraphics()
                .drawImage(image, 0, 0,
                        image.getWidth(),image.getHeight(), null);
        return grayImage;
    }

    /**
     * 图片缩放.
     * @param scaleWidth 缩放后宽度
     * @param scaleHeight 缩放后高度
     */
    public static BufferedImage scaleImage(int scaleWidth,int scaleHeight,BufferedImage image) throws Exception {
        int width = image.getWidth();
        int height = image.getHeight();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(scaleWidth,
                scaleHeight, BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.black);
        g.fillRect(0, 0, scaleWidth, scaleHeight);
        g.drawImage(image.getScaledInstance(scaleWidth, scaleHeight,
                BufferedImage.SCALE_SMOOTH),
                0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    /**
     * 旋转
     * @param degree  旋转角度
     */
    public static BufferedImage spinImage(double degree, BufferedImage image) throws Exception {
        int swidth = 0; // 旋转后的宽度
        int sheight = 0; // 旋转后的高度
        int x; // 原点横坐标
        int y; // 原点纵坐标

        // 处理角度--确定旋转弧度
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;// 将角度转换到0-360度之间
        double theta = Math.toRadians(degree);// 将角度转为弧度

        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = image.getWidth();
            sheight = image.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = image.getWidth();
            swidth = image.getHeight();
        }
        else if(Math.abs((degree  %  360) - 360) < 10 || Math.abs(degree  %  360) < 10){
            swidth = image.getWidth();
            sheight = image.getHeight();
        }
        else {
            swidth = (int) (Math.sqrt(image.getWidth() * image.getWidth()
                    + image.getHeight() * image.getHeight()));
            sheight = (int) (Math.sqrt(image.getWidth() * image.getWidth()
                    + image.getHeight() * image.getHeight()));
        }

        x = (swidth / 2) - (image.getWidth() / 2);// 确定原点坐标
        y = (sheight / 2) - (image.getHeight() / 2);

        BufferedImage spinImage = new BufferedImage(swidth, sheight,
                image.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.black);
        gs.fillRect(0, 0, swidth, sheight);// 以给定颜色绘制旋转后图片的背景

        AffineTransform at = new AffineTransform();
        at.rotate(theta, swidth / 2, sheight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(image, spinImage);
        return spinImage;
    }

    /**
     * 目标图片定位
     */
    public static void fillData(BufferedImage image, int[][] data) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x <width ; x++) {
                data[x][y] = image.getRGB(x,y);
            }
        }
    }

    /**
     * 画线
     */
    public static BufferedImage drawLines(BufferedImage img, List<Line> lines, boolean drawAll) throws IOException {
        BufferedImage temp = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_BGR);
        Graphics g = temp.getGraphics();
        g.drawImage(img,0,0,img.getWidth(),img.getHeight(),null);
        g.setColor(Color.green);
        for(Line t : lines){
            if(drawAll){
                if(t.isTransverse()){
                    g.drawLine(0,t.getY1(),temp.getWidth(),t.getY2());
                }else{
                    g.drawLine(t.getX1(),0, t.getX2(), temp.getHeight());
                }
            }else{
                if(t.isTransverse()){
                    g.drawLine(t.getX1(),t.getY1(),t.getX2(),t.getY2());
                }else{
                    g.drawLine(t.getX1(),t.getY1(), t.getX2(), t.getY2());
                }
            }

        }
        g.dispose();
        return temp;
    }





    /**
     * 目标图片定位
     */
//    public static BufferedImage localtionTargetImage(int[][] data, boolean isMax) throws IOException {
//        List<Integer> lst = findLeftLine(data);
//        Integer[] ints = findLeftLine(data).toArray(new Integer[lst.size()]);
//
//        //按照数组中的信息 进行分段 连续在一起的数值为同一段
//        List continuousNumber =  ImageCommom.continuousNum(ints);
//        List continuousNumber1;
//
//        //如果分段数大于2  取出最长的一段连续的数值
//        if(isMax){
//            continuousNumber1 = ImageCommom.getMaxTarget(continuousNumber);
//        }else {
//            continuousNumber1 = ImageCommom.getMinTarget(continuousNumber);
//        }
//
//        List yzb = (List) continuousNumber1.get(0);
//        int fristNum = (int) yzb.get(0);
//        int lastNum = (int) yzb.get(yzb.size()-1);
//
//        image = image.getSubimage(0, fristNum, width, lastNum-fristNum);
//        Map<Integer,Integer> map =  getCoordinate(image);
//        int s = ImageCommom.getMapFirstKey(map);
//        return image;
//    }

    //读取图片内容
    public static String readImageContent(BufferedImage image,Boolean isEnglishRecognition,String isZqydno){
        ITesseract instance = TessData.getInstance();
        instance.setLanguage("eng");
        try {
            String result = instance.doOCR(image);
            if(isZqydno.equals("zqydno")){
                String str =  result.replaceAll("[^0-9A-Z-/]", "").trim();
                if(str.length()<5){
                    return  "invalidDistinguish";
                }
                return str;
            }else{
                String date =  result.replaceAll("[^0-9]", "").trim();
                if(date.length()<6){
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期
                    return  df.format(new Date());
                }
                return date;
            }

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "发生未知错误";
        }
    }
}
