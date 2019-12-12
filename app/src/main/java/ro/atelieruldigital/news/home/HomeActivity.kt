package ro.atelieruldigital.news.home

import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ro.atelieruldigital.news.R
import ro.atelieruldigital.news.core.BaseActivity
import ro.atelieruldigital.news.home.adapters.KeywordAdapter
import ro.atelieruldigital.news.home.widgets.NewsSwitch

class HomeActivity : BaseActivity() {


/*
    val newsRv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.news_rv) }
*/
    val keywordText: EditText by lazy { findViewById<EditText>(R.id.keyword_et) }
    val keywordRv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.keyword_rv) }
    val switch: NewsSwitch by lazy { findViewById<NewsSwitch>(R.id.touch_outside) }
    lateinit var keywordAdapter: KeywordAdapter
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpKeywords()

    }

    fun setUpKeywords() {
        keywordAdapter = KeywordAdapter {
            keywordText.setText(it.fold("") { acc, s ->
                "$acc,$s"
            }.removePrefix(",")
            )
        }

        keywordRv.layoutManager = LinearLayoutManager(this)
        keywordRv.isNestedScrollingEnabled = true
        keywordRv.adapter = keywordAdapter
        keywordText.addTextChangedListener(KeywordWatcher(keywordAdapter))
    }
}


fun ellipsize(string: String, size: Int): String {

    val words = string.split(" ")
    if (words.size == 1)
        if (words[0].length > 40) {
            return String(words[0].toCharArray().apply {
                this[words[0].length - 1] = '.'
                this[words[0].length - 2] = '.'
                this[words[0].length - 3] = '.'
            }
            )
        } else return words[0]
    else {
        var sum = 0
        var result = ""
        for (i in words.indices) {
            if (sum + words[i].length >= size) {
                return result.padEnd(3, '.')
            }
            result += words[i]
            sum += words[i].length
        }
    }

    return string

}

const val MAX_TITLE_SIZE = 40
