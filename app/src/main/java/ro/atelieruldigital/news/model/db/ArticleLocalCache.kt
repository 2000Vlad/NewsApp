package ro.atelieruldigital.news.model.db
import androidx.paging.DataSource
import ro.atelieruldigital.news.model.Article
import java.util.concurrent.Executor
/**
 * This class servers as an abstraction over the room database data source
 */
class ArticleLocalCache(private val dao: ArticleDao, private val ioExecutor: Executor) {

    fun insert(articles: List<Article>, finished: () -> Unit) {
        ioExecutor.execute {
            dao.addArticles(articles)
            finished()
        }
    }

    fun articles() : DataSource.Factory<Int, Article> {
        return dao.articles()
    }
    //TODO("Sort")
}