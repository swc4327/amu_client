package com.awesome.amuclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.awesome.amuclient.R
import kotlinx.android.synthetic.main.activity_reserve_detail.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class ReserveDetailActivity : AppCompatActivity() {

    private var store_name = ""
    private var store_place = ""
    private var store_place_detail = ""
    private var date: String? = null

    private var lat: Double? = null
    private var lng: Double? = null
    private var mapView : MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_detail)

        store_name = intent.getStringExtra("store_name").toString()
        store_place = intent.getStringExtra("store_place").toString()
        store_place_detail = intent.getStringExtra("store_place_detail").toString()
        date = intent.getStringExtra("date").toString()

        lat = intent.getStringExtra("lat")!!.toDouble()
        lng = intent.getStringExtra("lng")!!.toDouble()

        detail_store_name.setText(store_name)
        detail_store_place.setText(store_place)
        detail_store_place_detail.setText(store_place_detail)
        detail_date.setText(date)

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
        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()),true)
        var marker = MapPOIItem()
        marker.setItemName("업체위치")
        marker.setTag(0)
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat!!.toDouble(), lng!!.toDouble()))
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin)
        //marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView!!.addPOIItem(marker)

        close_reserve_detail.setOnClickListener {
            finish()
        }

        detail_reserve_cancel_button.setOnClickListener {

            finish()
        }
    }
}