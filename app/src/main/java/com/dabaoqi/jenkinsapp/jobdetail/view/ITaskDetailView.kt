package com.dabaoqi.jenkinsapp.jobdetail.view

import android.content.Context

/**
 *
 * Created by zengwendong on 2017/6/20.
 */
interface ITaskDetailView {

    fun downloadApkSuccess(filePath:String)

    fun downloadApkFailure()

    fun downloadMappingSuccess(savePath:String)

    fun downloadMappingFailure()

    fun getContext():Context
}