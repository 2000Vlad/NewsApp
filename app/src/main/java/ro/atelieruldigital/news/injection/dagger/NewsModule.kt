package ro.atelieruldigital.news.injection.dagger

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.atelieruldigital.news.BuildConfig
import ro.atelieruldigital.news.R
import ro.atelieruldigital.news.home.KeywordWatcher
import ro.atelieruldigital.news.home.NewsViewModel
import ro.atelieruldigital.news.home.NewsViewModelFactory
import ro.atelieruldigital.news.model.NewsRepository
import ro.atelieruldigital.news.model.db.NewsDao
import ro.atelieruldigital.news.model.db.NewsDatabase
import ro.atelieruldigital.news.model.db.NewsLocalCache
import ro.atelieruldigital.news.model.ws.NewsApi
import ro.atelieruldigital.news.model.ws.NewsWebService
import java.util.concurrent.Executors

@Module
open class NewsModule(val application: Application, val factivity: FragmentActivity) {


    @Provides
    fun provideDao(): NewsDao = NewsDatabase.getInstance(application).articleDao()

    @Provides
    fun provideLocalCache(dao: NewsDao): NewsLocalCache = NewsLocalCache(dao, Executors.newSingleThreadExecutor())

    @Provides
    fun provideApi(): NewsApi = Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    fun provideWebService(api: NewsApi): NewsWebService = NewsWebService(api)

    @Provides
    fun provideRepository(webService: NewsWebService, cache: NewsLocalCache): NewsRepository = NewsRepository(webService, cache)

    @Provides
    fun provideViewModelFactory(repository: NewsRepository): NewsViewModelFactory = NewsViewModelFactory(repository)

    @Provides
    fun provideViewModel(factory: NewsViewModelFactory): NewsViewModel =
            ViewModelProviders
                    .of(factivity, factory)
                    .get(NewsViewModel::class.java)
}