package com.example.dibujosdeldia.ui.main.api.net.mars

sealed class MarsData {
    data class Success(val serverResponseData: MarsResponseData) : MarsData()
    data class Error(val error : Throwable) : MarsData()
    data class Loading(val progress : Int?) : MarsData()
}
