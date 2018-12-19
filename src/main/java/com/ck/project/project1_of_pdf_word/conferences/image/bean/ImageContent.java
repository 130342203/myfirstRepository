package com.ck.project.project1_of_pdf_word.conferences.image.bean;

import java.awt.image.BufferedImage;

/**
 * Created by Administrator on 2018-5-30 0030.
 */
public class ImageContent {
    private String path;

    private BufferedImage contract;
    private BufferedImage matnr;
    private BufferedImage qyTime;
    private BufferedImage returnTime;

    private String matnrText;
    private String contractText;
    private String qyTimeText;
    private String returnTimeText;

    public BufferedImage getContract() {
        return contract;
    }

    public void setContract(BufferedImage contract) {
        this.contract = contract;
    }

    public String getMatnrText() {
        return matnrText;
    }

    public void setMatnrText(String matnrText) {
        this.matnrText = matnrText;
    }

    public String getContractText() {
        return contractText;
    }

    public void setContractText(String contractText) {
        this.contractText = contractText;
    }

    public String getQyTimeText() {
        return qyTimeText;
    }

    public void setQyTimeText(String qyTimeText) {
        this.qyTimeText = qyTimeText;
    }

    public String getReturnTimeText() {
        return returnTimeText;
    }

    public void setReturnTimeText(String returnTimeText) {
        this.returnTimeText = returnTimeText;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BufferedImage getMatnr() {
        return matnr;
    }

    public void setMatnr(BufferedImage matnr) {
        this.matnr = matnr;
    }

    public BufferedImage getQyTime() {
        return qyTime;
    }

    public void setQyTime(BufferedImage qyTime) {
        this.qyTime = qyTime;
    }

    public BufferedImage getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(BufferedImage returnTime) {
        this.returnTime = returnTime;
    }
}
