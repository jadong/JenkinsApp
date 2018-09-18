package com.dabaoqi.jenkinsapp.joblist.model

import com.google.gson.annotations.SerializedName

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
data class JobViewsDataResult constructor(@SerializedName("views") val jobViews: List<JobView>,
                                          @SerializedName("jobs") val jobs: List<JobEntity>,
                                          @SerializedName("primaryView") val primaryView: PrimaryView)