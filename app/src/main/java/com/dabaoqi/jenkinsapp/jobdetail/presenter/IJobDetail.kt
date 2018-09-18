package com.dabaoqi.jenkinsapp.jobdetail.presenter

import com.dabaoqi.jenkinsapp.api.DataResult
import com.dabaoqi.jenkinsapp.jobdetail.model.BuildDesc
import com.dabaoqi.jenkinsapp.jobdetail.model.JobDetail
import com.dabaoqi.jenkinsapp.jobdetail.model.Task
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
interface IJobDetail {

    @GET("job/detail/{name}/")
    fun getJobDetail(@Path("name") name: String): Observable<DataResult<JobDetail>>

    @GET("job/start/{name}/")
    fun startBuild(@Path("name") name: String): Observable<DataResult<BuildDesc>>

    @GET("build/detail/{name}/{num}/")
    fun getBuildDetail(@Path("name") name: String, @Path("num") num: String): Observable<DataResult<Task>>
}