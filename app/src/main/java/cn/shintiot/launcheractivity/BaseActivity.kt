package cn.shintiot.launcheractivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(this.javaClass.simpleName,"onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.e(this.javaClass.simpleName,"onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(this.javaClass.simpleName,"onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(this.javaClass.simpleName,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(this.javaClass.simpleName,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(this.javaClass.simpleName,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this.javaClass.simpleName,"onDestroy")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e(this.javaClass.simpleName,"onNewIntent")
    }
}