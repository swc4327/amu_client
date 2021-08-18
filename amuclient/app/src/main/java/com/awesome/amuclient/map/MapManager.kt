package com.awesome.amuclient.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.RelativeLayout
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapManager(val context : Context) {
    private var mapView: MapView? = null

    fun getLocation(locationName: String, markerName : String): Pair<Double, Double> {
        val geoCoder = Geocoder(context)
        val result: List<Address> = geoCoder.getFromLocationName(locationName, 1)
        setMap(markerName, result[0].latitude, result[0].longitude)
        return Pair(result[0].latitude, result[0].longitude)

    }

    fun setMap(markerName : String, latitude : Double, longitude : Double) {
        mapView?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true)
        val marker = MapPOIItem()
        marker.itemName = markerName
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        marker.markerType = MapPOIItem.MarkerType.BluePin
        mapView?.addPOIItem(marker);
    }

    fun addMapListener(map_view : RelativeLayout?) {
        mapView = MapView(context)
        this.mapView!!.setMapViewEventListener(object : MapView.MapViewEventListener {
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
                println("onMapViewCenterPointMoved")
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
        map_view?.addView(mapView)
    }
}