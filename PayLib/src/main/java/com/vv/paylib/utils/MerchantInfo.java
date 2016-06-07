package com.vv.paylib.utils;

/**
 * Created by vvgool on 16-6-7.
 */
public class MerchantInfo {
    //商户PID
    public String mPartner;

    //商户收款帐号
    public String mSeller;

    //商户私钥，pkcs8格式
    public String mRsaPrivate;


    public MerchantInfo(String mPartner, String mSeller, String mRsaPrivate) {
        this.mPartner = mPartner;
        this.mSeller = mSeller;
        this.mRsaPrivate = mRsaPrivate;
    }
}
