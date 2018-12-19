package com.ck.project.project1_of_pdf_word.conferences.image.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ron on 2018/5/24.
 */
public class Coordinates {

    List<Integer> xlist = new ArrayList<>();

    List<Integer> ylist = new ArrayList<>();

    public List<Integer> getXlist() {
        return xlist;
    }

    public void setXlist(List<Integer> xlist) {
        this.xlist = xlist;
    }

    public List<Integer> getYlist() {
        return ylist;
    }

    public void setYlist(List<Integer> ylist) {
        this.ylist = ylist;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "\nxlist=" + xlist +
                ", \nylist=" + ylist +
                '}';
    }
}
