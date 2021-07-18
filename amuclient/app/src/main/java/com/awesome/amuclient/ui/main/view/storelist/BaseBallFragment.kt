package com.awesome.amuclient.ui.main.view.storelist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.ui.main.view.StoreInfoActivity
import com.awesome.amuclient.ui.main.adapter.StoreAdapter
import com.awesome.amuclient.ui.main.viewmodel.StoreViewModel
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_baseball.*
import kotlinx.android.synthetic.main.fragment_baseball.view.*
import kotlinx.android.synthetic.main.fragment_karaoke.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BaseBallFragment : Fragment() {


    private var storeAdapter: StoreAdapter? = null
    private lateinit var storeViewModel : StoreViewModel


    private var lat: String? = ""
    private var lng: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeViewModel.stores.observe(this, Observer<ArrayList<Store>> { stores ->
            if (storeAdapter == null) {
                storeAdapter = StoreAdapter(arrayListOf() , Glide.with(this)) { store ->
                    val intent = Intent(requireContext(), StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }
                baseball_list.adapter = storeAdapter
            }
            storeAdapter!!.update(stores)
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_baseball, container, false)
        lat = arguments?.getString("lat")
        lng = arguments?.getString("lng")

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        storeViewModel.getStore(lat, lng,"스크린야구장")

        return view
    }
}