package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.data.model.Constants.FIRST_CALL
import com.awesome.amuclient.ui.main.adapter.ReserveListAdapter
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reserve_list.*
import kotlinx.android.synthetic.main.fragment_menu.*

class ReserveListActivity : AppCompatActivity() {

    private var reserveListAdapter: ReserveListAdapter? = null
    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var reserveViewModel: ReserveViewModel
    private var clientId = ""

    companion object {
        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, ReserveListActivity::class.java)
            activity.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)

        initListener()
        initRecyclerView()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientId = firebaseViewModel.getUid()
        var factory = ReserveViewModelFactory(clientId)
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)

        observe()
    }

    override fun onResume() {
        super.onResume()
        reserveListAdapter?.clearReserveLists()
        reserveViewModel.getReserveList(FIRST_CALL)
    }

    private fun observe() {
        reserveViewModel.reserveLists.observe(this, Observer<ArrayList<ReserveList>> {reserveLists->
            if(reserveListAdapter == null) {
                reserveListAdapter = ReserveListAdapter(arrayListOf(), Glide.with(this),
                    { store ->
                    StoreInfoActivity.startActivity(this, store)
                }, {reserveList ->
                    ReserveDetailActivity.startActivity(this, reserveList.reserve, reserveList.store)
                }, {reserveList ->
                        when {
                            reserveList.reserve.is_completed == "0" -> {
                                Toast.makeText(this, "리뷰 작성 기한이 아닙니다!!", Toast.LENGTH_LONG).show()
                            }
                            reserveList.reserve.is_reviewed =="1" -> {
                                Toast.makeText(this, "이미 리뷰를 작성하셨습니다!!", Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                ReviewActivity.startActivity(this, reserveList)
                            }
                        }
                })
                reserve_list.adapter = reserveListAdapter
            }
            reserveListAdapter?.update(reserveLists)
        })

    }
    private fun initRecyclerView() {
        reserve_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    reserveListAdapter?.getLastReserveId(lastVisibleItemPosition)?.let { reserveViewModel.getReserveList(it) }
                }
            }
        })
    }

    private fun initListener() {
        close_reserve_list.setOnClickListener {
            finish()
        }
    }
}