package vv.library.basicUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;

/**
 * Created by wiesen on 16-10-11.
 */
public abstract class LoadDexActivity extends Activity{

    public abstract int getContentViewID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
    }

    class LoadDexTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            MultiDex.install(getApplication());
            MultiApplication.getInstance().installSuccess();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            finish();
            System.exit(0);
        }
    }
}
