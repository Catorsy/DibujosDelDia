package com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol


import com.google.gson.annotations.SerializedName

//сюда приходит объект, не массив
data class MarsMaxApiResponseData (
    @field:SerializedName("photo_manifest") val photo_manifest: MarsMaxApiPhotoMResponseData,
)
