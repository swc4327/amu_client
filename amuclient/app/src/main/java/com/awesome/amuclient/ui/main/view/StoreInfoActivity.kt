package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.ui.main.view.storeinfo.InfoFragment
import com.awesome.amuclient.ui.main.view.storeinfo.MenuFragment
import com.awesome.amuclient.ui.main.view.storeinfo.ReviewFragment
import kotlinx.android.synthetic.main.activity_store_info.*

class StoreInfoActivity : AppCompatActivity() {

    private var store : Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_info)

        store = intent.getParcelableExtra("store")


        initLayout()
        initListener()

        goMenuFragment()
    }

    private fun initLayout() {
        store_info_name.setText(store!!.name)
        point.text = store!!.point!!.toString()
        count.text = "("+store!!.count.toString()+")"
    }

    private fun initListener() {
        //예약하기
        reserve_button.setOnClickListener {
            val intent = Intent(this, ReserveActivity::class.java)
            intent.putExtra("storeId", store!!.id.toString())
            startActivity(intent)
        }

        close_store_info.setOnClickListener {
            finish()
        }

        menu_bar_1.setOnClickListener {
            goMenuFragment()
        }

        menu_bar_2.setOnClickListener {
            goInfoFragment()
        }

        menu_bar_3.setOnClickListener {
            goReviewFragment()
        }
    }

    private fun goMenuFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, MenuFragment().apply {
                arguments = Bundle().apply {
                    putString("store_id", store!!.id.toString())
                }
            })
            .commit()
    }

    private fun goInfoFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, InfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("store", store)
                }
            })
            .commit()
    }

    private fun goReviewFragment() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString("store_id", store!!.id.toString())
                }
            })
            .commit()
    }
}