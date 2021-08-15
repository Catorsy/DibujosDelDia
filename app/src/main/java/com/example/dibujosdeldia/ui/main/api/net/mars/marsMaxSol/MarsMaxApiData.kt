package com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol

sealed class MarsMaxApiData {
    data class Success(val serverResponseData: MarsMaxApiResponseData) : MarsMaxApiData()
    data class Error(val error : Throwable) : MarsMaxApiData()
    data class Loading(val progress : Int?) : MarsMaxApiData()
}