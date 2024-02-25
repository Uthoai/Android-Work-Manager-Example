package com.workmanagerexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun doWork(): Result {
        try {
            val url = inputData?.getString("url")
            val imageUrl = URL(url)
            val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
            conn.doInput.and(true)
            conn.connect()
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bitmapImage = BitmapFactory.decodeStream(conn?.inputStream,null,options)
            val folder = context?.getExternalFilesDir(null)
            val file = File(folder,"textFile")
            file.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmapImage?.compress(Bitmap.CompressFormat.PNG,100,bos)
            val bitmapData = bos?.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.close()
            return Result.success(Data.Builder().putString("filePath",file?.absolutePath).build())
        }catch (e: Exception){
            e.printStackTrace()
        }
        return Result.failure()
    }
}
