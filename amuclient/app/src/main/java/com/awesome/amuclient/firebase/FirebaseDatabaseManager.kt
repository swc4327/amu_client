package com.awesome.amuclient.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.firebase.FirebaseAuthManager.getUid
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDatabaseManager {
    private val db = FirebaseFirestore.getInstance()

    fun addDatabase(collectionPath : String, field : String, value : String, status : MutableLiveData<Int>) {
        db.collection(collectionPath)
                .document(getUid())
                .set(hashMapOf(
                        field to value
                ))
                .addOnSuccessListener {
                    Log.e("Join To Manager", "성공")
                    status.value = 200

                }.addOnFailureListener{
                    Log.e("Join To Manager", "실패")
                    println(it)
                }
    }

    fun getDatabase(
        collectionPath: String,
        nickname: MutableLiveData<String>
    ) {
        val docRef = db.collection(collectionPath).document(getUid())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            nickname.value = documentSnapshot.get("nickname").toString()
        }.addOnFailureListener {
            println(it)
        }
    }
}