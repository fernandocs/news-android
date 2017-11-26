package fernandocs.news.repository.remote.api

import fernandocs.news.model.ArticleResult
import fernandocs.news.model.SourceResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {
    @GET("/v1/sources")
    fun getSources() : Observable<SourceResult>
}

interface ArticlesApi {
    @GET("/v1/articles")
    fun getArticles(@Query("source") source: String) : Observable<ArticleResult>
}