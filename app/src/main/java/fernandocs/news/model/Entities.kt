package fernandocs.news.model

import com.google.gson.annotations.SerializedName

data class SourceResult(@SerializedName("sources") val sources: List<Source>)

data class ArticleResult(@SerializedName("articles") val articles: List<Article>)

data class Source(@SerializedName("id") val id: String,
                  @SerializedName("name") val name: String,
                  @SerializedName("description") val description: String,
                  @SerializedName("url") val url: String)

data class Article(@SerializedName("author") val author: String,
                   @SerializedName("title") val title: String,
                   @SerializedName("description") val description: String,
                   @SerializedName("url") val url: String,
                   @SerializedName("urlToImage") val urlToImage: String,
                   @SerializedName("publishedAt") val publishedAt: String)