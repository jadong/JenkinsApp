package com.dabaoqi.jenkinsapp.jobdetail.presenter

import android.text.TextUtils
import com.dabaoqi.jenkinsapp.api.ApiCallback
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.jobdetail.view.IJobDetailView
import com.dabaoqi.jenkinsapp.util.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
class JobDetailPresenter : BasePresenter<IJobDetailView> {

    constructor(view: IJobDetailView) : super(view)


    fun getJobDetail(name: String) {
        buildRetrofit().create(IJobDetail::class.java).getJobDetail(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(ApiCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (name, tasks) ->
                    view.notifyDataChange(name, tasks)
                }, {
                    e->
                    e.printStackTrace()
                    view.onLoadFailure()
                })

    }

    fun startBuild(name: String) {
        buildRetrofit().create(IJobDetail::class.java).startBuild(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(ApiCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    buildDesc ->
                    if (TextUtils.equals(buildDesc.task.status, "BUILDING")) {//启动成功
                        view.startBuildSuccess(buildDesc)
                    } else {
                        view.startBuildFailure()
                    }

                }, {
                    e->
                    e.printStackTrace()
                    view.startBuildFailure()
                })
    }

    fun getBuildDetail(name: String, num: String) {
        buildRetrofit().create(IJobDetail::class.java).getBuildDetail(name, num)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(ApiCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    task ->
                    view.refreshBuildDetail(task)
                }, {
                    e->
                    e.printStackTrace()
                    AppUtils.showShortToast("error")
                })
    }

}