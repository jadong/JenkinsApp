package com.dabaoqi.jenkinsapp.jobdetail.presenter

import android.text.TextUtils
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.constant.AppConstant
import com.dabaoqi.jenkinsapp.jobdetail.view.IDownloadDirView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 *
 * Created by zengwendong on 2017/6/21.
 */
class DownloadDirPresenter :BasePresenter<IDownloadDirView>{

    constructor(view: IDownloadDirView) : super(view)

    fun readDirFile(dirFlag:String) {
        var path:String
        if (TextUtils.equals(dirFlag,"apk")) {
            path = AppConstant.DOWNLOAD_APK_PATH
        }else{
            path = AppConstant.DOWNLOAD_MAPPING_PATH
        }
        Observable.create(ObservableOnSubscribe<List<File>> {
            e ->
            val dirFile = File(path)
            val files = dirFile.listFiles()
            val fileList = ArrayList<File>()
            files.forEach { file -> fileList.add(file) }
            e.onNext(fileList)

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list ->
                    view.refreshDir(list)
                },{
                    view.readDirFailure()
                })


    }
}