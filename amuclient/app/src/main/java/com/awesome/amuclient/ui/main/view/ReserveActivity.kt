package com.awesome.amuclient.ui.main.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Constants
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.service.AddReserveService
import com.awesome.amuclient.data.model.Reserve
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
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

        close_reserve_page.setOnClickListener {
            finish()
        }

        complete_button.setOnClickListener {
            addReserve()
        }
    }
    private fun addReserve() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(AddReserveService::class.java)

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

        joinApi.addReserve(reserve)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit Reserve", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Add Reserve", "success")
                        finish()

                    } else {
                        Log.e("AddReserve", "실패")
                    }
                }
            })
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



//private fun addTransaction() {
//        val gson = GsonBuilder().setLenient().create()
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.serverUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//        val joinApi = retrofit.create(AddTransactionService::class.java)
//
//
//        val transaction = Transaction(null, null, 0, storeId, FirebaseUtils.getUid())
//
//        joinApi.addTransaction(transaction)
//            .enqueue(object : Callback<DefaultResponse> {
//
//                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                    Log.e("Retrofit Transaction", "실패")
//                    Log.e("Check", t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Add Transaction", "success")
//                        addReserve()
//                        //getLocation()
//
//                    } else {
//                        Log.e("Add Transaction", "실패")
//                    }
//                }
//            })
//    }
