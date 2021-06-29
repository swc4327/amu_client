package com.awesome.amuclient.ui.storelist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amuclient.R
import com.awesome.amuclient.api.Constants
import com.awesome.amuclient.api.response.StoreListResponse
import com.awesome.amuclient.api.service.GetBaseBallListService
import com.awesome.amuclient.model.Store
import com.awesome.amuclient.ui.StoreInfoActivity
import com.awesome.amuclient.ui.adapter.StoreListAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_baseball.*
import kotlinx.android.synthetic.main.fragment_baseball.view.*
import kotlinx.android.synthetic.main.fragment_karaoke.*
import kotlinx.android.synthetic.main.fragment_karaoke.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BaseBallFragment : Fragment() {

    private var stores : ArrayList<Store> = ArrayList<Store>()

    private var storeListAdapter: StoreListAdapter? = null


    private var lat: String? = ""
    private var lng: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.baseball_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), StoreInfoActivity::class.java)
            intent.putExtra("name", storeListAdapter!!.getItemName(position).toString())
            intent.putExtra("manager_uid", storeListAdapter!!.getItemManagerUid(position).toString())
            intent.putExtra("store_id", storeListAdapter!!.getItemStoreId(position).toString())
            intent.putExtra("lat", storeListAdapter!!.getItemLat(position).toString())
            intent.putExtra("lng", storeListAdapter!!.getItemLng(position).toString())
            intent.putExtra("place_detail", storeListAdapter!!.getItemPlaceDetail(position).toString())
            intent.putExtra("place", storeListAdapter!!.getItemPlace(position).toString())
            intent.putExtra("point", storeListAdapter!!.getItemPoint(position).toString())
            intent.putExtra("count", storeListAdapter!!.getItemCount(position).toString())
            startActivity(intent)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_baseball, container, false)
        lat = arguments?.getString("lat")
        lng = arguments?.getString("lng")
        getStoreList()
        return view
    }

        private fun getStoreList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(GetBaseBallListService::class.java)



        joinApi.getBaseBallList(lat.toString(),lng.toString())
            .enqueue(object : Callback<StoreListResponse> {

                override fun onFailure(call: Call<StoreListResponse>, t: Throwable) {
                    Log.e("Main Retrofit getStore", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<StoreListResponse>,
                    response: Response<StoreListResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        if(stores != null) //기존 store 목록 지우고 다시 채움
                            stores.clear()
                        stores.addAll(response.body()!!.stores)
                        storeListAdapter =
                            StoreListAdapter(
                                requireContext(),
                                stores
                            )
                        baseball_list.adapter = storeListAdapter
                    } else {

                    }
                }
            })
    }
}