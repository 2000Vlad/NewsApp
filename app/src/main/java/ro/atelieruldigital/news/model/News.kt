package ro.atelieruldigital.news.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(
        @SerializedName("source") val source: ArticleSource,
        @SerializedName("author") val author: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val sourceUrl: String,
        @SerializedName("urlToImage") val imageUrl: String,
        @SerializedName("publishedAt") val publishingDate: String, //TODO("Convert to date")
        @SerializedName("content") val contentPreview: String
)
//TODO("Add anything of interest")

data class ArticleSource(val id: Int?, val name: String)