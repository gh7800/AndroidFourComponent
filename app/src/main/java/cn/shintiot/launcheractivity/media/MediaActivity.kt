package cn.shintiot.launcheractivity.media

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.shintiot.launcheractivity.R
import cn.shintiot.launcheractivity.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_media.*
import java.io.IOException

class MediaActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        /*bt1.setOnLongClickListener {
            MediaRecorderManager.newInstance().startRecorder()
            DialogUtil.showAviLoadingView(this,"...."
            ) {
                DialogUtil.hideAviLoading()
                MediaRecorderManager.newInstance().stopRecorder()
            }
            true
        }*/

        bt1.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    MediaRecorderManager.newInstance().startRecorder()
                    DialogUtil.showAviLoadingView(v!!.context, "....")
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                    DialogUtil.hideAviLoading()
                    MediaRecorderManager.newInstance().stopRecorder()
                }
            }

            true
        }

        bt2.setOnClickListener {

        }

        bt3.setOnClickListener {

        }

        bt4.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        MediaRecorderManager.newInstance().resumeRecorder()
    }

    override fun onPause() {
        super.onPause()
        MediaRecorderManager.newInstance().pauseRecorder()
    }

    /**
     * 通过文件绝对路径播放
     *
     * @param path
     */
    fun playRecorder(path: String?) {
        try {
            val player = MediaPlayer()
            player.setDataSource(path)
            player.prepare()
            player.start()
            player.setOnInfoListener(object : MediaPlayer.OnInfoListener {
                override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                    return false
                }
            })
            /*播放完成回调*/player.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                override fun onCompletion(mp: MediaPlayer?) {}
            })
            player.setOnErrorListener(object : MediaPlayer.OnErrorListener {
                override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                    return false
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
