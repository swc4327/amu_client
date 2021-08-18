package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.ui.main.adapter.ReserveListAdapter
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reserve_list.*

class ReserveListActivity : AppCompatActivity() {

    private var reserveListAdapter: ReserveListAdapter? = null

    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var reserveViewModel: ReserveViewModel

    private var clientId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)

        initListener()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientId = firebaseViewModel.getUid()
        var factory = ReserveViewModelFactory(clientId.toString())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)


        observe()


        reserveViewModel.getReserveList()
    }

    private fun observe() {
        reserveViewModel.reserveLists.observe(this, Observer<ArrayList<ReserveList>> {reserveLists->
            if(reserveListAdapter == null) {
                reserveListAdapter = ReserveListAdapter(arrayListOf(), Glide.with(this),
                    { store ->
                    val intent = Intent(this, StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }, {reserveList ->
                    val intent = Intent(this, ReserveDetailActivity::class.java)
                    intent.putExtra("reserve", reserveList.reserve)
                    intent.putExtra("store", reserveList.store)
                    startActivity(intent)
                }, {reserveList ->
                        when {
                            reserveList.reserve.is_completed == "0" -> {
                                Toast.makeText(this, "리뷰 작성 기한이 아닙니다!!", Toast.LENGTH_LONG).show()
                            }
                            reserveList.reserve.is_reviewed =="1" -> {
                                Toast.makeText(this, "이미 리뷰를 작성하셨습니다!!", Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                val intent = Intent(this, ReviewActivity::class.java)
                                intent.putExtra("reserveList", reserveList)
                                startActivity(intent)
                            }
                        }
                })
                reserve_list.adapter = reserveListAdapter
            }
            reserveListAdapter!!.update(reserveLists)
        })

    }

    private fun initListener() {
        close_reserve_list.setOnClickListener {
            finish()
        }
    }
}