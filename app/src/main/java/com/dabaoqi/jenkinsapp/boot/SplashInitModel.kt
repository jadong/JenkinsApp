package com.dabaoqi.jenkinsapp.boot

import com.google.gson.annotations.SerializedName

/**
 * <pre>
 *     Project JenkinsApp
 *     Author  Kayo
 *     Data 17/6/23
 *     Desc
 * </pre>
 */
data class SplashInitModel(@SerializedName("splashUrl")var url:String)