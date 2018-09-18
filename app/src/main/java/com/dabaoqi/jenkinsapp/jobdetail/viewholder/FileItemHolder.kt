package com.dabaoqi.jenkinsapp.jobdetail.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.util.AppUtils
import java.io.File

/**
 * Created by zengwendong on 2017/6/19.
 */
class FileItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tv_file_name: TextView = itemView.findViewById(R.id.tv_file_name) as TextView
    private var tv_desc: TextView = itemView.findViewById(R.id.tv_desc) as TextView
    private var file: File? = null

    init {
        itemView.setOnClickListener {
            if (file?.name?.contains(".apk") as Boolean) {
                AppUtils.installApk(itemView.context, file!!.path)
            } else {
                AppUtils.openFile(itemView.context, file!!)
            }

        }
    }

    fun initData(file: File?, url: String?) {
        if (file == null) {
            return
        }
        this.file = file
        tv_file_name.text = file.name

        if (file.name.contains(".apk")) {
            tv_desc.text = "点击安装"
        } else {
            tv_desc.text = "点击查看"
        }

        if (url?.contains(file.name) as Boolean) {
            tv_file_name.setTextColor(R.color.orange)
            tv_desc.setTextColor(R.color.orange)
        } else {
            tv_file_name.setTextColor(R.color.color_666)
            tv_desc.setTextColor(R.color.color_666)
        }

    }
}
