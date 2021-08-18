package com.awesome.amuclient.firebase

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

object FirebaseStorageManager {

    fun uploadTask(bitmapDrawable: BitmapDrawable,
                   stringValue: String,
                   taskToString: MutableLiveData<String>) {
        val bitmap = bitmapDrawable.bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var task = FirebaseStorage.getInstance().reference
                .child(FirebaseAuthManager.getUid() + stringValue)

        val uploadTask = task.putBytes(data)

        uploadTask.addOnFailureListener {

        }.addOnSuccessListener {
            task.downloadUrl
                    .addOnCompleteListener { task ->
                        taskToString.value = task.result.toString()
                    }
        }
    }
}