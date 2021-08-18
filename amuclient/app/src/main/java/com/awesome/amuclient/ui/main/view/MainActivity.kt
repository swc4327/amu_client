package com.awesome.amuclient.ui.main.view

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.ui.main.view.storelist.BaseBallFragment
import com.awesome.amuclient.ui.main.view.storelist.BowlingFragment
import com.awesome.amuclient.ui.main.view.storelist.KaraokeFragment
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseViewModel : FirebaseViewModel

    private var fusedLocationClient : FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private var lat: Double? = null
    private var lng: Double? = null

    companion object {
        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
            if(fusedLocationClient != null) {
                fusedLocationClient?.removeLocationUpdates(locationCallback)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        initListener()

    }

    private fun initListener() {
        menu_bar_1.setOnClickListener {
            if (lat != null && lng != null) {
                goKaraoke()
            }
        }

        menu_bar_2.setOnClickListener {
            if (lat != null && lng != null) {
                goBaseball()
            }
        }

        menu_bar_3.setOnClickListener {
            if (lat != null && lng != null) {
                goBowling()
            }
        }

        reserve_list.setOnClickListener {
            val intent = Intent(this, ReserveListActivity::class.java)
            startActivity(intent)
        }

        my_page.setOnClickListener {
            if (!firebaseViewModel.isLoggedIn()) { //로그인 no
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else { //로그인 ok
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            }
        }

        set_place.setOnClickListener {
            getLocation()
        }
    }

    private fun goKaraoke() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        supportFragmentManager.beginTransaction()
            .replace(R.id.store_list_area, KaraokeFragment().apply {
                arguments = Bundle().apply {
                    putString("lat", lat.toString())
                    putString("lng", lng.toString())
                }
            })
            .commit()
    }

    private fun goBaseball() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        supportFragmentManager.beginTransaction()
            .replace(R.id.store_list_area, BaseBallFragment().apply {
                arguments = Bundle().apply {
                    putString("lat", lat.toString())
                    putString("lng", lng.toString())
                }
            })
            .commit()
    }

    private fun goBowling() {
        menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
        menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
        supportFragmentManager.beginTransaction()
            .replace(R.id.store_list_area, BowlingFragment().apply {
                arguments = Bundle().apply {
                    putString("lat", lat.toString())
                    putString("lng", lng.toString())
                }
            })
            .commit()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationReq = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    for (location in locationResult.locations) {
                        println("latitude: " + location.latitude)
                        println("longitude: " + location.longitude)
                        lat = location.latitude
                        lng = location.longitude

                        goKaraoke()

                    }
                }
            }
        }
        fusedLocationClient!!.requestLocationUpdates(
            locationReq,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}