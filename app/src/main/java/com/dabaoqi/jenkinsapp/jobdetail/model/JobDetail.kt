package com.dabaoqi.jenkinsapp.jobdetail.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
data class JobDetail(@SerializedName("name") val name:String,@SerializedName("tasks") val tasks:List<Task>)