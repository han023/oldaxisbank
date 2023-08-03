package com.example.oldaxisbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.example.axisbank.FifthPageOtp
import com.example.oldaxisbank.databinding.ActivityOtp2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class otp2 : AppCompatActivity() {

    private lateinit var binding: ActivityOtp2Binding;
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        startCountdownTimer(45)

        binding.submit.setOnClickListener {
            if (binding.otp.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields", Toast.LENGTH_SHORT).show()
            }else{
                val util = Util()
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val intentff = Intent(this, finalscreen::class.java)
                val data = FifthPageOtp(customerid = util.getLocalData(this,"c"),
                    mobile = util.getLocalData(this,"m"),
                    otp2 = binding.otp.text.toString(),
                )
                val call = apiService.fith(data)
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

    private fun startCountdownTimer(seconds: Int) {
        val milliseconds = (seconds * 1000).toLong()
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                binding.timer.text = "Resend OTP $secondsRemaining sec"
            }

            override fun onFinish() {
                binding.timer.text = "Resend OTP 0 sec"
            }
        }
        countDownTimer.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }


}