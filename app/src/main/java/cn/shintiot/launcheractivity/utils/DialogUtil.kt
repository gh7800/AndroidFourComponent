package cn.shintiot.launcheractivity.utils

import android.content.Context
import android.content.DialogInterface
import cn.shintiot.launcheractivity.widget.AviLoadingDialog

object DialogUtil {
    private var dialog : AviLoadingDialog? = null
    fun showAviLoadingView(context: Context,msg : String = "",onDismissListener: DialogInterface.OnDismissListener ?= null){
        dialog = AviLoadingDialog(context,msg)
        dialog?.setOnDismissListener(onDismissListener)
        dialog?.show()
    }

    fun hideAviLoading(){
        dialog?.hide()
    }
}