package com.vv.paylib.utils;

/**
 *@Desc {商户信息描述}
 *@Author Wiesen Wang
 *@Email vv_gool@163.com
 *@Time  16-6-8
 */
public class MerchantInfo {
    //商户PID
    public String mPartner ="2088702738495782";

    //商户收款帐号
    public String mSeller = "15623880929";

    //商户私钥，pkcs8格式
    public String mRsaPrivate="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPG3z7U19XQu7EoT" +
            "58fBEnmoORjxaoi8znDGz1UIUNl42KTWwbMvGgvDXivyjccXDD46lOYzw/nPDa5J" +
            "sywD66bt7B8d02AsDox79vj+YvEX5MJfR7iavPGdqMJvdiPAoE6ef/KBX/93ElQm" +
            "36WbjMQdtHdCr7dVSq6IDg7/6UojAgMBAAECgYEA2zoZwUFqF/DCz8DT88Cxb/NZ" +
            "rbyvIcsT9QbrXjTHlyXaX06DqW7r0YVksLkxS0MlEH9zMoH6mWPoBY3EWgRIQX+5" +
            "KAeruBrJxpPKmPGE5ehPN0s5YyMpYKvDJXal73sAPCbnHhoyZ08Tc1XBtl8+Vp1Q" +
            "nK4bvxkIkDb14mElytkCQQD8v6ojKa3vDWReoLItvkd7yX8mJHm0gvxFBTwNMWHE" +
            "t6z0mrMbD2k5YeaTTepznCHY3kb7c5DtlX/VQq6aS33NAkEA9NPSPgdcM2RW7bXN" +
            "Iotq8WaJYII+A5fayOZdAgzGkARR9F/K+1h1MRjDBVdR4brDaG/Ud++bM5l99x+h" +
            "tiR3rwJABO+5YvZtbz3TuAVrdXlB7CkDUrtjpSLpym9PvPI59p2fwydCWET0ySog" +
            "VsAu1BM4lgPBXyfirME+veuPfXP8AQJACND36TPqpg8e8kBxuqS6nSbMG6WVBhwZ" +
            "zNI+1ywOmnENYndwn8x3q+TwYOjcEfzfvIsKY6dFN353gsao1B5MEQJATWgzjPyN" +
            "5An/kA8G/vbSf/iG0ZO/uPAXVREiSGRrLPxLGfVi/lXuFIvVUdzyGTmk/3pPjJxj" +
            "9Q5afE219V2hBQ==";


    public MerchantInfo(){}

    public MerchantInfo(String mPartner, String mSeller, String mRsaPrivate) {
        this.mPartner = mPartner;
        this.mSeller = mSeller;
        this.mRsaPrivate = mRsaPrivate;
    }
}
