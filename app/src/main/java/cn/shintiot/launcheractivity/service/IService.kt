package cn.shintiot.launcheractivity.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @author GF63
 */
class IService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("tag","onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("tag","onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("tag","onDestroy")
    }
}