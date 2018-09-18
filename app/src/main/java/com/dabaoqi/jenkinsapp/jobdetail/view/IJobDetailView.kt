package com.dabaoqi.jenkinsapp.jobdetail.view

import com.dabaoqi.jenkinsapp.jobdetail.model.BuildDesc
import com.dabaoqi.jenkinsapp.jobdetail.model.Task

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
interface IJobDetailView {

    fun notifyDataChange(name:String,tasks:List<Task>)

    fun onLoadFailure()

    fun startBuildSuccess(buildDesc:BuildDesc)

    fun startBuildFailure()

    fun refreshBuildDetail(task: Task)

}