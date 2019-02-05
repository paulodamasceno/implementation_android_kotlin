package mvp.implement.kotlin.service

/**
 * Created by paulo_frw on 04/04/18.
 */


import android.app.Application
import mvp.implement.kotlin.AppApplication
import mvp.implement.kotlin.common.Constants
import mvp.implement.kotlin.common.Prefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInitializer {


    lateinit var user: String
    lateinit var token: String

    fun retrofit() :Retrofit  {
        return RetrofitInitializer.retrofit(Constants.Network.BASE_URL)
    }

    fun retrofit(baseUrl:String) :Retrofit  {

        val prefs = Prefs(AppApplication.applicationContext())

        token = prefs.apiAuth
        user = prefs.userId

        val httpClient = OkHttpClient.Builder()
        val interceptor = Interceptor {chain ->
            val request = chain.request()?.newBuilder()
                    ?.addHeader("Content-Type", "application/json")
                    ?.addHeader("Authorization", token)
                    ?.addHeader("user", user)
                    ?.build()
            chain.proceed(request)
        }


        httpClient.connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

        httpClient.addInterceptor(interceptor)
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        return retrofit
    }

}