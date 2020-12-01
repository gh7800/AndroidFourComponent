package cn.shintiot.launcheractivity.media

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Handler
import cn.shineiot.base.manager.FileManger
import cn.shineiot.base.utils.LogUtil
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import kotlin.math.log10

/**
 * 录音管理
 */
object MediaRecorderManager {
    private var mMediaRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var recorderListener: MediaRecorderListener? = null
    private var path : String ? = null

    private var handler: Handler = Handler { false }

    interface MediaRecorderListener {
        fun playerFinish()              //播放结束
        fun updateProgress(db: Double)  //更新声音分贝
    }

    fun setRecorderListener(recorderListener: MediaRecorderListener) {
        this.recorderListener = recorderListener
    }

    fun startRecord(amrPath: String) {
        this.path = amrPath

        if (mMediaRecorder == null) {
            mMediaRecorder = MediaRecorder()

            val file = File(amrPath)
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    LogUtil.e(e.message)
                }
            }
            try {
                mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
                mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                mMediaRecorder?.setOutputFile(file.absolutePath)
                mMediaRecorder?.setMaxDuration(1000 * 60 * 2)
                mMediaRecorder?.prepare()

                mMediaRecorder?.start()

                updateMicStatus()

            } catch (e: Exception) {
                LogUtil.e("startRecord___" + e.message)
            }
        }
        /*else {

            recorderListener?.playerFinish()

            val file = File(amrPath)
            if (file.exists()) {
                val bl = file.delete()
                LogUtil.e(bl)
            }
        }*/

    }

    fun stopRecord() {
        handler.removeCallbacks(mUpdateMicStatusTimer)

        if (null != mMediaRecorder) {
            mMediaRecorder?.setPreviewDisplay(null)
            mMediaRecorder?.setOnErrorListener(null)

            try {
                mMediaRecorder?.stop()
                mMediaRecorder?.release()

            } catch (e: IllegalStateException) {
                LogUtil.e("IllegalStateException $mMediaRecorder __" + e.message)
                FileManger.deleteFile(File(path))
            }catch (e: RuntimeException){
                LogUtil.e("RuntimeException $mMediaRecorder __" + e.message)
                FileManger.deleteFile(File(path))
            }catch (e : Exception){
                LogUtil.e("Exception $mMediaRecorder __" + e.message)
                FileManger.deleteFile(File(path))
            }

            mMediaRecorder = null

        }
    }

    fun playVoice(amrPath: String?) {

        mPlayer = MediaPlayer()
        mPlayer?.setOnCompletionListener { recorderListener?.playerFinish() }
        try {
            mPlayer?.setDataSource(amrPath)
            mPlayer?.prepare()
            mPlayer?.start()
        } catch (e: IOException) {
            LogUtil.e("playVoice: ${e.message}")
            recorderListener?.playerFinish()
        }
    }

    fun stopPlayer() {
        if (null != mPlayer) {
            mPlayer?.stop()
            mPlayer?.release()
            mPlayer = null
        }
    }

    /**
     * 更新话筒状态
     */
    private const val BASE = 1
    private const val SPACE: Long = 100 // 间隔取样时间

    private fun updateMicStatus() {
        if (mMediaRecorder != null) {
            val ratio: Double = mMediaRecorder!!.maxAmplitude.toDouble() / BASE
            var db = 0.0 // 分贝
            if (ratio > 1) {
                db = 20 * log10(ratio)
                if (null != recorderListener) {
                    recorderListener?.updateProgress(db)
                }
            }
            handler.postDelayed(mUpdateMicStatusTimer, SPACE)
        }
    }

    private val mUpdateMicStatusTimer = Runnable { updateMicStatus() }

    /**
     * 得到amr的时长
     *
     * @param file
     * @return amr文件时间长度
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getAmrDuration(file: File): Int {
        var duration: Long = -1
        val packedSize = intArrayOf(
            12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0,
            0, 0
        )
        var randomAccessFile: RandomAccessFile? = null
        try {
            randomAccessFile = RandomAccessFile(file, "rw")
            // 文件的长度
            val length = file.length()
            // 设置初始位置
            var pos = 6
            // 初始帧数
            var frameCount = 0
            var packedPos = -1
            // 初始数据值
            val datas = ByteArray(1)
            while (pos <= length) {
                randomAccessFile.seek(pos.toLong())
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = if (length > 0) (length - 6) / 650 else 0
                    break
                }
                val s = datas[0].toInt() shr 3
                packedPos = s and 0x0F
                pos += packedSize[packedPos] + 1
                frameCount++
            }
            // 帧数*20
            duration += (frameCount * 20).toLong()
        } finally {
            randomAccessFile?.close()
        }
        return (duration / 1000 + 1).toInt()
    }
}