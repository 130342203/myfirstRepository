package com.ck.project.project1_of_pdf_word.version_1.pdfToImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfToImages {
    public  List<BufferedImage> getImagesFromPdf(String pdfPath) {//根据pdf路径得到pdf的图像集合
        List<BufferedImage> imageList = new ArrayList<>();
        try {
            PDDocument pdDocument = PDDocument.load(new File(pdfPath));
            List<PDPage> pages =  (List<PDPage>) pdDocument.getDocumentCatalog().getAllPages();
            for (int i = 0; i <pages.size() ; i++) {
                BufferedImage image = pages.get(i).convertToImage(BufferedImage.TYPE_3BYTE_BGR, 300);
                imageList.add(image);
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return imageList;
    }
    //todo 方法：单张图像转为pdf

}
