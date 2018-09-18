package com.dabaoqi.jenkinsapp.jobdetail.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Created by zengwendong on 2017/6/22.
 */
data class Task(@SerializedName("num") val num:String,
                @SerializedName("status") val status:String,//task状态：FAILURE, UNSTABLE, REBUILDING, BUILDING,ABORTED,SUCCESS,UNKNOWN,NOT_BUILT,CANCELLED
                @SerializedName("apkUrl") val apkUrl:String,
                @SerializedName("mappingUrl") val mappingUrl:String,
                @SerializedName("changeSet") val changeSet:List<String>):Serializable