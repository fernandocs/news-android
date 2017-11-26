package fernandocs.news.articles

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fernandocs.news.R
import fernandocs.news.data.Article
import kotlinx.android.synthetic.main.article_item.view.*
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticlesFragment: Fragment(), ArticlesContract.View {

    private val articles = mutableListOf<Article>()
    private lateinit var presenter: ArticlesContract.Presenter

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    override fun setPresenter(presenter: ArticlesContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater!!.inflate(R.layout.fragment_articles, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewArticles.adapter = ArticleAdapter(articles, { _, _ -> })
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun showLoadingIndicator() {
        textViewMessage.visibility = View.GONE
        progressBarLoadArticles.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        progressBarLoadArticles.visibility = View.GONE
    }

    override fun showArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        recyclerViewArticles.adapter.notifyDataSetChanged()
    }

    override fun showNoArticles() {
        textViewMessage.visibility = View.VISIBLE
        textViewMessage.setText(R.string.no_articles)
    }

    override fun showLoadingArticlesError() {
        textViewMessage.visibility = View.VISIBLE
        textViewMessage.setText(R.string.error_get_articles)
    }

    class ArticleAdapter(private val list: List<Article>,
                         private val itemClick: (Article, Int) -> Unit)
        : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
        override fun getItemCount() = list.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.article_item, parent, false)
            return ViewHolder(view, itemClick)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bind(list[position], position)
        }

        class ViewHolder(view: View, private val itemClick: (Article, Int) -> Unit) : RecyclerView.ViewHolder(view) {
            fun bind(item: Article, position: Int) {
                with(item) {
                    Picasso.with(itemView.context)
                            .load(urlToImage)
                            .placeholder(R.drawable.place_holder)
                            .into(itemView.imageViewArticleThumbnail)
                    itemView.textViewName.text = title
                    itemView.textViewDescription.text = description
                    itemView.textViewAuthor.text = author

                    // The date cannot have a correct date pattern
                    itemView.textViewDate.text = publishedAt?.substring(0, 10) ?: ""

                    itemView.setOnClickListener { itemClick(this, position) }
                }
            }
        }
    }

}