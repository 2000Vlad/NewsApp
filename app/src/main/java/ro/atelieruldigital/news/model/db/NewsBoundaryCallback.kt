package ro.atelieruldigital.news.model.db

import androidx.paging.PagedList
import ro.atelieruldigital.news.model.NETWORK_PAGE_SIZE
import ro.atelieruldigital.news.model.News
import ro.atelieruldigital.news.model.NewsListResponse
import ro.atelieruldigital.news.model.ws.NewsWebService

class NewsBoundaryCallback(
        private val webService: NewsWebService,
        private val cache: NewsLocalCache,
        private val request: Request) : PagedList.BoundaryCallback<News>() {

    private var lastPage: Int = 0
    private var isRequesting = false

    override fun onZeroItemsLoaded() {
        requestData(request)
    }

    override fun onItemAtEndLoaded(itemAtEnd: News) {
        requestData(request)
    }

    private fun requestData(request: Request) {
        if (isRequesting) return
        lastPage++
        val onResponse: (NewsListResponse?) -> Unit = {
            if (it != null)
                cache.insert(it.news) {
                    isRequesting = false
                }
        }

        when (request) {
            is NewsRequest -> requestNews(request, onResponse)
            is HeadlineRequest -> requestHeadlines(request, onResponse)
        }
    }

    private fun requestNews(request: NewsRequest, onResponse: (NewsListResponse?) -> Unit) {
        when (request) {
            is BasicNewsRequest -> requestBasicNews(request, onResponse)
            is AdvancedNewsRequest -> requestAdvancedNews(request, onResponse)
        }
    }

    private fun requestBasicNews(request: BasicNewsRequest, onResponse: (NewsListResponse?) -> Unit) {
        val (cKeywords, tKeywords, s, old, new) = request
        val contentKeywords = if (cKeywords.isNullOrEmpty()) null else cKeywords
        val titleKeywords = if (tKeywords.isNullOrEmpty()) null else tKeywords
        webService.queryNews(
                contentKeywords,
                titleKeywords,
                null,
                s,
                old?.toString(), // Here we will do proper time formatting
                new?.toString(),
                null,
                lastPage,
                NETWORK_PAGE_SIZE) {
            isRequesting = false
            onResponse(it)
        }

    }

    private fun requestAdvancedNews(request: AdvancedNewsRequest, onResponse: (NewsListResponse?) -> Unit) {
        val (cKeywords, tKeywords, old, new, website, s, lang) = request
        val contentKeywords = if (cKeywords.isNullOrEmpty()) null else cKeywords
        val titleKeywords = if (tKeywords.isNullOrEmpty()) null else tKeywords
        val language = if (lang.isNullOrEmpty()) null else lang
        webService.queryNews(
                contentKeywords,
                titleKeywords,
                language,
                s,
                old.toString(),
                new.toString(),
                website,
                lastPage,
                NETWORK_PAGE_SIZE) {
            isRequesting = false
            onResponse(it)
        }

    }

    private fun requestHeadlines(request: HeadlineRequest, onResponse: (NewsListResponse?) -> Unit) {
        val (cKeywords, tKeywords, catg, c) = request
        val contentKeywords = if (cKeywords.isNullOrEmpty()) null else cKeywords
        val titleKeywords = if (tKeywords.isNullOrEmpty()) null else tKeywords
        val category = if (catg.isNullOrEmpty()) null else catg
        val country = if (c.isNullOrEmpty()) null else c
        webService.queryHeadlines(contentKeywords, titleKeywords, category, country, lastPage, NETWORK_PAGE_SIZE) {
            isRequesting = false
            onResponse(it)
        }

    }


}