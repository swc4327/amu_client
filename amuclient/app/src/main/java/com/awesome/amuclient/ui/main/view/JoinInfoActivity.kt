package com.awesome.amuclient.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Constants
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.service.SignUpService
import com.awesome.amuclient.data.model.Client
import com.awesome.amuclient.ui.main.viewmodel.ClientViewModel
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.util.FirebaseUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_join_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class JoinInfoActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    private lateinit var firebaseViewModel : FirebaseViewModel
    private lateinit var clientViewModel : ClientViewModel

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_info)

        auth = FirebaseAuth.getInstance()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)

        initListener()
        observe()


    }

    private fun observe() {
        clientViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                Toast.makeText(this, "회원가입이 완료 되었어요!!", Toast.LENGTH_LONG).show()
                val intent = Intent(this@JoinInfoActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })

        firebaseViewModel.taskToString.observe(this, Observer<String> {
            val taskToString = it
            db.collection("clients")
                .document(auth.currentUser?.uid.toString())
                .set(hashMapOf(
                    "nickname" to join_info_nickname.text.toString()
                ))
                .addOnSuccessListener {
                    Log.e("Join To Manager", "성공")
                    val uid = firebaseViewModel.getUid()
                    val nickname = join_info_nickname.text.toString()
                    val client = Client(uid, nickname, taskToString, "0", "0")
                    clientViewModel.addClient(client)

                }.addOnFailureListener{
                    Log.e("JoinInfoActivity", "실패")
                    println(it)
                }
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
            firebaseViewModel.uploadTask(join_info_profile_img.drawable as BitmapDrawable, "_profile")

        }
    }

//    private fun addClient(client: HashMap<String, String>, task: Task<Uri>) {
//        db.collection("clients")
//            .document(auth.currentUser?.uid.toString())
//            .set(client)
//            .addOnSuccessListener {
//                Log.e("Join To Client", "성공")
//
//                val gson = GsonBuilder().setLenient().create()
//                val retrofit = Retrofit.Builder()
//                    .baseUrl(Constants.serverUrl)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build()
//
//                val joinApi = retrofit.create(SignUpService::class.java)
//
//                val uid = FirebaseUtils.getUid()
//                val nickname = join_info_nickname.text.toString()
//                Log.e("Check nickname", nickname)
//
//                val client =
//                    Client(uid, nickname, task.result.toString(), "0", "0")
//
//                joinApi.addClient(client)
//                    .enqueue(object : Callback<DefaultResponse> {
//
//                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                            Log.e("retrofit add client", "실패")
//                            Log.e("Check", t.toString())
//                        }
//
//                        override fun onResponse(
//                            call: Call<DefaultResponse>,
//                            response: Response<DefaultResponse>
//                        )  {
//                            if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                                Log.e("JoinInfoActivity", "success")
//                                val intent = Intent(this@JoinInfoActivity, MainActivity::class.java)
//                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                startActivity(intent)
//
//                            } else {
//                                Log.e("JoinInfoActivity", "실패")
//                            }
//                        }
//                    })
//            }.addOnFailureListener{
//                Log.e("JoinInfoActivity", "실패")
//            }
//    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
                IMAGE_PICK_CODE
        )
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
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