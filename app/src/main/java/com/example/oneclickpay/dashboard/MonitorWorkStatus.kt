package com.example.oneclickpay.dashboard

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.data.worker.DataSyncWorker


// For monitor Worker Status
@Composable
fun MonitorWorkStatus(context: Context) {
    val workInfos by WorkManager.getInstance(context)
        .getWorkInfosForUniqueWorkLiveData(DataSyncWorker.WORK_NAME)
        .observeAsState()

    val workInfo = workInfos?.firstOrNull()

    when (workInfo?.state) {
        WorkInfo.State.ENQUEUED -> Log.d("WorkManager", "  waiting")
        WorkInfo.State.RUNNING -> Log.d("WorkManager", "implement now")
        WorkInfo.State.SUCCEEDED -> Log.d(
            "WorkManager",
            "Syncing was successfully performed in the background"
        )

        WorkInfo.State.FAILED -> Log.d("WorkManager", "failed")
        WorkInfo.State.BLOCKED -> Log.d("WorkManager", "The task is disabled")
        else -> {}
    }
}