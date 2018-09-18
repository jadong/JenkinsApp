package com.dong.easy.image.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.dabaoqi.jenkinsapp.BuildConfig
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.boot.IImageLoadView
import com.dabaoqi.jenkinsapp.boot.api.LoadImageApi
import com.dabaoqi.jenkinsapp.constant.AppConstant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class LoadImagePresenter : BasePresenter<IImageLoadView> {

    private val pageSize = 30

    constructor(view: IImageLoadView) : super(view)

    @SuppressLint("CheckResult")
    fun loadImageList(page: Int) {
        val params = mutableMapOf<String, String>()
        params["word"] = view.getSearchKeyword()
        params["tn"] = "resultjson"
        params["pn"] = "" + ((page - 1) * pageSize)
        params["rn"] = "" + pageSize
        buildRetrofit(AppConstant.BAIDU_IMAGE_BASE_URL).create(LoadImageApi::class.java).loadImageList(params)
                .subscribeOn(Schedulers.io())
                .timeout(12, TimeUnit.SECONDS)
                .unsubscribeOn(Schedulers.io())
                .map(ImageLoadCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imageDataResult ->
                    view?.refreshImageData(imageDataResult)
                }, { e ->
                    Log.e("LoadImagePresenter", e.message, e.cause)
                    view.loadFail()
                })

    }



}