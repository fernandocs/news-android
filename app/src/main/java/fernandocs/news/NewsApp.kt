package fernandocs.news

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApp: Application() {
    companion object {
        private val baseUrl = "https://newsapi.org/v2/"
        private val apiKey = "PUT_YOUR_KEY_HERE"

        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

        httpClient.addInterceptor { chain ->
            val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("X-Api-Key", apiKey)
                    .build()
            chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(httpClient.build())
                .build()
    }
}