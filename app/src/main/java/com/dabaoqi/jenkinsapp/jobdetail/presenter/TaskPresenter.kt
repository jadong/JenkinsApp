package com.dabaoqi.jenkinsapp.jobdetail.presenter

import android.app.ProgressDialog
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.dabaoqi.jenkinsapp.base.BasePresenter
import com.dabaoqi.jenkinsapp.constant.AppConstant
import com.dabaoqi.jenkinsapp.download.*
import com.dabaoqi.jenkinsapp.jobdetail.view.ITaskDetailView
import com.dabaoqi.jenkinsapp.util.AppUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * Created by zengwendong on 2017/6/20.
 */
class TaskPresenter : BasePresenter<ITaskDetailView> {

    constructor(view: ITaskDetailView) : super(view)

    fun downloadApk(downloadUrl: String) {
        downloadFile(downloadUrl, "apk")
    }

    fun downloadMapping(downloadUrl: String) {
        downloadFile(downloadUrl, "mapping")
    }

    private fun downloadFile(downloadUrl: String, flag: String) {
        if (TextUtils.isEmpty(downloadUrl)) {
            AppUtils.showShortToast("下载路径为空")
            return
        }

        val fileName = getFileName(downloadUrl)//文件名

        var savePath: String
        if (TextUtils.equals(flag, "mapping")) {
            savePath = AppConstant.DOWNLOAD_MAPPING_PATH
        } else {
            savePath = AppConstant.DOWNLOAD_APK_PATH
        }

        //监听下载进度
        val dialog = ProgressDialog(view.getContext())
        dialog.setProgressNumberFormat("%1d KB/%2d KB")
        dialog.setTitle("下载")
        dialog.setMessage("正在下载，请稍后...")
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setCancelable(false)
        dialog.show()

        val retrofitBuilder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.SERVER_BASE_URL)

        val builder = createProgressBuilder(dialog)
        val retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadApi::class.java)

        val call = retrofit.downloadFile(downloadUrl)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    //保存到本地目录
                    val inputStream = response.body().byteStream()
                    val dir = File(savePath)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    val file = File(savePath, fileName)
                    val fos = FileOutputStream(file)
                    val bis = BufferedInputStream(inputStream)
                    val buffer = ByteArray(1024)
                    var len: Int = bis.read(buffer)
                    while (len != -1) {
                        fos.write(buffer, 0, len)
                        fos.flush()
                        len = bis.read(buffer)
                    }
                    fos.close()
                    bis.close()
                    inputStream.close()
                    //文件保存成功后
                    dialog.dismiss()
                    if (TextUtils.equals(flag, "mapping")) {
                        view.downloadMappingSuccess(file.path)
                    } else {
                        view.downloadApkSuccess(file.path)
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                    dialog.dismiss()

                    if (TextUtils.equals(flag, "mapping")) {
                        view.downloadMappingFailure()
                    } else {
                        view.downloadApkFailure()
                    }
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                dialog.dismiss()
                if (TextUtils.equals(flag, "mapping")) {
                    view.downloadMappingFailure()
                } else {
                    view.downloadApkFailure()
                }
            }
        })

    }

    fun createProgressBuilder(dialog: ProgressDialog): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        val handler = object : Handler(Looper.getMainLooper()) {

            override fun handleMessage(msg: Message?) {
                if (msg != null) {
                    val progressBean: ProgressBean = msg.obj as ProgressBean

                    val toInt = (progressBean.total / 1024).toInt()
                    dialog.max = toInt
                    dialog.progress = (progressBean.progress / 1024).toInt()

                    if (progressBean.done) {
                        //下载完成
                        dialog.setMessage("正在保存文件，请稍后...")
                    }
                }
            }

        }

        val progressListener = object : ProgressListener {

            //该方法在子线程中运行
            override fun onProgress(progress: Long, total: Long, done: Boolean) {
                Log.d("progress:", String.format("%d%% done\n", 100 * progress / total))
                val progressBean = ProgressBean()
                progressBean.progress = progress
                progressBean.total = total
                progressBean.done = done
                handler.obtainMessage(0, progressBean).sendToTarget()
            }

        }

        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add(Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            originalResponse.newBuilder().body(
                    ProgressResponseBody(originalResponse.body(), progressListener))
                    .build()
        })

        return builder
    }

    private fun getFileName(url: String): String {
        val fileName = url.substring(url.lastIndexOf("/"), url.length)
        return fileName
    }
}