package vv.weight.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Wissen on 16-6-7.
 */
public class ProgressDialogUtils {

    private ProgressDialog mProgressDialog;
    private static ProgressDialogUtils mProgressDialogUtils;

    public static ProgressDialogUtils getInstance(){
        if (mProgressDialogUtils == null){
            synchronized (ProgressDialogUtils.class){
                if (mProgressDialogUtils == null){
                    mProgressDialogUtils = new ProgressDialogUtils();
                }
            }
        }
        return mProgressDialogUtils;
    }

    public ProgressDialogUtils createProgressDialog(Context context){
        mProgressDialog = new ProgressDialog(context);
        return this;
    }

    public ProgressDialogUtils createProgressDialog(Context context, int theme){
        mProgressDialog = new ProgressDialog(context,theme);
        return this;
    }

    public ProgressDialogUtils setContentView(int layoutID){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.setContentView(layoutID);
        return this;
    }

    public ProgressDialogUtils setTitle(String title){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.setTitle(title.toString());
        return this;
    }

    public ProgressDialogUtils setMessage(String message){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.setMessage(message.toString());
        return this;
    }

    public ProgressDialogUtils setCancelable(boolean cancelable){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.setCancelable(cancelable);
        return this;
    }

    public void show(){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.show();
    }

    public void cancel(){
        if (mProgressDialog == null) throw new NullPointerException("ProgressDialog is null");
        mProgressDialog.cancel();
    }
}
