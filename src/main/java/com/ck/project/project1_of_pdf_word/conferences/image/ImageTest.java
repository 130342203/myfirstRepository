package com.ck.project.project1_of_pdf_word.conferences.image;

import com.ck.project.project1_of_pdf_word.conferences.image.bean.Coordinates;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.ImageContent;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by ron on 2018/5/22.
 */
public class ImageTest {

    private static Logger logger = LoggerFactory.getLogger(ImageTest.class);

    public static ImageContent jxConvert(BufferedImage img, int i){
        ImageContent imageContent = new ImageContent();
        BufferedImage matnr = null;
        BufferedImage time = null;
        int w = 0,h = 0;
        try {
            //原图黑化
            img =  Image.grayImage(img,500);
            w = img.getWidth();
            h = img.getHeight();
            //缩放原图
            if(w > 1000){
                img = Image.scaleImage(1000, h/(w/1000), img);
            }
            //找距离左边最近的线条 线条长度至少大于100
            List<Line> lines = ImageCommom.findLineByX(img);
            //找不到则无法继续
            if(lines == null || lines.size()==0){
                logger.info("获取不到线条长度大于100的线,第"+i+"张合同");
                return null;
            }
            //最左边的线坐标
            Line line = lines.get(0);
            //计算最左边的线的偏转角度
            double angle = ImageCommom.coordinateToAngle(line.getX1(),line.getY1(),line.getX2(),line.getY2());
            if(line.getX2()<line.getX1()){
                angle = -angle;
            }
            //旋转原图 将原图摆正
            if(angle != 0){
                img = Image.spinImage(angle, img);
            }

            //寻找缩放后的图片中的线条
            lines = ImageCommom.findRow(img);
            if(lines == null || lines.size() == 0){
                logger.info("获取不到缩放后的图片中的线条,第"+i+"张合同");
                return null;
            }
            //线条转化为坐标系
            Coordinates s = ImageCommom.convert2coordinate(lines);
            int targetWidth = img.getWidth()-1;
            int targetHeight = s.getYlist().get(s.getYlist().size()-1);
            if(targetHeight>1000){
                targetHeight = 857;
            }
            img = img.getSubimage(0, 0, targetWidth,targetHeight);
            //二值化
            img =  Image.twoValueImage(img,200);
//            ImageIO.write(img,"jpg",new File("F:\\null\\outFile\\"+i+"截取.jpg"));

            //寻找二值化后的图片中的线条
            lines = ImageCommom.findRow(img, 50 ,0, 20);

            //线条转化为坐标系
            s = ImageCommom.convert2coordinate(lines);
            for (int j = 0; j <s.getYlist().size() ; j++) {
                if(s.getYlist().get(0)<300){
                    s.getYlist().remove(0);
                }
            }
            int beginX = s.getXlist().get(1)+5;
            int beginY = s.getYlist().get(1)+5;
            int endX = Math.abs(s.getXlist().get(4)-s.getXlist().get(1)-10);
            int endY = Math.abs(s.getYlist().get(2)-s.getYlist().get(1));
            if(img.getWidth()-beginX<endX) {
                logger.info("截取Y目标超出长度,第" + i + "张合同");
                return null;
            }
            if(img.getHeight()-beginY<endY) {
                logger.info("截取Y目标超出长度,第" + i + "张合同");
                return null;
            }
            //获取件号
            matnr = img.getSubimage(beginX,beginY,endX,endY);
            imageContent.setMatnr(matnr);
//            ImageIO.write(matnr,"jpg",new File("F:\\null\\outFile\\"+i+"件号.jpg"));
            //获取合同号
            targetWidth =  s.getXlist().get(s.getXlist().size()-1)-150;
            targetHeight = s.getYlist().get(3);
            time = img.getSubimage(targetWidth, targetHeight, 150,50);
//            ImageIO.write(time,"jpg",new File("F:\\null\\outFile\\"+i+"合同号.jpg"));
            imageContent.setContract(time);
            //获取创建日期
            targetWidth = s.getXlist().get(0)+85;
            targetHeight = Math.abs(s.getYlist().get(0)-135);
            if(img.getWidth()-targetWidth<130||img.getHeight()-targetHeight<30){
                logger.info("截取X目标超出长度,第" + i + "张合同");
                return null;
            }
            time = img.getSubimage(targetWidth, targetHeight, 130,30);
            imageContent.setQyTime(time);
            //            ImageIO.write(time,"jpg",new File("F:\\null\\outFile\\"+i+"时间.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageContent;
    }

    public static ImageContent ytConvert(BufferedImage img){
        ImageContent imageContent= new ImageContent();
        BufferedImage zqydno = null;
        BufferedImage time = null;
        int w = 0,h = 0;
        try {
            w = img.getWidth();
            h = img.getHeight();
            //缩放
            if(w > 1000){
                img = Image.scaleImage(1000, h/(w/1000), img);
            }
            //黑化
            img =  Image.grayImage(img,350);
            //找距离左边最近的线条 线条长度至少大于100
            List<Line> lines = ImageCommom.findLineByX(img);
            if(lines == null || lines.size()==0){
                return null;
            }
            //最左边的线 坐标
            Line line = lines.get(0);
            int fx = line.getX2();
            int fy = line.getY2();
            //计算最左边的线的偏转角度
            double angle = ImageCommom.coordinateToAngle(line.getX1(),line.getY1(),line.getX2(),line.getY2());
            if(line.getX2()<line.getX1()){
                angle = -angle;
            }
            //旋转图片
            img = Image.spinImage(angle, img);
            //旋转后重新二值化
            img = Image.twoValueImage(img,200);

            img = img.getSubimage(0, line.getY2(), img.getWidth(), 350);
            //获取最左边线
            lines = ImageCommom.findLineByX(img,0,0,100 , 8);
            if(lines == null || lines.size() == 0){
                return null;
            }
            //第二段线 的坐标
            line = lines.get(lines.size()-1);
            //计算最左边的线的偏转角度
            double angle2 = ImageCommom.coordinateToAngle(line.getX1(),line.getY1(),line.getX2(),line.getY2());
            if(line.getX2()<line.getX1()){
                angle2 = -angle2;
            }
            if(line.getX2()-line.getX1()>10||line.getX2()-line.getX1()<0){
                //旋转图片
                img = Image.spinImage(angle2, img);
            }
            w = img.getWidth();
            h = img.getHeight();
            //截取第二段线 所在的表格
            img = img.getSubimage(0, 0,
                    Math.abs(w),  Math.abs(line.getY2()-line.getY1()));
            lines = ImageCommom.findRow(img, 50 ,0, 10);

//            ImageCommom.applicationDebug(img,lines, false,"F:\\null\\outFile\\"+i+"test.jpg");
            if(lines == null || lines.size() == 0){
                return null;
            }
            Coordinates s = ImageCommom.convert2coordinate(lines);
            //获取件号
            zqydno = img.getSubimage(s.getXlist().get(1)+3, s.getYlist().get(2)+3,
                    Math.abs(s.getXlist().get(2)-s.getXlist().get(1)),
                    Math.abs(s.getYlist().get(3)-s.getYlist().get(2)));
            imageContent.setMatnr(zqydno);
            //获取时间
            time = img.getSubimage(s.getXlist().get(7)+3, s.getYlist().get(2)+3,
                    Math.abs(s.getXlist().get(8)-s.getXlist().get(7)),
                    Math.abs(s.getYlist().get(3)-s.getYlist().get(2)));
            imageContent.setQyTime(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageContent;
    }

//    public static void main(String[] args){
//        String workspace = "/Users/ron/Downloads/imgwork/";
//        BufferedImage img = null;
//        try {
//
//            for(int i=0;i<3;i++){
//                img = ImageIO.read(new FileInputStream(workspace+"jbht-"+i+".jpg"));
//                ytConvert(img,workspace+"jbht-x"+i+".jpg");
//            }
//            for(int i=0;i<3;i++){
//                img = ImageIO.read(new FileInputStream(workspace+"jx-"+i+".jpg"));
//                jxConvert(img,workspace+"jx-x"+i+".jpg");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
