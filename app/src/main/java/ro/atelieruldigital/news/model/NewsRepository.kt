package ro.atelieruldigital.news.model

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ro.atelieruldigital.news.model.db.NewsBoundaryCallback
import ro.atelieruldigital.news.model.db.NewsLocalCache
import ro.atelieruldigital.news.model.db.Request
import ro.atelieruldigital.news.model.ws.NewsWebService

class NewsRepository(val webService: NewsWebService, val cache: NewsLocalCache) {


        fun searchNews(request: Request) : LiveData<PagedList<News>> {

            val dataSource = cache.news()

            val callback = NewsBoundaryCallback(webService, cache, request)

            return LivePagedListBuilder(dataSource, NETWORK_PAGE_SIZE)
                    .setBoundaryCallback(callback)
                    .build()

        }
}
const val NETWORK_PAGE_SIZE = 50
const val DATABASE_PAGE_SIZE = 20