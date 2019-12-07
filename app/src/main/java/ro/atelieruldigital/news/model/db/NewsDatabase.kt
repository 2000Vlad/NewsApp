package ro.atelieruldigital.news.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ro.atelieruldigital.news.model.News

/**
 * Web service will insert into database and the database will be observed by UI
 */
@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun articleDao() : NewsDao

    companion object {
        private val INSTANCE : NewsDatabase? = null
        private val lock : Any = Any()
       fun getInstance(context: Context) : NewsDatabase  {
            if(INSTANCE != null)
                return INSTANCE
            else {
                synchronized(lock) {
                    return Room.databaseBuilder(context, NewsDatabase::class.java, "newsdb")
                            .build()
                }
            }
        }
    }
}