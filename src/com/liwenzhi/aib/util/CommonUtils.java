package com.liwenzhi.aib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.liwenzhi.aib.app.App;

/**
 * 通用工具类
 */

public class CommonUtils {

    private static final String TAG = "CommonUtils-TAG";

    /**
     * toast some msg
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * check if network avalable
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * check if sdcard exist
     */
    public static boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 拆箱整形对象，如果为空返回 0
     */
    public static int getIntegerValue(Integer integer) {
        return integer == null ? 0 : integer;
    }

    /**
     * Log
     */
    //这里DEBUG的作用是，可以在程序完成后设置DEBUG的值为false，程序以后就不会在显示以前的打印信息
    public static boolean DEBUG = true;

    //各种Log打印
    public static void e(Object o) {
        if (DEBUG)
            Log.e(TAG, "打印：------      " + o.toString());
    }

    public static void e(int i) {
        if (DEBUG)
            Log.e(TAG, "打印：------      " + i);
    }

    public static void e(float i) {
        if (DEBUG)
            Log.e(TAG, "打印：------      " + i);
    }

    public static void e(boolean b) {
        if (DEBUG)
            Log.e(TAG, "打印：------      " + b);
    }

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getAppVersionName() {
        //获取packagemanager的实例
        PackageManager packageManager = App.getInstance().getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            CommonUtils.e("获取版本名发生错误：" + e.getMessage());
            return "2.0";
        }
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionName;
    }


    /*
 * 获取当前程序的版本号
 */
    public static int getAppVersionCode() {
        //获取packagemanager的实例
        PackageManager packageManager = App.getInstance().getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            CommonUtils.e("获取版本号发生错误：" + e.getMessage());
            return 2;
        }
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionCode;
    }

}


