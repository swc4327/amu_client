package com.awesome.amuclient.ui.main.view.storelist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Constants
import com.awesome.amuclient.data.model.Constants.FIRST_CALL
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.ui.main.view.StoreInfoActivity
import com.awesome.amuclient.ui.main.adapter.StoreAdapter
import com.awesome.amuclient.ui.main.viewmodel.StoreViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_bowling.*
import kotlinx.android.synthetic.main.fragment_karaoke.*


class BowlingFragment : Fragment() {

    private var storeAdapter: StoreAdapter? = null

    private var lat: String? = ""
    private var lng: String? = ""
    private lateinit var storeViewModel : StoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lat = arguments?.getString("lat")
        lng = arguments?.getString("lng")

        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bowling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        storeAdapter?.clearStores()
        storeViewModel.getStore(lat, lng,"볼링장", FIRST_CALL)
    }

    private fun observe() {
        storeViewModel.stores.observe(this, Observer<ArrayList<Store>> { stores ->
            if (storeAdapter == null) {
                storeAdapter = StoreAdapter(arrayListOf() , Glide.with(this)) { store ->
                    val intent = Intent(requireContext(), StoreInfoActivity::class.java)
                    intent.putExtra("store", store)
                    startActivity(intent)
                }
                bowling_list.adapter = storeAdapter
            }
            storeAdapter?.update(stores)
        })
    }

    private fun initRecyclerView() {
        bowling_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    storeAdapter?.getLastStoreId(lastVisibleItemPosition)?.let { storeViewModel.getStore(lat,lng, "볼링장", it) }
                }
            }
        })
    }


}