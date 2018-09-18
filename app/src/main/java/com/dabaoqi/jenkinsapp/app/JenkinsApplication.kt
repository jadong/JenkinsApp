package com.dabaoqi.jenkinsapp.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
class JenkinsApplication : Application() {

    companion object{
        var appContext:Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        //全局异常捕获
        CrashHandler.instance.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}