package cn.shineiot.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import cn.shineiot.base.R
import java.util.*

/**
 * activity 管理栈
 */
object ActManager {
    private var activityStack: Stack<Activity> = Stack<Activity>()

    //添加activity
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    //结束单个Activity
    fun finishActivity(activity: Activity?) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity)
            activity?.finish()
        }
    }

    //清除所有activity
    fun finishAllActivity() {
        val size = activityStack.size - 1
        for (i in 0..size) {
            if (null != activityStack[i]) {
                activityStack[i].finish()
            }
        }
        activityStack.clear()
    }

    /**
     *  startActivity
     *  @param requestCode 请求码
     */
    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap(),requestCode : Int = -1) {
        val currentActivity = activityStack[activityStack.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            if (it.value is List<*>) {
                intent.putParcelableArrayListExtra(it.key, it.value as ArrayList<out Parcelable>?)
            } else if(it.value is Parcelable){
                intent.putExtras(it.key to it.value)
            }else{
                intent.putExtras(it.key to it.value)
            }
        }
        if(requestCode != -1){
            currentActivity.startActivityForResult(intent,requestCode)
        }else {
            currentActivity.startActivity(intent)
        }
        currentActivity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out)
    }

    /**
     * setResult
     */
    fun setActivityResult(params: Map<String, Any> = emptyMap()){
        val currentActivity = activityStack[activityStack.lastIndex]
        val intent = Intent()

        params.forEach {
            when (it.value) {
                is List<*> -> {
                    intent.putParcelableArrayListExtra(it.key, it.value as ArrayList<out Parcelable>?)
                }
                is Parcelable -> {
                    intent.putExtras(it.key to it.value)
                }
                else -> {
                    intent.putExtras(it.key to it.value)
                }
            }
        }

        currentActivity.setResult(Activity.RESULT_OK,intent)

        finish(currentActivity)
    }

    /**
     * 结束
     */
    fun finish(context: Activity){
        context.finish()
        context.overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out)
    }

}