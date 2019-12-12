package ro.atelieruldigital.news.home

import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import ro.atelieruldigital.news.home.adapters.KeywordAdapter

class KeywordWatcher(private val adapter: KeywordAdapter) : TextWatcher {

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(p0 == null) return
        val keywords = p0.split(SEPARATORS).filter { !it.isNullOrEmpty() }
        adapter.keywords = keywords

    }
}
val SEPARATORS = Regex("\\W")