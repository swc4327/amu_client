package com.awesome.amuclient.ui.main.view.storeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Menu
import com.awesome.amuclient.ui.main.adapter.MenuAdapter
import com.awesome.amuclient.ui.main.viewmodel.MenuViewModel
import com.awesome.amuclient.ui.main.viewmodel.MenuViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment() : Fragment() {


    private lateinit var menuViewModel : MenuViewModel

    private var menuAdapter: MenuAdapter? = null
    private var storeId: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_menu, container, false)
        storeId = arguments?.getString("store_id")

        var factory = MenuViewModelFactory(storeId.toString())
        menuViewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)

        menuViewModel.getMenu(storeId)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViewModel.menus.observe(viewLifecycleOwner, Observer<ArrayList<Menu>> {menus ->
            if (menuAdapter == null) {
                menuAdapter = MenuAdapter(arrayListOf() , Glide.with(this))
                menu_list.adapter = menuAdapter
            }
            //menuAdapter!!.update(menus)
        })

    }
}