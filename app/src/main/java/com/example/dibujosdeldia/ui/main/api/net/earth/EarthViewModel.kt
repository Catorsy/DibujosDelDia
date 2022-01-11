package com.example.dibujosdeldia.ui.main.api.net.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dibujosdeldia.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: EarthRetrofitImpl = EarthRetrofitImpl()
) :
    ViewModel() {
    fun getData(lon: Float, lat: Float, dim : Float, date: String): LiveData<EarthData> {
        sendServerRequest(lon, lat, dim, date)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(lon: Float, lat: Float, dim : Float, date: String) {
        liveDataForViewToObserve.value = EarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthData.Error(Throwable("Вам нужен API ключ"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(lon, lat, dim, date, apiKey).enqueue(object :
                Callback<EarthResponseData> {
                override fun onResponse(
                    call: Call<EarthResponseData>,
                    response: Response<EarthResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            EarthData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable("Неизвестная ошибка"))
                        } else {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<EarthResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = EarthData.Error(t)
                }
            })
        }
    }
}