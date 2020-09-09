package com.decagon.pokemonapicall.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decagon.pokemonapicall.R
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class UploadImage : AppCompatActivity() {
    var filePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        uploadImage()


    }

    fun uploadImage() {
        val file = File(filePath)
        val requestBody = RequestBody.create(MediaType.parse("image/"), file)
    }
}