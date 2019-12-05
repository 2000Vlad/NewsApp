package ro.atelieruldigital.news.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class NewsListResponse(
        @SerializedName("status") val status: String,
        @SerializedName("totalResults") val totalResults: Int,
        @SerializedName("articles") val articles: List<Article>
)


