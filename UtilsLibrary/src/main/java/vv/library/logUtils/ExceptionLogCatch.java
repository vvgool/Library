package vv.library.logUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionLogCatch implements Thread.UncaughtExceptionHandler {
    public static final String TAG = ExceptionLogCatch.class.getCanonicalName();

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static ExceptionLogCatch mExceptionLogCatch;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> mInfoMap = new HashMap<String, String>();

    private String mPath;

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat mFileFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private static boolean mIsOnlyException = true;


    /**
     * 获取实例 ，单例模式
     */
    public static ExceptionLogCatch getInstance() {
        if (mExceptionLogCatch == null){
            mExceptionLogCatch = new ExceptionLogCatch();
        }
        return mExceptionLogCatch;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context,String path) {
        mContext = context;
        mPath = path;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }

            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run()
            {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常。", Toast.LENGTH_LONG)
                        .show();
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息
        collectDeviceInfo(mContext);

        // 保存日志文件
        String str = saveCrashInfo2File(ex);
        Log.e(TAG, str);

        return false;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfoMap.put("versionName", versionName);
                mInfoMap.put("versionCode", versionCode);
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occurred when collect package info", e);
        }
        if (!mIsOnlyException) {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    mInfoMap.put(field.getName(), field.get(null).toString());
                    Log.d(TAG, field.getName() + " : " + field.get(null));
                } catch (Exception e) {
                    Log.e(TAG, "an error occurred when collect crash info", e);
                }
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        String time = mFormatter.format(date);
        sb.append("Time:"+time+"\n");
        for (Map.Entry<String, String> entry : mInfoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("[" + key + ": " + value + "]\n");
        }

        sb.append("\n" + getStackTraceString(ex)+"\n\n");

        try {


            String fileName = "Log-" + mFileFormatter.format(date) + ".txt";


            File cacheDir = new File(mPath + File.separator + "Log");
            if (!cacheDir.exists())
                cacheDir.mkdir();

            File filePath = new File(cacheDir + File.separator + fileName);

            FileOutputStream fos = new FileOutputStream(filePath,true);
            fos.write(sb.toString().getBytes());
            fos.close();

            return fileName;
        }
        catch (Exception e) {
            Log.e(TAG, "an error occurred while writing file...", e);
        }
        return null;
    }

    /**
     * 获取捕捉到的异常的字符串
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "UnknownHostException";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 是否只捕获异常不捕获设备信息
     * @param isOnlyException
     */
    public static void isOnlyException(boolean isOnlyException){
       mIsOnlyException = isOnlyException;
    }
}
