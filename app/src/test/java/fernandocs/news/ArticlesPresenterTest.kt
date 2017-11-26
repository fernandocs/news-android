package fernandocs.news

import fernandocs.news.articles.ArticlesContract
import fernandocs.news.articles.ArticlesPresenter
import fernandocs.news.data.Article
import fernandocs.news.data.NewsRepository
import fernandocs.news.util.schedulers.BaseSchedulerProvider
import fernandocs.news.util.schedulers.ImmediateSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ArticlesPresenterTest {
    private var MOCK_ITEMS: List<Article>? = null

    @Mock
    private lateinit var mNewsRepository: NewsRepository

    @Mock
    private lateinit var mArticlesView: ArticlesContract.View

    private lateinit var mSchedulerProvider: BaseSchedulerProvider

    private lateinit var mArticlesPresenter: ArticlesPresenter

    @Before
    fun setupTasksPresenter() {
        MockitoAnnotations.initMocks(this)

        mSchedulerProvider = ImmediateSchedulerProvider()

        mArticlesPresenter = ArticlesPresenter(mNewsRepository, mArticlesView, mSchedulerProvider)

        MOCK_ITEMS = arrayListOf(
                Article("Author1", "Title1","Description1", "Url1", "https://i2.ztat.net/thumb_hd/BE/82/4G/00/2C/11/BE824G002-C11@8.jpg", "11-11-2017"),
                Article("Author2", "Title2","Description2", "Url2", "https://i2.ztat.net/thumb_hd/BE/82/4G/00/2C/11/BE824G002-C11@8.jpg", "11-11-2017"),
                Article("Author3", "Title3","Description3", "Url3", "https://i2.ztat.net/thumb_hd/BE/82/4G/00/2C/11/BE824G002-C11@8.jpg", "11-11-2017"))
    }

    @Test
    fun createPresenterSetsThePresenterToView() {
        mArticlesPresenter = ArticlesPresenter(mNewsRepository, mArticlesView, mSchedulerProvider)

        verify<ArticlesContract.View>(mArticlesView).setPresenter(mArticlesPresenter)
    }

    @Test
    fun loadAllArticlesFromRepositoryAndLoadIntoView() {
        `when`(mNewsRepository.getArticles(anyString())).thenReturn(Flowable.just<List<Article>>(MOCK_ITEMS))

        mArticlesPresenter.loadArticles("bbc")

        verify<ArticlesContract.View>(mArticlesView).showLoadingIndicator()
        verify<ArticlesContract.View>(mArticlesView).hideLoadingIndicator()
    }

    @Test
    fun loadFilteredArticlesFromRepositoryAndLoadIntoView() {
        `when`(mNewsRepository.getArticles(anyString())).thenReturn(Flowable.just<List<Article>>(MOCK_ITEMS))

        mArticlesPresenter.loadArticles("bbc")

        verify<ArticlesContract.View>(mArticlesView).showLoadingIndicator()
        verify<ArticlesContract.View>(mArticlesView).hideLoadingIndicator()
    }

    @Test
    fun errorLoadingArticlesShowsError() {
        `when`(mNewsRepository.getArticles(anyString())).thenReturn(Flowable.error(Exception()))

        mArticlesPresenter.loadArticles("bbc")

        verify<ArticlesContract.View>(mArticlesView).showLoadingArticlesError()
    }

    @Test
    fun loadingArticlesShowsEmptyMessage() {
        `when`(mNewsRepository.getArticles(anyString())).thenReturn(Flowable.just(emptyList()))

        mArticlesPresenter.loadArticles("bbc")

        verify<ArticlesContract.View>(mArticlesView).showNoArticles()
    }
}
