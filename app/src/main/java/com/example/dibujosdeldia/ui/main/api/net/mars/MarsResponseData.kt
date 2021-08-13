package com.example.dibujosdeldia.ui.main.api.net.mars

import com.google.gson.annotations.SerializedName

data class MarsResponseData(
    @field:SerializedName("photos") val photos: Array<MarsPhotoResponseData>,
)
