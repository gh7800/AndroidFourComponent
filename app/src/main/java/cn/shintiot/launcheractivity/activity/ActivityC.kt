package cn.shintiot.launcheractivity.activity

import android.content.Intent
import android.os.Bundle
import cn.shintiot.launcheractivity.BaseActivity
import cn.shintiot.launcheractivity.MainActivity
import cn.shintiot.launcheractivity.R

class ActivityC : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        start_a.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        start_b.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
        start_c.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
        start_d.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            startActivity(intent)
        }
    }
}