package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.ui.main.adapter.StoreAdapter
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.StoreViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_visited_store.*

class VisitedStoreActivity : AppCompatActivity() {

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var storeViewModel : StoreViewModel
    private var storeAdapter : StoreAdapter? = null
    
    companion object {
        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, VisitedStoreActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visited_store)

        initListener()
        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        observe()

        storeViewModel.getVisitedStore(firebaseViewModel.getUid())
    }

    private fun observe() {
        storeViewModel.stores.observe(this, Observer<ArrayList<Store>> { stores ->
            if (storeAdapter == null) {
                storeAdapter = StoreAdapter(arrayListOf() , Glide.with(this)) { store ->
                    val intent = Intent(this, StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }
                visited_store_list.adapter = storeAdapter
            }
            storeAdapter?.update(stores)
        })
    }

    private fun initListener() {
        close_visited_store.setOnClickListener {
            finish()
        }
    }
}