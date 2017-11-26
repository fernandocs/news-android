package fernandocs.news.data

import com.google.gson.annotations.SerializedName

data class SourceResult(@SerializedName("sources") val sources: List<Source>)

data class Source(@SerializedName("id") val id: String,
                  @SerializedName("name") val name: String,
                  @SerializedName("description") val description: String,
                  @SerializedName("url") val url: String)