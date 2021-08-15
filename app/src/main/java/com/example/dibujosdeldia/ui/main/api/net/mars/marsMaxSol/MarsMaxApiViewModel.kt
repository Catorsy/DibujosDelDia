package com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dibujosdeldia.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsMaxApiViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsMaxApiData> = MutableLiveData(),
    private val retrofitImpl: MarsMaxApiRetrofitImpl = MarsMaxApiRetrofitImpl()
) :
    ViewModel() {
    fun getData(): LiveData<MarsMaxApiData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsMaxApiData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsMaxApiData.Error(Throwable("Вам нужен API ключ"))
        } else {
            retrofitImpl.getRetrofitImpl().getMaxSol(apiKey).enqueue(object :
                Callback<MarsMaxApiResponseData> {
                override fun onResponse(
                    call: Call<MarsMaxApiResponseData>,
                    response: Response<MarsMaxApiResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            MarsMaxApiData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                MarsMaxApiData.Error(Throwable("Неизвестная ошибка"))
                        } else {
                            liveDataForViewToObserve.value =
                                MarsMaxApiData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsMaxApiResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = MarsMaxApiData.Error(t)
                }
            })
        }
    }
}