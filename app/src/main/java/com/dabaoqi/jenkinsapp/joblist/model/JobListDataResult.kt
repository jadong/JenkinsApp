package com.dabaoqi.jenkinsapp.joblist.model

import com.google.gson.annotations.SerializedName

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
data class JobListDataResult constructor(@SerializedName("jobs") val jobs: List<JobEntity>,
                                         @SerializedName("url") val url: String)