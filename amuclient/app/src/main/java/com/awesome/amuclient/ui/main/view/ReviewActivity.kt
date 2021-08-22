package com.awesome.amuclient.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.ReserveList
import com.awesome.amuclient.data.model.Review
import com.awesome.amuclient.ui.main.viewmodel.*
import com.awesome.amuclient.ui.main.viewmodel.factory.ReviewViewModelFactory
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var reviewViewModel: ReviewViewModel

    private var reserveList : ReserveList? = null
    private var clientId : String = ""

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;

        fun startActivity(activity : AppCompatActivity, reserveList : ReserveList) {
            val intent = Intent(activity, ReviewActivity::class.java)
            intent.putExtra("reserveList", reserveList)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        reserveList = intent.getParcelableExtra("reserveList")

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientId = firebaseViewModel.getUid()

        reviewViewModel = ViewModelProvider(this, ReviewViewModelFactory(clientId)).get(ReviewViewModel::class.java)



        initLayout()
        initListener()

        observe()


    }

    private fun observe() {
        firebaseViewModel.taskToString.observe(this, Observer<String> {
            val review: Review = Review(null, it,
                    review_comment.text.toString(),
                    null,
                    reserveList!!.store.id.toString(),
                    firebaseViewModel.getUid(),
                    rbPoint.text.toString(),
                    reserveList!!.reserve.id.toString())
            reviewViewModel.addReview(review)
        })

        reviewViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                finish()
            }
        })

    }

    private fun initLayout() {
        store_name.setText(reserveList!!.store.name)

    }

    private fun initListener() {
        add_image.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                        PERMISSION_CODE
                    )
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }

        rb.setOnRatingBarChangeListener { _, rating, _ ->
            rbPoint.setText(rating.toString())
        }

        submit_review.setOnClickListener {
            firebaseViewModel.uploadTask(add_image.drawable as BitmapDrawable, reserveList!!.reserve.id.toString() +"_review")
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
                IMAGE_PICK_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            add_image.setImageURI(data?.data)
        }
    }
}