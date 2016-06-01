package vv.codelib.MVPBase;

import android.content.Context;

/**
 * Created by vvgool on 2016/5/23.
 */
public interface BaseView {
    /**
     * 加载时显示加载框
     */
    void showLoading();

    /**
     * 加载完成时隐藏加载框
     */
    void hideLoading();

    /**
     * 显示提示消息
     */
    void showToastMessage(String message);

    /**
     * 获取Context对象
     */
    Context getViewContext();

}
