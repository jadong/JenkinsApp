package com.dabaoqi.jenkinsapp.joblist.model

import com.google.gson.annotations.SerializedName

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
data class JobEntity constructor(@SerializedName("color") val color: String,
                                 @SerializedName("name") val name: String,
                                 @SerializedName("url") val url: String)