package com.ck.controller;

import com.ck.framework.Result;
import com.ck.project.project1_of_pdf_word.conferences.image.ImageService;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.ImageContent;
import com.ck.project.project1_of_pdf_word.conferences.pdf.PdfUtil;
import com.ck.project.project1_of_pdf_word.version_1.CharacterLibrary.CharacterUtils;
import com.ck.project.project1_of_pdf_word.version_1.output.OutPutUtils;
import com.ck.project.project1_of_pdf_word.version_1.pdfToImage.PdfToImages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class PdfToWordController {


    @RequestMapping("/test/ptw")
    @ResponseBody
    Result convertPdfToWord() throws IOException {
        //todo 1、pdf转image；2、image识别（内含图像处理：难点所在）
        String pdfPath = "G:\\testPdfWord\\2018年财务年终关账通知.pdf";
        String savePath ="G:\\testPdfWord\\images";
        List<BufferedImage> images = new PdfToImages().getImagesFromPdf(pdfPath);
        OutPutUtils outPutUtils = new OutPutUtils();
        for (int i=0;i<images.size();i++){
            ImageIO.write(images.get(i),"jpg",new File(savePath+File.separator+String.valueOf(i)+".jpg"));

        }
        new CharacterUtils().getCharactersFromSystem();
        return new Result().buildSuccess();
    }
}
