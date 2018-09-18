package com.dabaoqi.jenkinsapp.jobdetail.viewholder

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.dabaoqi.jenkinsapp.R

/**
 * Created by zengwendong on 17/6/19.
 */
class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //提示文案
    private val tv_tip: TextView = itemView.findViewById(R.id.tv_tip) as TextView
    private val refresh_progress: ProgressBar = itemView.findViewById(R.id.refresh_progress) as ProgressBar

    fun setLoadingText() {
        tv_tip.text = "加载中..."
        tv_tip.visibility = View.VISIBLE
        refresh_progress.visibility = View.VISIBLE
    }

    fun setLoadEnd() {
        tv_tip.text = "已显示全部"
        tv_tip.visibility = View.VISIBLE
        refresh_progress.visibility = View.GONE
    }

    fun setFooterViewText(@StringRes resId: Int) {
        tv_tip.text = itemView.context.getString(resId)
        tv_tip.visibility = View.VISIBLE
        refresh_progress.visibility = View.GONE
    }

    fun setFooterViewText(tips: String) {
        tv_tip.text = tips
        tv_tip.visibility = View.VISIBLE
        refresh_progress.visibility = View.GONE
    }

}
