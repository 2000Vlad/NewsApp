package ro.atelieruldigital.news.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(

        @Expose(serialize = false)
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @SerializedName("source")
        @Embedded
        val source: NewsSource,

        @SerializedName("author")
        @ColumnInfo(name = "author")
        val author: String,

        @SerializedName("title")
        @ColumnInfo(name = "title")
        val title: String,

        @SerializedName("description")
        @ColumnInfo(name = "description")
        val description: String,

        @SerializedName("url")
        @ColumnInfo(name = "url")
        val sourceUrl: String,

        @SerializedName("urlToImage")
        @ColumnInfo(name = "urlToImage")
        val imageUrl: String,

        @SerializedName("publishedAt")
        @ColumnInfo(name = "publishedAt")
        val publishingDate: String, //TODO("Convert to date")

        @SerializedName("content")
        @ColumnInfo(name = "content")
        val contentPreview: String
)
//TODO("Add anything of interest")

data class NewsSource(@SerializedName("id") val sourceId: Int?, @SerializedName("name") val name: String)