package fernandocs.news

import android.content.Context
import fernandocs.news.data.NewsRepository
import fernandocs.news.data.remote.NewsRemoteDataSource
import fernandocs.news.data.remote.api.NewsApi

object Injection {

    fun provideNewsRepository(context: Context): NewsRepository {
        checkNotNull(context)
        return NewsRepository
                .getInstance(NewsRemoteDataSource
                        .getInstance(NewsApp.retrofit.create(NewsApi::class.java)))
    }
}