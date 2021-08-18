package com.awesome.amuclient.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Client
import com.awesome.amuclient.ui.main.viewmodel.ClientViewModel
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join_info.*

class JoinInfoActivity : AppCompatActivity() {

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;

        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, JoinInfoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var clientViewModel : ClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_info)

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)

        initListener()
        observe()


    }

    private fun observe() {
        clientViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                Toast.makeText(this, "회원가입이 완료 되었어요!!", Toast.LENGTH_LONG).show()
                MainActivity.startActivity(this)
            }
        })

        firebaseViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                firebaseViewModel.uploadTask(join_info_profile_img.drawable as BitmapDrawable, "_profile")
            }
        })

        firebaseViewModel.taskToString.observe(this, Observer<String> {taskToString ->
            val uid = firebaseViewModel.getUid()
            val nickname = join_info_nickname.text.toString()
            val client = Client(uid, nickname, taskToString, "0", "0")
            clientViewModel.addClient(client)
        })
    }

    private fun initListener() {
        //프로필 설정
        join_info_profile_img.setOnClickListener {
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

        join_info_login_button.setOnClickListener {
            firebaseViewModel.addDatabase("clients", "nickname", join_info_nickname.text.toString())

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

    //handle requested permission result
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
            join_info_profile_img.setImageURI(data?.data)
        }
    }
}