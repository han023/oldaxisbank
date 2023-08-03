package com.example.oldaxisbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import com.example.oldaxisbank.databinding.ActivityOtpwaitingBinding

class otpwaiting : AppCompatActivity() {

    private val splashDelay: Long = 4000
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: ActivityOtpwaitingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpwaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCountdownTimer(7)

        Handler().postDelayed({
            val intent = Intent(this, otp2::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)



    }

    private fun startCountdownTimer(seconds: Int) {
        val milliseconds = (seconds * 1000).toLong()
        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                binding.timer.text = "Redirecting in $secondsRemaining seconds."
            }

            override fun onFinish() {
                binding.timer.text = "Redirecting in 0 seconds."
            }
        }
        countDownTimer.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }


}