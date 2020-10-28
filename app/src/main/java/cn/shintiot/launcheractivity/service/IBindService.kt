package cn.shintiot.launcheractivity.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.Exception

/**
 * @author GF63
 */
class IBindService : Service() {
    private val myBind = MyBind()
    private lateinit var progressListener: ProgressListener

    override fun onBind(intent: Intent): IBinder? {
        Log.e("tagBind","onBind")
        return myBind
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("tagBind","onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("tagBind","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    fun setProgressListener(progressListener: ProgressListener){
        this.progressListener = progressListener
    }

    /**
     * 任务
     */
    fun downLoad(){
        if(null == progressListener){
            return throw Exception("progressListener = null")
        }
        progressListener.onProgress(0)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.e("tagBind","onUnbind")

        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("tagBind","onDestroy")
    }

    class MyBind : Binder() {
        fun getService() : IBindService{
            return IBindService()
        }
    }
}