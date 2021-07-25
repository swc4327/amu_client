package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.api.response.StoreResponse
import com.awesome.amuclient.data.api.service.GetStoreInfoService
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.ui.main.adapter.PromotionAdapter
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.PromotionViewModel
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_promotion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PromotionActivity : AppCompatActivity() {

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var promotionViewModel : PromotionViewModel

    private var promotionAdapter: PromotionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion)

        initListener()
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)

        observe()

        promotionViewModel.getPromotions(firebaseViewModel.getUid())
    }

    private fun observe() {
        promotionViewModel.promotionLists.observe(this, Observer<ArrayList<PromotionList>> {promotionLists->
            if (promotionAdapter == null) {
                promotionAdapter = PromotionAdapter(arrayListOf(), Glide.with(this)){ store ->
                    val intent = Intent(this, StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }
                promotion_list.adapter = promotionAdapter
            }
            promotionAdapter!!.update(promotionLists)
        })
    }

    private fun initListener() {
        close_promotion_list.setOnClickListener {
            finish()
        }
    }
}