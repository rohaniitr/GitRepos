package com.appstreet.myapplication.util.imageCache

import com.appstreet.myapplication.AppApplication
import com.appstreet.myapplication.util.Utils
import com.appstreet.myapplication.util.download.DownloadHelper
import com.appstreet.myapplication.util.download.DownloadInterface
import java.io.File

object ImageCacheHelper {
    val IMAGE_DIR = "images"

    fun downloadImage(imageUrl: String, downloadInterface: DownloadInterface?) {
        DownloadHelper.downloadFile(
            imageUrl,
            getDownloadDirPath(),
            getDownloadFileName(
                imageUrl
            ),
            downloadInterface
        )
    }

    fun isDownloaded(imageUrl: String): Boolean =
        File(
            getDownloadDirPath(),
            getDownloadFileName(
                imageUrl
            )
        ).exists()

    fun getDownloadFilePath(imageUrl: String): String =
        getDownloadDirPath() + getDownloadFileName(
            imageUrl
        )

    fun getDownloadDirPath(): String =
        AppApplication.getInstance().filesDir.absolutePath + File.separator + IMAGE_DIR + File.separator

    fun getDownloadFileName(imageUrl: String): String = Utils.convertStringToHash(imageUrl) + ".png"
}