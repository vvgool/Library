package vv.codelib.MVPBase;

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


}
