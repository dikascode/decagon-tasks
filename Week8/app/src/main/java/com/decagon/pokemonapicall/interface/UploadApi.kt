package com.decagon.pokemonapicall.`interface`

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadApi {
    @Multipart
    @POST("upload")
     fun uploadImage(@Part part: MultipartBody.Part,  @Part("someday") requestBody: RequestBody): Call<RequestBody>
}