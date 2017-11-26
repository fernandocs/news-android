package fernandocs.news.articles

import android.content.Context
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fernandocs.news.Injection
import fernandocs.news.R
import fernandocs.news.data.Article
import fernandocs.news.util.EspressoIdlingResource
import fernandocs.news.util.schedulers.SchedulerProvider
import kotlinx.android.synthetic.main.activity_articles.*
import kotlinx.android.synthetic.main.article_item.view.*

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
