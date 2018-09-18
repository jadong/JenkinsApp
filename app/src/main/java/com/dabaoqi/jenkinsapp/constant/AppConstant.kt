package com.dabaoqi.jenkinsapp.constant

import android.os.Environment

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
object AppConstant {

    const val BAIDU_IMAGE_BASE_URL = "http://image.baidu.com/"
    const val SERVER_BASE_URL = "http://app.int.jumei.com:8080/"
    val SDCARD_DIR: String = Environment.getExternalStorageDirectory().path
    val CRASH_LOG_PATH = "$SDCARD_DIR/JenkinsApp/CrashLog/"
    val DOWNLOAD_APK_PATH = "$SDCARD_DIR/JenkinsApp/DownloadAPK/" //apk下载目录
    val DOWNLOAD_MAPPING_PATH = "$SDCARD_DIR/JenkinsApp/DownloadMapping/" //mapping文件下载目录

}