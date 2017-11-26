package fernandocs.news.data

import io.reactivex.Flowable

interface NewsDataSource {
    fun getSources(): Flowable<List<Source>>

    fun getArticles(sourceId: String): Flowable<List<Article>>
}