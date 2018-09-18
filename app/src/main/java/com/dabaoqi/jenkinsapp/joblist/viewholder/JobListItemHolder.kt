package com.dabaoqi.jenkinsapp.joblist.viewholder

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.JobDetailActivity
import com.dabaoqi.jenkinsapp.joblist.model.JobEntity

class JobListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var jobNameTextView: TextView = itemView.findViewById(R.id.jobNameTextView) as TextView
    private var statusImageView: ImageView = itemView.findViewById(R.id.status_iv) as ImageView

    private var job: JobEntity? = null

    init {
        itemView.setOnClickListener {
            if (job != null) {
                val intent = Intent(itemView.context, JobDetailActivity::class.java)
                intent.putExtra("name", job!!.name)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun initData(jobInfo: JobEntity) {
        this.job = jobInfo
        jobNameTextView.text = jobInfo.name
        when {
            TextUtils.equals(jobInfo.color, "blue") -> statusImageView.setImageResource(R.drawable.ic_success)
            TextUtils.equals(jobInfo.color, "aborted") -> statusImageView.setImageResource(R.drawable.ic_error)
            TextUtils.equals(jobInfo.color, "blue_anime") -> statusImageView.setImageResource(R.drawable.ic_timelapse)
        }
    }
}

