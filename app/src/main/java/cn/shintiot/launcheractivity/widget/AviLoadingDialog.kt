package cn.shintiot.launcheractivity.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.text.TextUtils
import android.view.Gravity
import cn.shineiot.base.utils.LogUtil
import cn.shintiot.launcheractivity.R
import kotlinx.android.synthetic.main.dialog_avi_loading.*

class AviLoadingDialog(context: Context,theme : Int = R.style.AviLoadingDialog) : Dialog(context,theme) {

    private var msg : String? = ""

    constructor(context: Context,msg : String):this(context){
        this.msg = msg
    }

    override fun show() {
        super.show()

        val view = this.window?.layoutInflater?.inflate(R.layout.dialog_avi_loading,null)
        setContentView(view!!)
        setCanceledOnTouchOutside(false)

        if(!TextUtils.isEmpty(msg)){
            aviContent.text = msg
        }

        this.window?.setGravity(Gravity.CENTER)
        val lp = this.window?.attributes
        val display = this.window?.windowManager?.defaultDisplay
        val point = Point()
        display?.getSize(point)
        lp?.width = point.x.times(0.3f).toInt()
        lp?.height = point.x.times(0.3f).toInt()
        lp?.alpha = 1f

        //LogUtil.e(lp?.width)
        this.window?.attributes = lp

    }

    override fun dismiss() {
        super.dismiss()
        aviLoadingView.hide()
        LogUtil.e("dismiss")
    }
}