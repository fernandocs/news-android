package fernandocs.news.data.remote.api

import fernandocs.news.data.ArticleResult
import fernandocs.news.data.SourceResult
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("sources")
    fun getSources() : Flowable<SourceResult>

    @GET("top-headlines")
    fun getArticles(@Query("sources") sources: List<String>? = null) : Flowable<ArticleResult>

}