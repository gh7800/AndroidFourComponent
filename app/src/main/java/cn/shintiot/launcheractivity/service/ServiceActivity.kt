package cn.shintiot.launcheractivity.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import cn.shintiot.launcheractivity.BaseActivity
import cn.shintiot.launcheractivity.R
import kotlinx.android.synthetic.main.activity_service.*

/**
 * @author GF63
 * Service的两种启动方式
 */
class ServiceActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        startIService.setOnClickListener {
            intent = Intent(this,IService::class.java)
            startService(intent)
        }

        stopIService.setOnClickListener {
            stopService(intent)
        }

        startIBIndService.setOnClickListener {
            val bindIntent = Intent(this,IBindService::class.java)
            bindService(bindIntent,serviceConnection, Context.BIND_AUTO_CREATE) //flags = Context.BIND_AUTO_CREATE
        }

        stopIBindService.setOnClickListener {
            unbindService(serviceConnection)
        }


    }

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("tag","onServiceConnected")

            service as IBindService.MyBind

            val bindService = service.getService()

            bindService.setProgressListener(object : ProgressListener{
                override fun onProgress(progress: Int) {
                    Log.e("tag","$progress")
                }
            })

            bindService.downLoad()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("tag","onServiceDisconnected")
        }
    }
}