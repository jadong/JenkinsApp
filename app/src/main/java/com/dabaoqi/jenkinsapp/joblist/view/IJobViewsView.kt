package com.dabaoqi.jenkinsapp.joblist.view

import com.dabaoqi.jenkinsapp.joblist.model.JobViewsDataResult

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
interface IJobViewsView{

    fun onLoadSuccess(dataResult: JobViewsDataResult)

    fun onLoadFailure()

}