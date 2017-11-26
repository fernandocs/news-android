package fernandocs.news.articles

import fernandocs.news.BasePresenter
import fernandocs.news.BaseView
import fernandocs.news.data.Article

interface ArticlesContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showArticles(articles: List<Article>)

        fun showNoArticles()

        fun showLoadingArticlesError()

    }

    interface Presenter : BasePresenter {
        fun loadArticles(sourceId: String)
    }
}