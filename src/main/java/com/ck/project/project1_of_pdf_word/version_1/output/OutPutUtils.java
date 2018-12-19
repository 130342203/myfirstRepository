package com.ck.project.project1_of_pdf_word.version_1.output;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OutPutUtils {
    //todo 单张图像保存到本地
    public void saveImageToLocal(BufferedImage image, String localPath){
        try {
            ImageIO.write(image,"jpg",new File(localPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
