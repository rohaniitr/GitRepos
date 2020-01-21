package com.appstreet.myapplication.util.download

import android.content.Context
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig

object DownloadHelper {
    private const val READ_TIMEOUT = 30000
    private const val CONNECTION_TIMEOUT = 30000

    fun init(appContext: Context) {
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .setConnectTimeout(CONNECTION_TIMEOUT)
            .setReadTimeout(READ_TIMEOUT)
            .build()

        PRDownloader.initialize(appContext, config)
    }

    fun downloadFile(
        url: String,
        dirPath: String,
        fileName: String,
        downloadInterface: DownloadInterface?
    ) {
        PRDownloader.download(url, dirPath, fileName)
            .build()
            .setOnStartOrResumeListener {
                downloadInterface?.downloadStarted()
            }
            .setOnCancelListener {
                downloadInterface?.downloadCancelled()
            }
            .setOnProgressListener { progress ->
                progress?.let {
                    downloadInterface?.setProgress(((it.currentBytes * 100) / it.totalBytes).toFloat())
                }
            }
            .start(object : OnDownloadListener {

                override fun onDownloadComplete() {
                    downloadInterface?.downloadCompleted()
                }

                override fun onError(error: com.downloader.Error?) {
                    if (error == null) {
                        downloadInterface?.downloadError(error!!.responseCode)
                    } else {
                        downloadInterface?.downloadError(0)
                    }
                }
            })
    }
}

interface DownloadInterface {
    fun downloadStarted()
    fun setProgress(progress: Float)
    fun downloadCompleted()
    fun downloadCancelled()
    fun downloadError(responseCode: Int)
}