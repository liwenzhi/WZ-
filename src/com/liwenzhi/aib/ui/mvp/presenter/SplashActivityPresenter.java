package com.liwenzhi.aib.ui.mvp.presenter;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import com.liwenzhi.aib.ui.mvp.view.SplashActivityView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 5/15/17
 * Time: 3:30 PM
 * 欢迎界面视图与数据桥梁类
 */
public class SplashActivityPresenter extends BasePresenter<SplashActivityView> {


    public SplashActivityPresenter() {
    }


//    /**
//     * 检查版本数据
//     */
//    public void checkNewVersion() {
//        CommonUtils.e("checkNewVersion：");
//        if (!checkNetwork()) {
//            mView.showNetworkError();
//            mView.setRemindString("网络连接失败，请稍后再试");
//            return;
//        }
//        mView.showLoading();
//        splashActivityMode.checkVersion(new SplashActivityMode.OnCheckNewVersionListener() {
//            @Override
//            public void hasVersionData(VersionResponse versionResponse) {
//                mView.hideLoading();
//                //判断是否最新版本，决定是否跟新
//                judgeUpVersion(versionResponse);
//            }
//
//            @Override
//            public void hasNotVersionData(Throwable e) {
//                mView.hideLoading();
//                mView.setRemindString("服务器连接失败");
//                CommonUtils.e("服务器版本书记处出错：" + e.getMessage());
//            }
//
//            @Override
//            public void checkVersionErr(Throwable e) {
//                mView.setRemindString("网络/服务器连接失败");
//                CommonUtils.e("网络数据出错：" + e.getMessage());
//                mView.hideLoading();
//            }
//        });
//
//
//    }
//
//    private void judgeUpVersion(VersionResponse versionResponse) {
//        String version = versionResponse.getVersion();
//        String url = versionResponse.getUrl();
//
//        String appVersionName = CommonUtils.getAppVersionName();
//        CommonUtils.e("服务器中的版本名：" + version + "，APP中的版本名：" + appVersionName + ",url:" + url);
//        //版本名不相同，更新
//        if (version != null && !version.equals(appVersionName)) {  //可以更新的情况
//            CommonUtils.e("版本更新");
//            //根据URL获取新版本的文件
//            showDialogUpdate(versionResponse);
//        } else {//不用更新就到主页面
//            CommonUtils.e("版本不更新");
//            mView.gotoMainActivity();
//        }
//
//        CommonUtils.e("versionResponse" + versionResponse);
//
//    }
//
//
//    /**
//     * 提示版本更新的对话框
//     */
//    private void showDialogUpdate(final VersionResponse versionResponse) {
//        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
//        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getActivityContext());
//        // 设置提示框的标题
//        builder.setTitle("版本升级").
//                // 设置提示框的图标
//                        setIcon(R.drawable.icon_up_version).
//                // 设置要显示的信息
//                        setMessage("发现新版本！请及时更新").
//                // 设置确定按钮
//                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        loadNewVersionProgress(versionResponse);//下载最新的版本程序
//                    }
//                }).
//
//                // 设置取消按钮,关闭页面
//                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mView.finishActivity();//关闭页面
//                        //                       mView.gotoMainActivity();//跳转到主页面页面
//                    }
//                });
//
//        // 生产对话框
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);  //不能失去焦点就关闭
//        // 显示对话框
//        alertDialog.show();
//
//
//    }
//
//    /**
//     * 下载新版本
//     */
//    private void loadNewVersionProgress(VersionResponse versionResponse) {
//        final String uri = versionResponse.getUrl();
//        final ProgressDialog pd;    //进度条对话框
//        pd = new ProgressDialog(mView.getActivityContext());
//        pd.setCancelable(false);//设置不能失去焦点和回退键后关闭
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pd.setMessage("正在下载更新");
//        pd.show();
//        //启动子线程下载任务
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    File file = getFileFromServer(uri, pd);
//                    sleep(2000);
//                    installApk(file);
//                    pd.dismiss(); //结束掉进度条对话框
//                } catch (Exception e) {
//                    //下载apk失败
//                    if (mView != null)
//                        mView.setRemindString("下载新版本失败" + e.getMessage());
//                }
//            }
//        }.start();
//
//    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            int processNumberAll = conn.getContentLength();
            pd.setMax(100);
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                int processNumber = 100 * total / processNumberAll;
                pd.setProgress(processNumber);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mView.getActivityContext().startActivity(intent);
        mView.finishActivity();
    }
}
