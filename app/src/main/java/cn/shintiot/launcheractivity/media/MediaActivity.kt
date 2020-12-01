package cn.shintiot.launcheractivity.media

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cn.shineiot.base.manager.FileManger
import cn.shineiot.base.utils.LogUtil
import cn.shintiot.launcheractivity.R
import cn.shintiot.launcheractivity.utils.DialogUtil
import cn.shintiot.launcheractivity.widget.AudioRecorderDialog
import kotlinx.android.synthetic.main.activity_media.*
import java.io.File

class MediaActivity : AppCompatActivity() , MediaRecorderManager.MediaRecorderListener{

    private var path : String ?= null
    private var audioRecorderDialog : AudioRecorderDialog ?= null
    private var downT : Long = 0
    private var bt3Animation : AnimationDrawable?=null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        MediaRecorderManager.setRecorderListener(this)
        audioRecorderDialog = AudioRecorderDialog(this)

        bt1.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {

                    val root = externalCacheDir?.path
                    path = root + "/" + System.currentTimeMillis() + ".amr"

                    LogUtil.e(path)

                    downT = System.currentTimeMillis()
                    MediaRecorderManager.startRecord(path!!)

                    audioRecorderDialog?.showAtLocation(v, Gravity.CENTER, 0, 0)
                    //DialogUtil.showAviLoadingView(v!!.context, "语音中")
                }

                MotionEvent.ACTION_UP -> {
                    //DialogUtil.hideAviLoading()
                    audioRecorderDialog?.dismiss()

                    MediaRecorderManager.stopRecord()
                }
            }

            true
        }

        bt2.setOnClickListener {

        }

        bt3.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.voicebutton_animlist_idle))
        bt3.setOnClickListener {

            bt3.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.voicebutton_animlist))

            bt3Animation = bt3.drawable as AnimationDrawable
            bt3Animation?.start()

            /*DialogUtil.showAviLoadingView(this, ""){
                MediaRecorderManager.stopPlayer()
                //DialogUtil.hideAviLoading()
            }*/

            if(null != path) {
                LogUtil.e(path)
                LogUtil.e(MediaRecorderManager.getAmrDuration(File(path)))

                MediaRecorderManager.playVoice(path!!)
            }
        }

        bt4.setOnClickListener {

        }
    }



    override fun onResume() {
        super.onResume()
        //MediaRecorderManager.newInstance().resumeRecorder()
    }

    override fun onPause() {
        super.onPause()
       // MediaRecorderManager.newInstance().pauseRecorder()
    }

    override fun onDestroy() {
        super.onDestroy()

        FileManger.deleteFile(File(externalCacheDir?.path + "/recorder/"))
    }

    override fun updateProgress(db: Double) {
        audioRecorderDialog?.setLevel(db.toInt())
        audioRecorderDialog?.setTime(System.currentTimeMillis() - downT)
    }

    override fun playerFinish() {
        //DialogUtil.hideAviLoading()
        if(null != bt3Animation){
            bt3Animation?.stop()
            bt3.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.voicebutton_animlist_idle))
        }
    }


}
