package fernandocs.news.articles

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import fernandocs.news.Injection
import fernandocs.news.R
import fernandocs.news.util.EspressoIdlingResource
import fernandocs.news.util.schedulers.SchedulerProvider

class ArticlesActivity : AppCompatActivity() {

    companion object {
        val TAG = ArticlesActivity::class.java.simpleName!!
    }

    private lateinit var presenter: ArticlesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_articles)

        var articlesFragment:ArticlesFragment? =
                supportFragmentManager.findFragmentById(R.id.container) as ArticlesFragment?

        if (articlesFragment == null) {
            // Create the fragment
            articlesFragment = ArticlesFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.container, articlesFragment).commit()
        }

        presenter = ArticlesPresenter(Injection.provideNewsRepository(this),
                articlesFragment, SchedulerProvider.instance)
                .apply { sourceId = intent.getStringExtra("source_id") }
    }


    @VisibleForTesting
    fun getCountingIdlingResource() = EspressoIdlingResource.idlingResource


}
