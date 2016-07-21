package vv.codelib.MVPBase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by vvgool on 2016/5/26.
 */
public abstract class BaseFragment<V extends BaseView,T extends BasePresenter> extends Fragment implements BaseView{
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.bindView((V)this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init(savedInstanceState);
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this,view);
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract T createPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter!= null){
            mPresenter.unBindView();
        }
    }


    @Override
    public Context getViewContext() {
        return getActivity().getApplicationContext();
    }
}
