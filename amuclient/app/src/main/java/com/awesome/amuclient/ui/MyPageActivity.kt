package com.awesome.amuclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.awesome.amuclient.MainActivity
import com.awesome.amuclient.R
import com.awesome.amuclient.util.FirebaseUtils
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPageActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        auth = FirebaseAuth.getInstance()
        val docRef = db.collection("clients")
            .document(auth.currentUser?.uid.toString())

        docRef.get().addOnSuccessListener { documentSnapshot ->
            nickname_area.setText(documentSnapshot.get("nickname").toString())
        }

        close_my_page.setOnClickListener {
            finish()
        }

        mypage_logout_button.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        mypage_promotion_button.setOnClickListener {
            val intent = Intent(this, PromotionListActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val ref = FirebaseStorage.getInstance()
            .getReference(FirebaseUtils.getUid()+"_profile")

        Log.e("image file url check", ref.toString())

        ref.downloadUrl
            .addOnCompleteListener(OnCompleteListener { task ->
                Log.e("task check", task.result.toString())
                if(task.isSuccessful) {
                    Glide.with(this)
                        .load(task.result)
                        .into(profile_img)
                } else {
                    Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            })
    }
}