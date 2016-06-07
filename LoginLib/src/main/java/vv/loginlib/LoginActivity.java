package vv.loginlib;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wiesen on 2016/6/6.
 */
public class LoginActivity extends AuthActivity {

    @Bind(R.id.ib_sina_login)
    ImageButton mIbSinaLogin;

    @Bind(R.id.ib_wechat_login)
    ImageButton mIbWeChatLogin;

    @Bind(R.id.bt_login)
    Button mBtLogin;

    @Bind(R.id.at_user_name)
    AutoCompleteTextView mAtUserName;

    @Bind(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.ib_sina_login)
    void onSinaLoginClick(){
        requestLimitOfPower(SHARE_MEDIA.SINA);
    }

    @OnClick(R.id.ib_wechat_login)
    void onWeChatClick(){
        requestLimitOfPower(SHARE_MEDIA.WEIXIN);
    }
}
