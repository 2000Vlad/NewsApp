package ro.atelieruldigital.news.model.ws;

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ro.atelieruldigital.news.model.NewsListResponse

class NewsWebService(val api: NewsApi) {
    private val API_KEY = "121ae9a3461d468ba2158b1a74ef3b93"

    fun queryArticles(search: String, failure: (Throwable) -> Unit, success: (NewsListResponse?) -> Unit) {
        api.queryArticles(search, API_KEY).enqueue(object: Callback<NewsListResponse> {
            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                failure(t)
            }

            override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                if(response.isSuccessful) {
                    success(response.body())
                }
            }

        })
    }

    interface NewsApi {
        @GET("v2/everything")
        fun queryArticles(@Query("q") keywords: String, @Query("apiKey") key: String): Call<NewsListResponse>
    }
}
