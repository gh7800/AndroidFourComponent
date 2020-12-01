package cn.shintiot.launcheractivity.media;

import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import cn.shineiot.base.utils.ToastUtil;

public class MediaRecorderManagerS {

    private MediaRecorder mediaRecorder;
    private static MediaRecorderManagerS mediaRecorderManager;
    /*录音状态*/
    private static int State = 0;


    public static MediaRecorderManagerS newInstance() {
        if (null == mediaRecorderManager) {
            synchronized (MediaRecorderManagerS.class) {
                if (null == mediaRecorderManager) {
                    mediaRecorderManager = new MediaRecorderManagerS();
                }
            }
        }
        return mediaRecorderManager;
    }


    private MediaRecorderManagerS() {
        mediaRecorder = new MediaRecorder();
    }


    /**
     * 开始录制
     *
     * @throws IOException
     */


    public void startRecorder(String filePath) throws IOException {
        Log.e("tag", "startRecorder__" + filePath);
        /*判断是否有SdCard*/
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            if(null != mediaRecorder) {
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                //String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                //File file = new File(rootPath, System.currentTimeMillis() + ".mp4");
                File file = new File(filePath);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    mediaRecorder.setOutputFile(file);
                } else {
                    mediaRecorder.setOutputFile(file.getAbsolutePath());
                }
                mediaRecorder.prepare();
                mediaRecorder.start();
                State = 1;
            }else{
                ToastUtil.INSTANCE.showToast("mediaRecorder = null",0);
            }

        }
    }


    /**
     * 恢复录制
     */
    public void resumeRecorder() {
        if (mediaRecorder != null && State == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.resume();
                State = 1;
            }
        }
    }


    /**
     * 暂停录制
     */
    public void pauseRecorder() {
        if (mediaRecorder != null && State == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.pause();
                State = 2;
            } else {
                //mediaRecorder.pause();
                stopRecorder();
            }
        }
    }


    /**
     * 停止录制
     */
    public void stopRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            State = 0;

            //release();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            State = 0;
        }
    }
}