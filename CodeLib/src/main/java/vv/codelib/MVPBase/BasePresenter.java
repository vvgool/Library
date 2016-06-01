package vv.codelib.MVPBase;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import rx.Observable;

/**
 * Created by vvgool on 2016/5/23.
 */
public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    /**
     * 将Presenter 与 view绑定
     * @param view
     */
    protected void bindView(V view){
        this.mView = view;
        onViewBind();
    }

    protected void onViewBind(){}

    /**
     * 解除绑定
     */
    protected void unBindView(){
        this.mView = null;
    }

    protected <T> Observable.Transformer<T, T> bindToLifeCycle() {
        return mView.bind();
    }

    protected <T> Observable.Transformer<T, T> bindUntilEvent(ActivityEvent event) {
        return mView.bindUntil(event);
    }

    protected <T> Observable.Transformer<T, T> bindUntilEvent(FragmentEvent event) {
        return mView.bindUntil(event);
    }
}
