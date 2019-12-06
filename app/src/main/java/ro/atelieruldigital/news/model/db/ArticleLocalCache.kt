package ro.atelieruldigital.news.model.db
import androidx.paging.DataSource
import ro.atelieruldigital.news.model.News
import java.util.concurrent.Executor
/**
 * This class servers as an abstraction over the room database data source
 */
class ArticleLocalCache(private val dao: ArticleDao, private val ioExecutor: Executor) {

    fun insert(news: List<News>, finished: () -> Unit) {
        ioExecutor.execute {
            dao.addArticles(news)
            finished()
        }
    }

    fun news() : DataSource.Factory<Int, News> {
        return dao.articles()
    }
    //TODO("Sort")
}