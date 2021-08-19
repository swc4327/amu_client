package com.awesome.amuclient.ui.main.view.storeinfo

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
import com.awesome.amuclient.data.model.Constants.FIRST_CALL
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeId = arguments?.getString("store_id")
        menuViewModel = ViewModelProvider(this, MenuViewModelFactory(storeId.toString())).get(MenuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        menuAdapter?.clearMenus()
        menuViewModel.getMenu(FIRST_CALL)
    }

    private fun initRecyclerView() {
        menu_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    menuAdapter?.getLastMenuId(lastVisibleItemPosition)?.let { menuViewModel.getMenu(it) }
                }
            }
        })
    }

    private fun observe() {
        menuViewModel.menus.observe(viewLifecycleOwner, Observer<ArrayList<Menu>> {menus ->
            if (menuAdapter == null) {
                menuAdapter = MenuAdapter(arrayListOf() , Glide.with(this))
                menu_list.adapter = menuAdapter
            }
            menuAdapter?.update(menus)
        })
    }
}