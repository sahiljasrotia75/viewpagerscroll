package com.app.atsz7.viewpagerautoscroll.module

import com.app.atsz7.viewpagerautoscroll.BuildConfig.DEBUG
import com.app.atsz7.viewpagerautoscroll.Constants
import com.app.atsz7.viewpagerautoscroll.data.api.ApiService
import com.app.atsz7.viewpagerautoscroll.data.repository.MainRepository
import com.app.atsz7.viewpagerautoscroll.extensions.gson
import com.app.atsz7.viewpagerautoscroll.utils.GsonPConverterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single {
        MainRepository(get())
    }
    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }

}




val networkModule = module {
    val connectTimeout: Long = 40// 20s
    val readTimeout: Long = 40 // 20s


    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.addInterceptor(AbstractTransformingDecodingInterceptor())
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }


    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
           // .addConverterFactory(GsonPConverterFactory(gson))
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        val baseUrl = Constants.baseUrl
        provideRetrofit(get(), baseUrl)
    }
    single {
        MainRepository(get())
    }


    single {
        get<Retrofit>().create(ApiService::class.java)
    }// 5
}


