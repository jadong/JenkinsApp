package com.dabaoqi.jenkinsapp.joblist.view

import com.dabaoqi.jenkinsapp.joblist.model.JobListDataResult

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
interface IJobListView {

    fun onLoadSuccess(dataResult: JobListDataResult)

    fun onLoadFailure()
}