package com.awesome.amuclient.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.ReserveList
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {

    private var reserveList : ReserveList? =null
    private var mapView : MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        reserveList = intent.getParcelableExtra("reserveList")

        initLayout()
        initListener()
        setMap()
        setLocation()
    }

    private fun initLayout() {
        detail_store_name.setText(reserveList!!.store.name)
        detail_store_place.setText(reserveList!!.store.place)
        detail_store_place_detail.setText(reserveList!!.store.place_detail)
        detail_date.setText(reserveList!!.reserve.date)
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

    private fun setMap() {
        mapView = MapView(this)
        mapView!!.setMapViewEventListener(object : MapView.MapViewEventListener {
            override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDoubleTapped")
            }

            override fun onMapViewInitialized(p0: MapView?) {
                println("onMapViewInitialized")
            }

            override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDragStarted")
            }

            override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
                println("onMapViewMoveFinished")
            }

            override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                println("onMapViewCetnerPointMoved")
            }

            override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
                println("onMapViewDragEnded")
            }

            override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
                println("onMapViewSingleTapped")
            }

            override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
                println("onMapViewZoomLevelChanged")
            }

            override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
                println("onMapViewLongPressed")
            }

        })
        info_map_view.addView(mapView)

    }

    private fun setLocation() {
        mapView!!.setMapCenterPoint(reserveList!!.store.lat?.toDouble()?.let {lat-> reserveList!!.store.lng?.toDouble()?.let {lng -> MapPoint.mapPointWithGeoCoord(lat, lng) } },true)
        var marker = MapPOIItem()
        marker.setItemName("업체위치")
        marker.setTag(0)
        marker.setMapPoint(reserveList!!.store.lat?.toDouble()?.let {lat-> reserveList!!.store.lng?.toDouble()?.let {lng-> MapPoint.mapPointWithGeoCoord(lat, lng) } })
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        mapView!!.addPOIItem(marker)
    }
}