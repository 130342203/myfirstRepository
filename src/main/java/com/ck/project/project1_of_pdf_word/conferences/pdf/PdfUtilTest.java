package com.ck.project.project1_of_pdf_word.conferences.pdf;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018-5-23 0023.
 * 测试类
 */

@Slf4j
public class PdfUtilTest {
    private static Logger logger = LoggerFactory.getLogger(PdfUtilTest.class);


    public static void main(String[] args) {
//        pdfToImage(args[0],Long.parseLong(args[1]));
    }

//    public static List<ImageContent> pdfToImage(String pdfPath ,Long companyId) {
//        PDDocument pdDocument = null;
//        List imageContentList = new ArrayList();
//        try {
//            // 读入PDF
//            pdDocument = PDDocument.load(new File(pdfPath));
//            List<PDPage> pages =  pdDocument.getDocumentCatalog().getAllPages();
//            ImageContent targetImages;
//            for (int i = 0; i <pages.size() ; i++) {
//                BufferedImage image = pages.get(i).convertToImage(BufferedImage.TYPE_3BYTE_BGR, 300);
//                if (companyId == 238) {
//                    targetImages = jxConvert(image);
//                } else if (companyId == 258) {
//                    targetImages = ytConvert(image);
//                } else {
//                    targetImages = jxConvert(image);
//                }
//
//                if (targetImages != null) {
//                    BufferedImage matnr = targetImages.getMatnr();
//                    if (matnr != null) {
//                        targetImages.setMatnrTxt(Image.readImageContent(matnr, true, "zqydno"));
//                        targetImages.setMatnr(null);
//                    }
//                    BufferedImage qyTime = targetImages.getQyTime();
//                    if (qyTime != null) {
//                        targetImages.setQyTimeText(Image.readImageContent(qyTime, true, "time"));
//                        targetImages.setQyTime(null);
//                    }
//                    targetImages.setPath(pdfPath);
//                    imageContentList.add(targetImages);
//                    logger.info("解析了" + i + "次-----件号" + targetImages.getMatnrTxt());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(pdDocument != null){
//                try {
//                    pdDocument.close();
//                } catch (Exception e){
//                }
//            }
//        }
//        return imageContentList;
//    }
}
