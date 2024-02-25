package com.workmanagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var data = Data.Builder()
            .putString("url","https://www.freevector.com/uploads/vector/preview/9702/FreeVector-Android-Logo.jpg").build()
        var workManager = WorkManager.getInstance(this)
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .addTag("download")
            .setInputData(data)
            .build()
        workManager.enqueueUniqueWork(
            "download"+System.currentTimeMillis().toString(),
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest)
    }

}