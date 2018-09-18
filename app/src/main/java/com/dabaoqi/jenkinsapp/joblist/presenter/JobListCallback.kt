package com.dabaoqi.jenkinsapp.joblist.presenter

import com.dabaoqi.jenkinsapp.joblist.model.JobListDataResult

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
class JobListCallback : io.reactivex.functions.Function<JobListDataResult,JobListDataResult>{

    override fun apply(dataResult: JobListDataResult): JobListDataResult {
        return dataResult
    }
}