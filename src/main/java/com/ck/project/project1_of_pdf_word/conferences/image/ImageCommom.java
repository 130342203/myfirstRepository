package com.ck.project.project1_of_pdf_word.conferences.image;

import com.ck.project.project1_of_pdf_word.conferences.image.bean.Coordinates;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.Line;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018-5-21 0021.
 */
public class ImageCommom {


    public static double coordinateToAngle(int xPointA,int yPointA,int xPointB,int yPointB){
        int a = Math.abs(xPointB-xPointA);
        int b = Math.abs(yPointA-yPointB);
        double c = Math.sqrt(a*a + b*b);
        double angle = Math.atan(a/(double)b);//最终角度
        angle = angle /0.017453293;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String angleString = decimalFormat.format(angle);
        angle= Double.valueOf(angleString);
        return angle;
    }

    /**
     * 用于寻找图片最左侧的边缘
     * 从图片的左上 开始寻找
     * 逐行寻找 每行第一个发现的白点
     * */
    public static List<Line> findLineByX(BufferedImage img){
        return findLineByX(img,0,0,80 , 5);
    }
    public static List<Line> findLineByX(BufferedImage img, int offsetX, int offsetY, int length, int threshold){
        List<Line> lst =  new ArrayList<>();
        Line line = null;
        int w = img.getWidth(), h=img.getHeight(), lstX=0,lstY=0;
        if(img.getWidth() == 0){
            return null;
        }
        for (int y = offsetY; y < h; y++) {
            for (int x = offsetX; x < w ; x++) {
                if (isTarget(img.getRGB(x,y))) {
                    if(line == null){
                        line = new Line();
                        line.setX1(x);
                        line.setY1(y);
                    }else{
                        //与上个白点 x坐标数值相差5
                        if(Math.abs(lstX -x) > threshold || Math.abs(lstY - y) > threshold ){
                            line.setX2(lstX);
                            line.setY2(lstY);
                            //线条长度大于length
                            if(line.length() > length){
                                lst.add(line);
                            }
                            line = null;
                        }
                    }
                    lstX = x;
                    lstY = y;
                    break;
                }
            }
        }
        return lst;
    }


    /**
     * 从图片的左上 开始寻找
     * 逐行寻找 每行第一个发现的白点
     * threshold 同轴内多长长度的像素认定为线条（自动检索周围1个像素 是否有偏离）
     * pixOffset 同一线内允许间隔多长
     * nextLinePixOffset 下一个线条 同轴内与上一线条 偏差超过 多少像素 认定与前一线条为同一线条
     * */
    public static List<Line> findRow(BufferedImage img){
        return findRow(img, 50 ,5, 5);
    }
    public static List<Line> findRow(BufferedImage img, int threshold , int pixOffset, int nextLinePixOffset){
        List<Line> lst =  new ArrayList<>();
        int w = img.getWidth(), h=img.getHeight(),fx=0,fy=0;
        if(img == null){
            return null;
        }
        Line line = null;
        int score = 0;
        int offset = 0;
        int t2 = 0,t3 = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w ; x++) {
                t2 = y+1 > h-1 ? h-1 : y+1;
                t3 = y-1 < 0 ? 0 : y-1;
                if (isTarget(img.getRGB(x,y)) || isTarget(img.getRGB(x,t2)) || isTarget(img.getRGB(x,t3))) {
                    if(score == 0){
                        fx = x;
                        fy = y;
                    }
                    score ++ ;
                    offset = 0;
                }else{
                    if(score > threshold){
                        if(line!= null && Math.abs(line.getY2() -y) < nextLinePixOffset
                                ){
                            if(x > line.getX2()){
                                line.setX2(x);
                                line.setY2(y);
                            }
                        }else{
                            line = new Line();
                            line.setX1(fx);
                            line.setY1(fy);
                            line.setX2(x);
                            line.setY2(y);
                            line.setTransverse(true);
                            lst.add(line);
                        }
                        score = 0;
                    }
                    if(offset < pixOffset){
                        offset ++;
                        continue;
                    }else{
                        score = 0;
                    }
                }
            }
        }
        score = offset = 0;
        for (int x = 0; x < w ; x++) {
            for (int y = 0; y < h; y++) {
                t2 = x+1 > w-1 ? w-1 : x+1;
                t3 = x-1 < 0 ? 0 : x-1;
                if (isTarget(img.getRGB(x,y)) || isTarget(img.getRGB(t2,y)) || isTarget(img.getRGB(t3,y))) {
                    if(score==0){
                        fx = x;
                        fy = y;
                    }
                    score ++ ;
                    offset = 0;
                }else{
                    if(score > threshold){
                        if(line!= null
                                && Math.abs(line.getX2() -x) < nextLinePixOffset){
                            if(y > line.getY2()){
                                line.setX2(x);
                                line.setY2(y);
                            }
                        }else{
                            line = new Line();
                            line.setX1(fx);
                            line.setY1(fy);
                            line.setX2(x);
                            line.setY2(y);
                            line.setTransverse(false);
                            lst.add(line);
                        }
                        score = 0;
                    }
                    if(offset < pixOffset){
                        offset ++;
                        continue;
                    }else{
                        score = 0;
                    }
                }
            }
        }
        for(Line t :lst){
            if(t.isTransverse()){
                if(t.getY2() == 0){
                    t.setY2(t.getY1());
                }
                if(t.getX2() == 0){
                    t.setX2(w);
                }
            }else{
                if(t.getY2() == 0){
                    t.setY2(h);
                }
                if(t.getX2() == 0){
                    t.setX2(t.getX1());
                }
            }

        }
        return lst;
    }

    public static Coordinates convert2coordinate(List<Line> lines ){
        Coordinates s = new Coordinates();
        for(Line t :lines){
            if(t.isTransverse()){
                s.getYlist().add(t.getY1());
            }else{
                s.getXlist().add(t.getX1());
            }
        }
        Collections.sort(s.getYlist());
        Collections.sort(s.getXlist());
        return s;
    }


    public static boolean isTarget(int c){
        return c >= -1907998;
    }

    @Deprecated
    public  static void applicationDebug(BufferedImage img, List<Line> lines, boolean drawAll, String outPath){
        try {
            if(lines != null){
                img = Image.drawLines(img, lines, drawAll);
            }
            ImageIO.write(img,  "jpg", new File(outPath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
//
//
//    public static List<Line> findLineByX2(int[][] data,int threshold){
//        List<Line> lst =  new ArrayList<>();
//        Line line = null;
//        int lstX=0,lstY=0,t1=0,t2=0;
//        if(data.length == 0){
//            return null;
//        }
//        for (int l = 0; l < data.length ; l++) {
//            for (int y = 0; y < data[0].length; y++) {
//                //阈值控制
//                t1 =l-(threshold/2);
//                t2 =l+(threshold/2);
//                if(t1 <0) {
//                    t1 = 0;
//                }
//                for (int x = t1; x < t2 ; x++) {
//                    if (data[x][y] == Color.WHITE.getRGB()) {
//                        if(line == null){
//                            line = new Line();
//                            line.setX1(x);
//                            line.setY1(y);
//                        }else{
//                            //与上个白点 x坐标数值相差5
//                            if(Math.abs(lstX -x) > 5 || Math.abs(lstY - y) > 5 ){
//                                line.setX2(lstX);
//                                line.setY2(lstY);
//                                //线条长度大于100
//                                if(line.length() > 100){
//                                    lst.add(line);
//                                }
//                                line = null;
//                            }
//                        }
//                        lstX = x;
//                        lstY = y;
//                        break;
//                    }
//                }
//            }
//        }
//    }

}
