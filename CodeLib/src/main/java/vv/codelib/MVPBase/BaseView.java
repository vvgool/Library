package vv.codelib.MVPBase;

import android.content.Context;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import rx.Observable;

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

    <V> Observable.Transformer<V, V> bind();

    <V> Observable.Transformer<V, V> bindUntil(FragmentEvent event);

    <V> Observable.Transformer<V, V> bindUntil(ActivityEvent event);
}
