package ro.atelieruldigital.news.model

import kotlin.collections.List

data class NewsListResponse(
    val status: String ,
    val totalResults: Int ,
    val articles: List<Article>
)


