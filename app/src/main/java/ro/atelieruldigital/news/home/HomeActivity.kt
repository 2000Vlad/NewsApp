package ro.atelieruldigital.news.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ro.atelieruldigital.news.R
import ro.atelieruldigital.news.core.BaseActivity

class HomeActivity : BaseActivity() {


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                return result.padEnd(3,'.')
            }
            result += words[i]
            sum += words[i].length
        }
    }

    return string

}

const val MAX_TITLE_SIZE = 40
