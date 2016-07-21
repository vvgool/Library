package vv.weight.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Wiesen on 16-6-7.
 */
public class ProgressDialogUtils {

    private ProgressDialog mProgressDialog;

    private static class InstanceHolder{
        private static ProgressDialogUtils mInstance = new ProgressDialogUtils();
    }

    public static ProgressDialogUtils getInstance(){
        return InstanceHolder.mInstance;
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
