package com.dabaoqi.jenkinsapp.webview

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.*
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*


/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/12.
 */
class WebViewActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_webview
    }

    override fun initData() {
        val title = intent.getStringExtra("name")
        if (!TextUtils.isEmpty(title)) {
            setTitle(title)
        }

//        val url = intent.getStringExtra("url")
        val url = "http://app.int.jumei.com:8080/jenkins/"

        initWebSetting()

        webView.loadUrl(url)
    }

    private fun initWebSetting() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放

        webSettings.displayZoomControls = false //隐藏原生的缩放控件

        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String?) {
                val title = view.title
                if (!TextUtils.isEmpty(title)) {
                    setTitle(title)
                }

            }

            override fun onLoadResource(view: WebView?, url: String?) {
                //每一个资源（比如图片）的加载都会调用一次
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                // 加载页面的服务器出现错误时（如404）调用
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

}