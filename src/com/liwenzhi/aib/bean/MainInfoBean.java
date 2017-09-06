package com.liwenzhi.aib.bean;

/**
 * 用户基本信息的bean
 */
public class MainInfoBean {
    String info;
    int pictureId;

    public MainInfoBean(String info, int pictureId) {
        this.info = info;
        this.pictureId = pictureId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String toString() {
        return "MainInfoBean{" +
                "info='" + info + '\'' +
                ", pictureId='" + pictureId + '\'' +
                '}';
    }
}
