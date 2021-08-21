package com.awesome.amuclient.ui.main.view.storeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.map.MapManager
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment() : Fragment() {

    private var store : Store? = null
    private var mapManager : MapManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = arguments?.getParcelable("store")
        mapManager = MapManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        mapManager?.addMapListener(info_map_view)
        store?.lat?.toDouble()?.let {lat-> store?.lng?.toDouble()?.let {lng->
            mapManager?.setMap("업체위치", lat, lng) }
        }

    }
    private fun initLayout() {
        info_place.text = store?.place
        info_place_detail.text = store?.placeDetail
    }

}