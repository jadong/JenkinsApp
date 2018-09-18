package com.dabaoqi.jenkinsapp.jobdetail.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by zengwendong on 2017/6/22.
 */
data class BuildDesc(@SerializedName("repeat_time") var repeat_time:Long,
                     @SerializedName("task") var task: Task)