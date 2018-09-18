package com.dabaoqi.jenkinsapp.joblist.presenter

import com.dabaoqi.jenkinsapp.joblist.model.JobListDataResult
import com.dabaoqi.jenkinsapp.joblist.model.JobViewsDataResult
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
interface IJobList {

    @GET("jenkins/api/json?pretty=true")
    fun getJobViewList(): Observable<JobViewsDataResult>

    @GET("api/json?pretty=true")
    fun getJobList(): Observable<JobListDataResult>
}