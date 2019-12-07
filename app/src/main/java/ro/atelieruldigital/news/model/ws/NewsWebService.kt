package ro.atelieruldigital.news.model.ws;

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ro.atelieruldigital.news.model.NewsListResponse

class NewsWebService(private val api: NewsApi) {
    private val API_KEY = "121ae9a3461d468ba2158b1a74ef3b93"

    /*fun queryNewsByKeywords(
            contentKeywords: String,
            page: Int,
            pageSize: Int,
            @NewsSort sort: String,
            failure: (Throwable?) -> Unit = {},
            success: (NewsListResponse?) -> Unit) {
        api.queryNewsByKeywords(contentKeywords, null, sort, page, pageSize, API_KEY).enqueue(object : Callback<NewsListResponse> {
            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                failure(t)
            }

            override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else failure(null)
            }

        })

    }

    fun queryNewsByKeywordsInTitle(
            contentKeywords: String = "",
            titleKeywords: String,
            @NewsSort sort: String,
            page: Int,
            pageSize: Int,
            failure: (Throwable?) -> Unit = {},
            success: (NewsListResponse?) -> Unit
    ) {
        api.queryNewsByKeywords(contentKeywords, titleKeywords, sort, page, pageSize, API_KEY)
                .enqueue(object : Callback<NewsListResponse> {
                    override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                        failure(t)
                    }

                    override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                        if (response.isSuccessful)
                            success(response.body())
                        else failure(null)
                    }
                }

                )
    }*/

    fun queryNews(
            contentKeywords: String? = "",
            titleKeywords: String? = "",
            language: String?,
            @NewsSort sort: String,
            oldest: String?,
            newest: String?,
            website: String?,
            page: Int,
            pageSize: Int,
            failure: (Throwable?) -> Unit = {},
            success: (NewsListResponse?) -> Unit
    ) {
        api.queryNews(contentKeywords, titleKeywords, language, sort, oldest, newest, website, page, pageSize, API_KEY)
                .enqueue(
                        object : Callback<NewsListResponse> {
                            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                                failure(t)
                            }

                            override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                                if (response.isSuccessful)
                                    success(response.body())
                                else failure(null)
                            }
                        }
                )

    }

    fun queryHeadlines(
            contentKeywords: String? = "",
            titleKeywords: String? = "",
            category: String? = "",
            country: String?,
            page: Int,
            pageSize: Int,
            failure: (Throwable?) -> Unit = {},
            success: (NewsListResponse?) -> Unit
    ) {
        api.queryHeadlines(contentKeywords, titleKeywords, category, country, page, pageSize, API_KEY)
                .enqueue(
                        object : Callback<NewsListResponse> {
                            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                                failure(t)
                            }

                            override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                                if(response.isSuccessful)
                                    success(response.body())
                                else failure(null)
                            }
                        }
                )

    }


}

interface NewsApi {


    @GET("v2/everything")
    fun queryNews(
            @Query("q") keywords: String?,
            @Query("qInTitle") titleKeywords: String?,
            @Query("language") lang: String?, //ISO-639-1 language code
            @Query("sortBy") sort: String?,
            @Query("from") oldest: String?,
            @Query("to") newest: String?,
            @Query("domains") website: String?,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("apiKey") apiKey: String
    ): Call<NewsListResponse>

    @GET("v2/headlines")
    fun queryHeadlines(
            @Query("q") keywords: String?,
            @Query("qInTitle") titleKeywords: String?,
            @Query("category") category: String?,
            @Query("country") country: String?,      // ISO 3166-1 country code
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int,
            @Query("apiKey") apiKey: String
    ): Call<NewsListResponse>

}

const val PUBLISH_DATE = "publishedAt"
const val RELEVANCY = "relevancy"
const val POPULARITY = "popularity"

@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY,
        AnnotationTarget.VALUE_PARAMETER
)
@StringDef(PUBLISH_DATE, RELEVANCY, POPULARITY)
annotation class NewsSort
