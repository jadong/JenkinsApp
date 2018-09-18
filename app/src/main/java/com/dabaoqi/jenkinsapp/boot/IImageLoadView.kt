package com.dabaoqi.jenkinsapp.boot

import com.dong.easy.image.data.ImageDataResult

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/11/30.
 */
interface IImageLoadView {

    fun getSearchKeyword(): String

    fun refreshImageData(imageData: ImageDataResult)

    fun loadFail()

}