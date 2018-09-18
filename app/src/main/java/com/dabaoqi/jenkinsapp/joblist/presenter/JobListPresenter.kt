package com.dabaoqi.jenkinsapp.joblist.presenter

import android.annotation.SuppressLint
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.joblist.view.IJobListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2018/9/11.
 */
class JobListPresenter : BasePresenter<IJobListView> {


    constructor(view: IJobListView) : super(view)

    @SuppressLint("CheckResult")
    fun getJobList(url: String) {
        buildRetrofit(url).create(IJobList::class.java).getJobList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(JobListCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    view.onLoadSuccess(it)
                }, {
                    view.onLoadFailure()
                })

    }
}