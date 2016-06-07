package vv.loginlib;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import vv.weight.dialog.ProgressDialogUtils;

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

    ProgressDialogUtils mProgressUtils;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mProgressUtils = ProgressDialogUtils.getInstance().createProgressDialog(this);
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

    @OnClick(R.id.bt_login)
    void onLoginClick(){
        String hostName = mAtUserName.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        if (checkHost(hostName,password)){
//            attemptToLogin(hostName,password);
        }
    }


    public boolean checkHost(String hostName,String password){
        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            mAtUserName.requestFocus();
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(hostName)) {
            mAtUserName.setError(getString(R.string.error_field_required));
            mAtUserName.requestFocus();
            cancel = true;
        } /*else if (!isEmailValid(email)) {
            mView.onHostNameErr(getString(R.string.error_invalid_email));
            cancel = true;
        }*/

        return !cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void attemptToLogin(String hostname,String password){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        }).subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressUtils.setCancelable(false)
                                .setTitle(getString(R.string.login))
                                .setMessage(getString(R.string.progress_login))
                                .show();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mProgressUtils.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }

}
