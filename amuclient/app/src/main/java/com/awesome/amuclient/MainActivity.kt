package com.awesome.amuclient

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
import com.awesome.amuclient.ui.LoginActivity
import com.awesome.amuclient.ui.MyPageActivity
import com.awesome.amuclient.ui.ReserveListActivity
import com.awesome.amuclient.ui.storelist.BaseBallFragment
import com.awesome.amuclient.ui.storelist.BowlingFragment
import com.awesome.amuclient.ui.storelist.KaraokeFragment
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_bottom.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var lat: Double? = null
    private var lng: Double? = null

    private var fusedLocationClient : FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    override fun onPause() {
        super.onPause()
            if(fusedLocationClient != null) {
                fusedLocationClient!!.removeLocationUpdates(locationCallback)
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        //auth.signOut()

        reserve_list.setOnClickListener {
            val intent = Intent(this, ReserveListActivity::class.java)
            startActivity(intent)
        }

        my_page.setOnClickListener {
            if (auth.currentUser == null) { //로그인 no
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

        menu_bar_1.setOnClickListener {
            if (lat != null && lng != null) {
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
        }

        menu_bar_2.setOnClickListener {
            if (lat != null && lng != null) {
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
        }

        menu_bar_3.setOnClickListener {
            if (lat != null && lng != null) {
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

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    for (location in locationResult.locations) {
                        println("latitude: " + location.latitude)
                        println("longitude: " + location.longitude)
                        lat = location.latitude
                        lng = location.longitude

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
                }
            }
        }
        fusedLocationClient!!.requestLocationUpdates(
            locationReq,
            locationCallback,
            Looper.getMainLooper()
        )
    }

//        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location == null) {
//                    Log.e("Get location test", "location get fail")
//                } else {
//                    Log.d("Get location test", "${location.latitude} , ${location.longitude}")
//                    lat = location.latitude
//                    lng = location.longitude
//                    //getStoreList()
//
//                    menu_bar_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)
//                    menu_bar_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
//                    menu_bar_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
//
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.store_list_area, KaraokeFragment().apply {
//                            arguments = Bundle().apply {
//                                putString("lat", lat.toString())
//                                putString("lng", lng.toString())
//                            }
//                        })
//                        .commit()
//                }
//            }
//            .addOnFailureListener {
//                Log.e("Get location test", "location error is ${it.message}")
//                it.printStackTrace()
//            }

    fun getHashKey(){
        var packageInfo : PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures){
            try{
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch(e: NoSuchAlgorithmException){
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }
}