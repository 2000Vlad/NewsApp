package ro.atelieruldigital.news.model.db

import com.jakewharton.threetenabp.AndroidThreeTen
import ro.atelieruldigital.news.model.ws.NewsSort
import java.util.*

//These classes encapsulate a news request incoming from the UI
//Basic means that request comes from a normal search from the toolbar
//Advanced means that request comes from both toolbar and nav drawer

abstract class Request(
        open val contentKeywords: String?,
        open val titleKeywords: String?
)

//searches in v2/everything
abstract class NewsRequest(
        override val contentKeywords: String?,
        override val titleKeywords: String?,

        @NewsSort open val sort: String
) : Request(contentKeywords, titleKeywords)

//searches in v2/top-headlines
data class HeadlineRequest(
        override val contentKeywords: String?,
        override val titleKeywords: String?,
        val category: String?,
        val country: String?


) : Request(
        contentKeywords,
        titleKeywords
)

data class BasicNewsRequest(
        override val contentKeywords: String?,
        override val titleKeywords: String?,
        override val sort: String,
        val oldest: Date?,
        val newest: Date?

) : NewsRequest(contentKeywords, titleKeywords, sort)




data class AdvancedNewsRequest(
        override val contentKeywords: String?,
        override val titleKeywords: String?,
        val oldest: Date?,
        val newest: Date?,
        val domain: String?,
        override val sort: String,
        val language: String?

) : NewsRequest(
        contentKeywords,
        titleKeywords,
        sort
)

