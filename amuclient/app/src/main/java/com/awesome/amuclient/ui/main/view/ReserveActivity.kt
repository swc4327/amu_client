package com.awesome.amuclient.ui.main.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Constants
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.service.AddReserveService
import com.awesome.amuclient.data.model.Reserve
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModelFactory
import com.awesome.amuclient.util.FirebaseUtils
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_reserve.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReserveActivity : AppCompatActivity() {

    private var storeId = ""
    private var lat: Double? = null
    private var lng: Double? = null

    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var reserveViewModel: ReserveViewModel


    private var fusedLocationClient : FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    override fun onPause() {
        super.onPause()
        if(fusedLocationClient != null) {
            fusedLocationClient!!.removeLocationUpdates(locationCallback)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)
        storeId = intent.getStringExtra("storeId").toString()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        var factory = ReserveViewModelFactory(firebaseViewModel.getUid())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)

        initListener()
        observe()
    }

    private fun observe() {
        reserveViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                finish()
            }
        })
    }

    private fun initListener() {
        close_reserve_page.setOnClickListener {
            finish()
        }

        complete_button.setOnClickListener {
            //addReserve()
            val reserve = Reserve(
                null,
                name.text.toString(),
                phone.text.toString(),
                arrive.text.toString(),
                request.text.toString(),
                lat.toString(),
                lng.toString(),
                null,
                storeId,
                firebaseViewModel.getUid(),
                "0",
                "0",
                "0")

            reserveViewModel.addReserve(reserve)
        }
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

        locationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    for (location in locationResult.locations) {
                        println("latitude: " + location.latitude)
                        println("longitude: " + location.longitude)
                        lat = location.latitude
                        lng = location.longitude
                    }
                }
            }
        }
        fusedLocationClient!!.requestLocationUpdates(locationReq, locationCallback,Looper.getMainLooper())
    }
}