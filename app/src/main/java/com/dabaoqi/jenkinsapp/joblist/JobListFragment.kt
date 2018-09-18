package com.dabaoqi.jenkinsapp.joblist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.adapter.JobListAdapter
import com.dabaoqi.jenkinsapp.joblist.model.JobEntity
import com.dabaoqi.jenkinsapp.joblist.model.JobListDataResult
import com.dabaoqi.jenkinsapp.joblist.model.JobView
import com.dabaoqi.jenkinsapp.joblist.presenter.JobListPresenter
import com.dabaoqi.jenkinsapp.joblist.view.IJobListView
import kotlinx.android.synthetic.main.fragment_main.*

class JobListFragment : Fragment(), IJobListView {

    lateinit var jobView: JobView
    private lateinit var jobInfoModels: List<JobEntity>
    var jobListAdapter = JobListAdapter()
    private lateinit var jobListPresenter: JobListPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    fun initData(jobView: JobView, jobs: List<JobEntity>) {
        this.jobView = jobView
        this.jobInfoModels = jobs
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        contentView.layoutManager = LinearLayoutManager(context)
        contentView.adapter = jobListAdapter
        if (jobInfoModels.isNotEmpty()) {
            jobListAdapter.setData(jobInfoModels)
        } else {
            jobListPresenter = JobListPresenter(this)
            jobListPresenter.getJobList(jobView.url)
        }
    }

    override fun onLoadSuccess(dataResult: JobListDataResult) {
        jobInfoModels = dataResult.jobs
        jobListAdapter.setData(jobInfoModels)
    }

    override fun onLoadFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}