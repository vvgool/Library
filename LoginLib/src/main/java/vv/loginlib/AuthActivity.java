package vv.loginlib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import vv.codelib.NormalBase.NormalBaseActivity;

public abstract class AuthActivity extends NormalBaseActivity {

    private UMShareAPI mShareAPI;



    protected void init(Bundle savedInstanceState){
        initOtherParts();
    }


    /**
     * 初始化友盟第三方登录
     */
    protected void initOtherParts(){
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("200209850","b50577c30fc804fd810dc04f7f845082");
        mShareAPI = UMShareAPI.get(this);
    }



    /**
     * 申请第三方登录授权
     * @param platform 登录平台
     */
    protected void requestLimitOfPower(SHARE_MEDIA platform){
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }

    /**
     * 获取用户信息
     * @param platform
     */
    protected void getPlatformInfo(SHARE_MEDIA platform){
        mShareAPI.getPlatformInfo(this,platform,umPlatformInfoListener);
    }
    /**
     * 用户信息获取监听
     */
    private UMAuthListener umPlatformInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            showToast("Authorize succeed");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("Authorize cancel");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 请求授权监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            getPlatformInfo(platform);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("Authorize cancel");
        }
    };


    /**
     * 取消授权
     * @param platform 第三方平台
     */
    private void requestDelLimitOfPower(SHARE_MEDIA platform){
        mShareAPI.deleteOauth(this, platform, umdelAuthListener);
    }

    /**
     * 取消授权监听
     */
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
             showToast("Authorize succeed");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("Authorize cancel");
        }
    };


    protected Context getContext(){
        return getApplicationContext();
    }

    protected void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
