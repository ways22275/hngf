package com.example.sideproject.data.remote

import com.example.sideproject.BuildConfig
import com.example.sideproject.utils.Constant.DOMAIN
import com.example.sideproject.utils.Constant.HEADER_TOKEN
import com.example.sideproject.utils.SharePreferenceManager.getToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceClient {

    @Volatile
    private var instance: Service? = null

    fun getService(): Service =
        instance ?: synchronized(this) {
            instance ?: providerService().also { instance = it }
        }

    private fun providerService() : Service {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE))
            .addInterceptor{
                    chain ->
                        val newRequest = chain.request().newBuilder().apply {
                            val token = getToken()
                            if (token.isNotEmpty()) {
                                addHeader(HEADER_TOKEN, token)
                            }
                        }.build()
                        chain.proceed(newRequest)
            }
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(Service::class.java)
    }
}