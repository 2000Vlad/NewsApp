package ro.atelieruldigital.news.model.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.atelieruldigital.news.model.News

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticles(news: List<News>)

    @Query("select * from news")
    fun articles() : DataSource.Factory<Int, News>
}
