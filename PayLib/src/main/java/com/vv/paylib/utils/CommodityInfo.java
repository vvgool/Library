package com.vv.paylib.utils;
/**
 *@Desc {商品信息}
 *@Author Wiesen Wang
 *@Email vv_gool@163.com
 *@Time  16-6-8
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
