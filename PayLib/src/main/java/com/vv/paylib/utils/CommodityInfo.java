package com.vv.paylib.utils;

/**
 * Created by vvgool on 16-6-7.
 */
public class CommodityInfo {
    //商品名称
    public String mSubject;
    //商品详情
    public String mBody;
    //商品价格
    public String mPrice;

    public CommodityInfo(String mSubject, String mBody, String mPrice) {
        this.mSubject = mSubject;
        this.mBody = mBody;
        this.mPrice = mPrice;
    }
}
