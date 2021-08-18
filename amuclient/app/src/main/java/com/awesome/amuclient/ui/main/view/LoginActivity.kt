package com.awesome.amuclient.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseViewModel : FirebaseViewModel

    companion object {
        fun startActivity(activity : AppCompatActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        initListener()
        observe()

    }

    private fun observe() {
        firebaseViewModel.status.observe(this, Observer<Int> {
            if(it == 200) {
                MainActivity.startActivity(this)
            }
            else {
                //에러처리
            }
        })
    }

    private fun initListener() {
        login_button.setOnClickListener {
            firebaseViewModel.signIn(login_email.text.toString(), login_password.text.toString())
        }

        login_join_button.setOnClickListener{
            JoinActivity.startActivity(this)
        }
    }
}