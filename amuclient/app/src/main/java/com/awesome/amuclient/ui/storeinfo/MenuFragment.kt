package com.awesome.amuclient.ui.storeinfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amuclient.R
import com.awesome.amuclient.api.Constants
import com.awesome.amuclient.api.response.MenuListResponse
import com.awesome.amuclient.api.service.GetMenuListService
import com.awesome.amuclient.model.Menu
import com.awesome.amuclient.ui.adapter.MenuListAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MenuFragment() : Fragment() {

    private var menus : ArrayList<Menu> = ArrayList<Menu>()
    private var menuListAdapter: MenuListAdapter? = null
    private var storeId: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_menu, container, false)
        storeId = arguments?.getString("store_id")
        getMenuList()

        return view
    }

    private fun getMenuList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(GetMenuListService::class.java)
        Log.e("store_id check", storeId.toString())

        joinApi.getMenuList(storeId.toString())
            .enqueue(object : Callback<MenuListResponse> {

                override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
                    Log.e("Menu Retrofit getMenu", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<MenuListResponse>,
                    response: Response<MenuListResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Getmenu Retrofit", "success")
                        menus.addAll(response.body()!!.menus)
                        menuListAdapter =
                            MenuListAdapter(
                                requireContext(),
                                menus
                            )
                        menu_list.adapter = menuListAdapter
                    } else {

                    }
                }
            })

    }
}