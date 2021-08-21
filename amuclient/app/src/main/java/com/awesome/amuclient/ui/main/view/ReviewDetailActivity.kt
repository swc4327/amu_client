package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Client
import com.awesome.amuclient.data.model.ReviewList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_review_detail.*

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

        initLayout()
        initListener()
    }

    private fun initLayout() {
        review_detail_client_name.text = client?.nickname
        review_detail_client_count.text = "리뷰 수 " + client?.count.toString()
        review_detail_client_point.text = "평균 평점 " + client?.point.toString()
        Glide
            .with(this)
            .load(client?.image)
            .into(profile_img)
    }
    
    private fun initListener() {
        close_review_detail.setOnClickListener {
            finish()
        }
    }
}