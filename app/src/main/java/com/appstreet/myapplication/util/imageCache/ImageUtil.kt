package com.appstreet.myapplication.util.imageCache

import android.content.Context
import android.widget.ImageView
import com.appstreet.myapplication.R
import com.bumptech.glide.Glide

object ImageUtil {
    fun loadImage(context: Context, imageView: ImageView, url: String) {
        if (ImageCacheHelper.isDownloaded(url)) {
            Glide.with(context)
                .load(ImageCacheHelper.getDownloadFilePath(url))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .circleCrop()
                .into(imageView)
        } else {
            Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .circleCrop()
                .into(imageView)

            ImageCacheHelper.downloadImage(url, null)
        }
    }
}