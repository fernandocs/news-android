package fernandocs.news.sources

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import fernandocs.news.Injection
import fernandocs.news.R
import fernandocs.news.articles.ArticlesActivity
import fernandocs.news.data.Source
import fernandocs.news.util.EspressoIdlingResource
import fernandocs.news.util.schedulers.SchedulerProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.source_item.view.*

class SourcesActivity : AppCompatActivity(), SourcesContract.View {

    private var presenter: SourcesContract.Presenter? = null
    private val sources = mutableListOf<Source>()

    companion object {
        val TAG = SourcesActivity::class.java.simpleName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerViewSources.adapter = SourceAdapter(sources, { source, _ ->
            startActivity(Intent(this, ArticlesActivity::class.java).apply {
                putExtra("source_id", source.id)
            })
        })

        setPresenter(SourcesPresenter(Injection.provideNewsRepository(this),
                this, SchedulerProvider.instance))
    }

    override fun onResume() {
        super.onResume()
        presenter!!.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter!!.unsubscribe()
    }

    override fun setPresenter(presenter: SourcesContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoadingIndicator() {
        textViewMessage.visibility = View.GONE
        progressBarLoadSources.visibility = VISIBLE
    }

    override fun hideLoadingIndicator() {
        progressBarLoadSources.visibility = GONE
    }

    override fun showSources(sources: List<Source>) {
        this.sources.clear()
        this.sources.addAll(sources)
        recyclerViewSources.adapter.notifyDataSetChanged()
    }

    override fun showNoSources() {
        textViewMessage.visibility = View.VISIBLE
        textViewMessage.setText(R.string.no_sources)
    }

    override fun showLoadingSourcesError() {
        textViewMessage.visibility = View.VISIBLE
        textViewMessage.setText(R.string.error_get_sources)
    }

    @VisibleForTesting
    fun getCountingIdlingResource() = EspressoIdlingResource.idlingResource

    class SourceAdapter(private val list: List<Source>,
                         private val itemClick: (Source, Int) -> Unit)
        : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {
        override fun getItemCount() = list.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.source_item, parent, false)
            return ViewHolder(view, itemClick)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bind(list[position], position)
        }

        class ViewHolder(view: View, private val itemClick: (Source, Int) -> Unit) : RecyclerView.ViewHolder(view) {
            fun bind(item: Source, position: Int) {
                with(item) {
                    itemView.textViewName.text = name
                    itemView.textViewDescription.text = description
                    itemView.textViewUrl.text = url
                    itemView.setOnClickListener { itemClick(this, position) }
                }
            }
        }
    }
}
