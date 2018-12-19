package com.ck.project.project1_of_pdf_word.conferences.image;

import com.ck.project.project1_of_pdf_word.conferences.filetype.FileType;
import com.ck.project.project1_of_pdf_word.conferences.filetype.FileTypeUtil;
import com.ck.project.project1_of_pdf_word.conferences.image.bean.ImageContent;
import com.ck.project.project1_of_pdf_word.conferences.pdf.PdfUtil;
import com.ck.project.project1_of_pdf_word.conferences.zip.ZipUtil;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018-5-24 0024.
 */


@Slf4j
public class ImageService {
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    public static List downloadFile(String fileUrl,String fileLocal,Long companyId) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        String zipName = System.currentTimeMillis()+".zip";
        String outPath =  fileLocal  + File.separator+ zipName;
        //读文件流
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outPath));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
        logger.info("解压压缩包------->时间："+ new Date()+"");
        //获取文件类型,判断是否为zip压缩包类型
        FileType fileType =  FileTypeUtil.getFileType(outPath);
        List<String> fileOfZip = new ArrayList<>();
        if(fileType.name().equals("ZIP")){
            //获取压缩包内文件,并解压zip压缩包到指定文件路径
            fileOfZip =  ZipUtil.decompressionZip(outPath,fileLocal);
        }else if(fileType.name().equals("RAR")){
            fileOfZip =  ZipUtil.decompressionRar(outPath,fileLocal);
        }else {
            System.out.println("支持zip和rar压缩包");
        }
        List imagesOfPdf = new ArrayList<>();
        logger.info("pdf转image,并解析image图片内容------->时间："+ new Date()+"");
        for (String pdfPath : fileOfZip) {
            List<ImageContent> image =  PdfUtil.pdfToImage(pdfPath,companyId);
            imagesOfPdf.addAll(image);
        }
        return imagesOfPdf;
    }
}
