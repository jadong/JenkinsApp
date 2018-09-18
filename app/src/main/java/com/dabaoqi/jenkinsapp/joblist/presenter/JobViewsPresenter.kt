package com.dabaoqi.jenkinsapp.joblist.presenter

import android.annotation.SuppressLint
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.joblist.view.IJobViewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class JobViewsPresenter : BasePresenter<IJobViewsView> {

    constructor(view: IJobViewsView) : super(view)


    @SuppressLint("CheckResult")
    fun getJobList() {
        buildRetrofit().create(IJobList::class.java).getJobViewList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(JobViewsCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it ->
                    view.onLoadSuccess(it)
                }, {
                    view.onLoadFailure()
                })

    }

}