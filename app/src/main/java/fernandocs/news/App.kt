package fernandocs.news

import android.app.Application
import com.google.gson.GsonBuilder
import fernandocs.news.repository.NewsRepository
import fernandocs.news.repository.remote.api.ArticlesApi
import fernandocs.news.repository.remote.api.SourceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        val API_KEY = "8ebe0a704d3c469fbd8cb4d21991f282"
        val baseUrl = "https://newsapi.org/"

        private lateinit var retrofit: Retrofit
        private lateinit var sourceApi: SourceApi
        private lateinit var articlesApi: ArticlesApi
    }

    override fun onCreate() {
        super.onCreate()

        val logging = HttpLoggingInterceptor()
        logging.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor({ chain ->
            val request = chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
            chain.proceed(request)
        })

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(httpClient.build())
                .build()

        sourceApi = retrofit.create(SourceApi::class.java)
        articlesApi = retrofit.create(ArticlesApi::class.java)
        currencyRepository = NewsRepository(sourceApi)
        currencyViewModel = CurrencyViewModel(currencyRepository)
    }
}