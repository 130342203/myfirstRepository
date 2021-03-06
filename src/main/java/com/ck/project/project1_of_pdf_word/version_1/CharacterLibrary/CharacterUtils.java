package com.ck.project.project1_of_pdf_word.version_1.CharacterLibrary;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
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
            System.out.print(name+"/");
        }
        return ;
    }

    public static BufferedImage writeFontOnImage(String str,int width,int height,Font font) throws IOException {
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Font newfont = font.deriveFont(Font.BOLD,32f);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();
        //g2d.drawImage(image,0,0,image.getWidth(),image.getHeight(),null);//将图片画到图片上
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, width, height);
        g2d.setColor(Color.red);
        //g2d.setPaint(Color.green);
        //int fontSize = 8;
        //g2d.setFont(new Font("宋体",Font.BOLD,fontSize));
        //g2d.setFont(newfont);
        FontMetrics fm =g2d.getFontMetrics();
        Rectangle2D rc = fm.getStringBounds(str,g2d);
        System.out.println("宽度"+rc.getWidth());
        System.out.println("高度"+rc.getHeight()+"leading:"+fm.getLeading()+"ascent:"+fm.getAscent()+"descent:"+fm.getDescent());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = image.getWidth() - 2 *getWatermarkLength(str,g2d);
        int y = image.getHeight() - 2 *getWatermarkLength(str,g2d);
        x = (image.getWidth()/2) - ((int) rc.getWidth()/2);
        y = (image.getHeight()/2) + ((int) rc.getHeight()/2);
        System.out.println("图像宽高：（"+image.getWidth()+","+image.getHeight()+")"+"-字体宽度："+fm.stringWidth(str)+"-高度："+rc.getHeight()+"-起笔坐标：("+x+":"+y+")");
        g2d.setFont(new Font("宋体",Font.PLAIN,8));
        //g2d.setFont(newfont);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm1 = g2d.getFontMetrics();
        //Rectangle2D rc = newfont.getStringBounds(str)
        int x1 = (image.getWidth()/2) - (fm.stringWidth(str)/2);
        int y1 = image.getHeight()/2 +fm.getHeight()/2;
        g2d.drawString(str,x,y);
        g2d.drawLine(0,0,0,250);
        g2d.drawLine(255,0,255,255);
        // 释放对象
        g2d.dispose();
        //ImageIO.write(image, "jpg", new File("G:\\2.jpg"));
        return image;
    }
    public static int getWatermarkLength(String str, Graphics2D g) {//获取字体的起笔位置
        return g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(), 0, str.length());
    }
}
