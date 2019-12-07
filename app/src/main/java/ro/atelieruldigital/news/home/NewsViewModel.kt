package ro.atelieruldigital.news.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.atelieruldigital.news.model.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

}

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NewsRepository::class.java)
                .newInstance(repository)
    }

}