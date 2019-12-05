package ro.atelieruldigital.news.home

import android.os.Bundle

import ro.atelieruldigital.news.R
import ro.atelieruldigital.news.core.BaseActivity

class HomeActivity : BaseActivity() {


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

}
