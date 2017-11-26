package fernandocs.news.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import fernandocs.news.model.Source
import fernandocs.news.repository.NewsRepository

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {
    var sources = MutableLiveData<List<Source>>().apply { value = arrayListOf() }

    fun getSources() {
        sources.value = newsRepository.getSources()
    }
}