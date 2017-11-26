package fernandocs.news.data

import fernandocs.news.data.remote.NewsRemoteDataSource
import io.reactivex.Flowable

class NewsRepository
    private constructor(private val remoteDataSource: NewsRemoteDataSource):
        NewsDataSource {

    companion object {

        fun getInstance(remoteDataSource: NewsRemoteDataSource)
                = NewsRepository(remoteDataSource)

    }

    override fun getSources() = remoteDataSource.getSources()

    override fun getArticles(sourceId: String) = remoteDataSource.getArticles(sourceId)
}