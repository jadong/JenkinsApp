package com.dabaoqi.jenkinsapp.jobdetail.view

import java.io.File

/**
 *
 * Created by zengwendong on 2017/6/21.
 */
interface IDownloadDirView {

    fun refreshDir(fileList:List<File>)

    fun readDirFailure()
}