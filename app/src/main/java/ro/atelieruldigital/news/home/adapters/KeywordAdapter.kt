package ro.atelieruldigital.news.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.atelieruldigital.news.R

class KeywordAdapter(private val post:(List<String>) -> Unit) : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>() {

    var keywords: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.keyword_chip, parent, false)
        )
    }

    override fun getItemCount(): Int = keywords.size

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
       holder.bind(keywords[position], keywords, post)
    }

    class KeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text : TextView by lazy { itemView.findViewById<TextView>(R.id.keyword_text) }
        val cancel : ImageButton by lazy { itemView.findViewById<ImageButton>(R.id.keyword_cancel) }
        fun bind(string: String, keywords: List<String>, post: (List<String>) -> Unit) {
            text.text = string
            cancel.setOnClickListener {
                post(keywords)
            }
        }
    }
}