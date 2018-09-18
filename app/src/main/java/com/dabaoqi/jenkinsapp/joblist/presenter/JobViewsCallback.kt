package com.dabaoqi.jenkinsapp.joblist.presenter

import com.dabaoqi.jenkinsapp.joblist.model.JobViewsDataResult

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
class JobViewsCallback : io.reactivex.functions.Function<JobViewsDataResult,JobViewsDataResult>{

    override fun apply(dataResult: JobViewsDataResult): JobViewsDataResult {
        return dataResult
    }
}