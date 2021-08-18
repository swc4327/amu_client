package com.awesome.amuclient.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Reserve
import com.awesome.amuclient.data.model.ReserveList
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.map.MapManager
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {

    private var mapManager : MapManager? = null
    private var store : Store? = null
    private var reserve : Reserve? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        reserve = intent.getParcelableExtra("reserve")
        store = intent.getParcelableExtra("store")

        mapManager = MapManager(this)
        mapManager?.addMapListener(info_map_view)
        store?.lat?.toDouble()?.let {lat->
            store?.lng?.toDouble()?.let {lng ->
                mapManager?.setMap("업체위치", lat, lng) }
        }

        initLayout()
        initListener()
    }

    private fun initLayout() {
        detail_store_name.text = store?.name
        detail_store_place.text = store?.place
        detail_store_place_detail.text = store?.place_detail
        detail_date.text = reserve?.date
    }

    private fun initListener() {
        close_reserve_detail.setOnClickListener {
            finish()
        }

        detail_reserve_cancel_button.setOnClickListener {
            //구현해야함//
            finish()
        }
    }
}