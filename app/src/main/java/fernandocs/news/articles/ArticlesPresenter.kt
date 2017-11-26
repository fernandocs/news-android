package fernandocs.news.articles

import fernandocs.news.data.Article
import fernandocs.news.data.NewsRepository
import fernandocs.news.util.EspressoIdlingResource
import fernandocs.news.util.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ArticlesPresenter(private val articlesRepository: NewsRepository,
                        private val articlesView: ArticlesContract.View,
                        private val schedulerProvider: BaseSchedulerProvider) : ArticlesContract.Presenter {

    private var compositeDisposable = CompositeDisposable()
    lateinit var sourceId:String

    init {
        articlesView.setPresenter(this)
    }

    override fun subscribe() {
        loadArticles(sourceId)
    }

    override fun loadArticles(sourceId: String) {

        EspressoIdlingResource.increment()

        compositeDisposable.clear()

        articlesView.showLoadingIndicator()

        compositeDisposable.add(articlesRepository.getArticles(sourceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally {
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                .subscribe({
                    articlesView.hideLoadingIndicator()
                    processArticles(it)
                }, {
                    articlesView.hideLoadingIndicator()
                    articlesView.showLoadingArticlesError()
                }))
    }

    private fun processArticles(articles: List<Article>) {
        if (articles.isEmpty()) articlesView.showNoArticles() else articlesView.showArticles(articles)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}