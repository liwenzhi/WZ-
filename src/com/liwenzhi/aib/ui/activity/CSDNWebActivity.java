package com.liwenzhi.aib.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.ui.fragment.NormalTitleBarFragment;
import com.liwenzhi.aib.ui.mvp.presenter.CSDNWebActivityPresenter;
import com.liwenzhi.aib.ui.mvp.view.CSDNWebActivityView;
import com.liwenzhi.aib.util.CommonUtils;

/**
 * 跳转到wenzhi的CSDN博客页面
 */
public class CSDNWebActivity extends BaseActivity<CSDNWebActivityView, CSDNWebActivityPresenter> implements CSDNWebActivityView {
    @Override
    protected CSDNWebActivityPresenter initPresenter() {
        return new CSDNWebActivityPresenter();
    }

    //定义布局内的控件
    private WebView webView;
    private String url = "http://blog.csdn.net/wenzhi20102321";
    private NormalTitleBarFragment fragment_titleBar;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_csdn);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        //实例化控件
        webView = (WebView) findViewById(R.id.webview);
        fragment_titleBar = (NormalTitleBarFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_titleBar);
    }

    private void initData() {
        String webUrl = getIntent().getStringExtra("webUrl");
        if (webUrl != null && !TextUtils.isEmpty(webUrl)) {
            url = webUrl;
        }
        String title = getIntent().getStringExtra("title");
        if (title != null && !TextUtils.isEmpty(title)) {
            fragment_titleBar.getTitleBar().setTitle(title);
        } else {
            fragment_titleBar.getTitleBar().setTitle("wenzhi的博客");
        }
        fragment_titleBar.getTitleBar().setLeftLayoutVisibility(View.VISIBLE);
        fragment_titleBar.getTitleBar().setLeftImageResource(R.drawable.icon_title_back);
        fragment_titleBar.getTitleBar().setRightLayoutVisibility(View.VISIBLE);
        fragment_titleBar.getTitleBar().setRightImageResource(R.drawable.icon_refrash);
        toWeb();

    }

    private void initEvent() {
        fragment_titleBar.getTitleBar().setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null && !isLoading) {
                    webView.reload();
                } else {
                    showToast("数据加载中");
                }

            }
        });
    }

    /**
     * 跳转到网络页面
     */
    public void toWeb() {

        //设置页面显示的URL地址
        webView.loadUrl(url);

        //一般要设置WebViewClient，才能让网页在本页面布局内显示，否则会跳转到浏览器软件打开页面
        webView.setWebViewClient(new WebViewClient() {
            //shouldOverrideUrlLoading方法return true
            //表示我加载后这个Intent就消费了，不再跳转到其他页面。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置页面显示的URL地址
                webView.loadUrl(url);
                return true;
            }

        });

        //设置WebView的进度加载监听
        webView.setWebChromeClient(new WebChromeClient() {
            //进度变化
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress != 100) {
                    pd.setProgress(newProgress);
                    isLoading = true;
                } else {
                    isLoading = false;
                    pd.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        showProcessDialog();   //显示进度框

        //取消滚动条
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //添加网页的文本，支持java的JavaScript显示
        //webView.loadDataWithBaseURL(null, string, null, "utf-8", null);
        //获取WebView的设置对象，能设置是否支持缩放或是否在页面显示图片等等。
        WebSettings settings = webView.getSettings();

        //设置是否支持缩放
        settings.setSupportZoom(true);//有时无效！true
//        settings.setBuiltInZoomControls(true);
        //设置是否阻止显示图片
        settings.setBlockNetworkImage(false);
        //再请求网络数据缓存模式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置字体的大小
        settings.setTextZoom(20);
        //支持javaScript
        settings.setJavaScriptEnabled(true);
        //防止字体重叠 ，不一定有用
        settings.setUseWideViewPort(true);

    }

    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            // 返回键退回
            webView.goBack();
            return true;//直接返回
        }
        finish();//关闭页面
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 回退页面
     */
    public void toback(View view) {
        if (webView.canGoBack()) {
            //  退回
            webView.goBack();
        } else {
//            finish();
            showToast("没有能回退的页面了");
        }
    }

    /**
     * 前一个页面
     */
    public void topre(View view) {
        if (webView.canGoForward()) {
            // 前进
            webView.goForward();
        } else {
            showToast("没有能前进的页面了");
        }
    }


    /**
     * 显示加载数据的进度框
     */
    ProgressDialog pd;    //进度条对话框

    private void showProcessDialog() {
        pd = new ProgressDialog(this);
        //监听对话框的消失
        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                webView.stopLoading();
            }
        });
        pd.setMax(100);
//        pd.setCancelable(false);//设置不能失去焦点和回退键后关闭
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("正在加载网页数据");
        if (!isFinishing()) {
            pd.show();
        }
        webView.reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.e("onDestroy");
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        pd = null;

        if (webView != null) {
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;
    }


}