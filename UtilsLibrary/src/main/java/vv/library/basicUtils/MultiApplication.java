package vv.library.basicUtils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created by wiesen on 16-10-11.
 */

public class MultiApplication extends Application {
    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";
    private static MultiApplication sMultiApplication;



    @Override
    public void onCreate() {
        super.onCreate();
        sMultiApplication = this;
        if (isDexOptProcess()){
            return;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!isDexOptProcess() && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH ){
            if (needWaitForDexOpt(base)){
                waitForDexOptProcess(base);
            }
            MultiDex.install(this);
        }else {
            return;
        }
    }

    public static MultiApplication getInstance(){
        return sMultiApplication;
    }

    private void waitForDexOptProcess(Context base) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(getPackageName(),LoadDexActivity.class.getName());
        intent.setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);

        long startWait = System.currentTimeMillis();
        long waitTime = 10*1000;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1){
            waitTime = 20 * 1000;
        }
        while (needWaitForDexOpt(base)){
            try {
                long nowWait = System.currentTimeMillis() - startWait ;
                if (nowWait >= waitTime){
                    return;
                }
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    private boolean needWaitForDexOpt(Context base) {
        String flag  = get2thDexSHA1(base);
        SharedPreferences sharedPreferences = base.getSharedPreferences(getPackageInfo(this).
                versionName,MODE_MULTI_PROCESS);
        String saveValue = sharedPreferences.getString(KEY_DEX2_SHA1,"");
        return !flag.equals(saveValue);
    }


    /**
     * get classes.dex file signature
     * @param base
     * @return
     */
    private String get2thDexSHA1(Context base) {
        ApplicationInfo applicationInfo = base.getApplicationInfo();
        String sourceDir = applicationInfo.sourceDir;
        try {
            JarFile jarFile = new JarFile(sourceDir);
            Manifest manifest = jarFile.getManifest();
            Map<String, Attributes> entries = manifest.getEntries();
            Attributes attributes = entries.get("classes2.dex");
            return attributes.getValue("SHA1-Digest");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * need to set process=":mini"  at AndroidManifest.xml in LoadDexActivity
     * @return
     */
    private boolean isDexOptProcess() {
        String curProcessName = getCurProcessName(this);
        if (!TextUtils.isEmpty(curProcessName) && curProcessName.contains(":mini")){
            return true;
        }
        return false;
    }


    public static String getCurProcessName(Context context){
        try {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcessInfo: activityManager.getRunningAppProcesses()){
                if (appProcessInfo.pid == pid){
                    return appProcessInfo.processName;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context){
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new PackageInfo();
    }

    public void installSuccess(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getPackageInfo(this).versionName,MODE_MULTI_PROCESS);
        sharedPreferences.edit().putString(KEY_DEX2_SHA1,get2thDexSHA1(this)).commit();

    }
}
