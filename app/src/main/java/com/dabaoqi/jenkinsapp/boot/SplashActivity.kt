package com.dabaoqi.jenkinsapp.boot

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.bumptech.glide.Glide
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.joblist.JobListActivity
import com.dong.easy.image.data.ImageDataResult
import com.dong.easy.image.presenter.LoadImagePresenter
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : Activity(),IImageLoadView {

    companion object {

        private const val TAG = "SplashActivity"

        private val IMAGES = intArrayOf(R.mipmap.splash_1, R.mipmap.splash_2, R.mipmap.splash_3)
    }

    private val randomImage: Int
        get() {
            val length = IMAGES.size
            val r = Random()
            val currentIndex = r.nextInt(length)
            Log.i(TAG, "currentIndex=$currentIndex")
            return IMAGES[currentIndex]
        }

    private val mHandler = @SuppressLint("HandlerLeak") object : Handler() {
        override fun handleMessage(msg: Message) {
            val intent = Intent(this@SplashActivity, JobListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        setContentView(R.layout.activity_splash)
        initData()
    }

    private fun initData() {
        LoadImagePresenter(this).loadImageList(1)
    }

    private fun showImage(url:String){
        if (url == "") {
            showDefaultImage()
        }else{
            Glide.with(this).load(url).into(splash_content_v)
            mHandler.sendEmptyMessageDelayed(0, 3000)
        }
    }

    private fun showDefaultImage(){
        splash_content_v.setImageResource(randomImage)
        mHandler.sendEmptyMessageDelayed(0, 3000)
    }

    override fun getSearchKeyword(): String {
        return "美女 竖屏壁纸 1080x1920"
    }

    override fun refreshImageData(imageData: ImageDataResult) {
        val r = Random()
        val currentIndex = r.nextInt(imageData.data.size)
        val imageEntity = imageData.data[currentIndex]
        showImage(imageEntity.middleURL)
    }

    override fun loadFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
