package com.ck.project.project1_of_pdf_word.version_1.CharacterLibrary;

import com.ck.project.project1_of_pdf_word.version_1.output.OutPutUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//系统字库
public class CharacterUtils {
    public void getCharactersFromSystem() throws IOException {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = graphicsEnvironment.getAvailableFontFamilyNames();
        Font[] fonts = graphicsEnvironment.getAllFonts();
        for (String name : fontNames) {

            System.out.println(name);
        }
        BufferedImage image = null;
        for (Font font :fonts){
            if (font.getName().equals("仿宋")){
                //image = convertFontToImage(128,128,font,"龙");
                writeFontOnImage("龙",256,256,font);
            }
        }
    }

    public static BufferedImage convertFontToImage(Integer width, Integer height, Font font,String str) {
        //字体转换为图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        //ImageIO.write(image, "png", outFile);// 输出png图片
        return image;
    }

    public static void writeFontOnImage(String str,int width,int height,Font font) throws IOException {
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        font.deriveFont(64f);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();
        //g2d.drawImage(image,0,0,image.getWidth(),image.getHeight(),null);
        // 画图
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, width, height);
        g2d.setColor(Color.red);
        //g2d.setPaint(Color.green);
        g2d.setFont(new Font("宋体",Font.PLAIN,50));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = image.getWidth() - 2 *getWatermarkLength(str,g2d);
        int y = image.getHeight() - 2 *getWatermarkLength(str,g2d);
        g2d.drawString(str,0,256);
        // 释放对象
        g2d.dispose();
        ImageIO.write(image, "jpg", new File("G:\\1.jpg"));
        //return image;

    }
    public static int getWatermarkLength(String str, Graphics2D g) {//获取字体的起笔位置
        return g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(), 0, str.length());
    }
}
