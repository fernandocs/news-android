package fernandocs.news.sources

import fernandocs.news.BasePresenter
import fernandocs.news.BaseView
import fernandocs.news.data.Source

interface SourcesContract {
    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showSources(sources: List<Source>)

        fun showNoSources()

        fun showLoadingSourcesError()

    }

    interface Presenter : BasePresenter {
        fun loadSources()
    }
}