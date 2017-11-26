package fernandocs.news.data.remote

import fernandocs.news.data.NewsDataSource
import fernandocs.news.data.remote.api.NewsApi
import io.reactivex.Flowable

class NewsRemoteDataSource
    private constructor(private val newsApi: NewsApi) : NewsDataSource {

    override fun getArticles(sourceId: String) =
            newsApi.getArticles(listOf(sourceId))
                    .flatMap { Flowable.fromIterable(it.articles) }
                    .toList()
                    .toFlowable()!!


    override fun getSources() =
            newsApi.getSources()
                    .flatMap { Flowable.fromIterable(it.sources) }
                    .toList()
                    .toFlowable()!!

    companion object {

        fun getInstance(articleApi: NewsApi) = NewsRemoteDataSource(articleApi)

    }
}