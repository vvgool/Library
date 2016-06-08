package com.vv.paylib;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.vv.paylib.utils.CommodityInfo;
import com.vv.paylib.utils.MerchantInfo;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by vvgool on 16-6-7.
 */
public class TestActivity extends PayActivity {

    @Bind(R.id.et_commodity_name)
    EditText mCommodityName;

    @Bind(R.id.et_commodity_label)
    EditText mCommodityLabel;

    @Bind(R.id.et_commodity_price)
    EditText mCommodityPrice;

    @Bind(R.id.bt_pay)
    Button  mPay;

    @Bind(R.id.bt_h5_pay)
    Button  mH5Pay;

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @OnClick(R.id.bt_pay)
    void Pay(){
        String name = mCommodityName.getText().toString().trim();
        String label = mCommodityLabel.getText().toString().trim();
        String price = mCommodityPrice.getText().toString().trim();
        CommodityInfo commodityInfo = new CommodityInfo(name,label,price);
        pay(new MerchantInfo(),commodityInfo);
    }

    @OnClick(R.id.bt_h5_pay)
    void H5Pay(){
        h5Pay();
    }
}
