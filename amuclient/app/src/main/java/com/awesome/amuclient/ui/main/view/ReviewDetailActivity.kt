package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Client
import com.awesome.amuclient.data.model.ReviewList

class ReviewDetailActivity : AppCompatActivity() {

    companion object {
        fun startActivity(activity: AppCompatActivity, reviewList : ReviewList) {
            val intent = Intent(activity, ReviewDetailActivity::class.java)
            intent.putExtra("client", reviewList.client)
            activity.startActivity(intent)
        }
    }

    private var client : Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        client = intent.getParcelableExtra("client")
        println(client?.nickname + "~~~~~~~~~~~~~~~~~~~~~")
    }
}