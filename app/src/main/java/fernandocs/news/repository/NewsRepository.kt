package fernandocs.news.repository

import fernandocs.news.repository.remote.api.ArticlesApi
import fernandocs.news.repository.remote.api.SourceApi

class NewsRepository(private val sourceApi: SourceApi,
                     private val articlesApi: ArticlesApi) {

    fun getSources() = sourceApi.getSources()

    fun getArticle(source: String) = articlesApi.getArticles(source)

}