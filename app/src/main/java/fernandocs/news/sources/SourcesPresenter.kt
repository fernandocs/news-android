package fernandocs.news.sources

import fernandocs.news.data.NewsRepository
import fernandocs.news.data.Source
import fernandocs.news.util.EspressoIdlingResource
import fernandocs.news.util.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SourcesPresenter(private val newsRepository: NewsRepository,
                        private val sourcesView: SourcesContract.View,
                        private val schedulerProvider: BaseSchedulerProvider) : SourcesContract.Presenter {

    private var compositeDisposable = CompositeDisposable()

    init {
        sourcesView.setPresenter(this)
    }

    override fun subscribe() {
        loadSources()
    }

    override fun loadSources() {
        EspressoIdlingResource.increment()

        compositeDisposable.clear()

        sourcesView.showLoadingIndicator()

        compositeDisposable.add(newsRepository.getSources()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally {
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                .subscribe({
                    sourcesView.hideLoadingIndicator()
                    processSources(it)
                }, {
                    sourcesView.hideLoadingIndicator()
                    sourcesView.showLoadingSourcesError()
                }))
    }

    private fun processSources(sources: List<Source>) {
        if (sources.isEmpty()) sourcesView.showNoSources() else sourcesView.showSources(sources)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}