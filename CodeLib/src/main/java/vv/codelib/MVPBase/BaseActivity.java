package vv.codelib.MVPBase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by vvgool on 2016/5/23.
 */
public abstract class BaseActivity<V extends BaseView,T extends BasePresenter> extends AppCompatActivity implements BaseView{

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.bindView((V)this);
        }

        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取布局控件id
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter!= null){
            mPresenter.unBindView();
        }
    }

    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }
}
