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
        val recyclerView = findViewById<RecyclerView>(R.id.news_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(
                        LayoutInflater.from(this@HomeActivity).inflate(R.layout.basic_search_layout, parent, false)
                ){}
            }

            override fun getItemCount(): Int {
                return 100
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            }
        }
    }

}
