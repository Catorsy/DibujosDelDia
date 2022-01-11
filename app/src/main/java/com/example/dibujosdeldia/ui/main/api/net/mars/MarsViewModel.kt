package com.example.dibujosdeldia.ui.main.api.net.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dibujosdeldia.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel (
    private val lifeDataForViewToObserve : MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl : MarsRetrofitImpl = MarsRetrofitImpl()
        ) :
ViewModel() {
    fun getData(sol : Int, camera : String) : LiveData<MarsData> {
        sendServerRequest(sol, camera)
        return lifeDataForViewToObserve
    }

    private fun sendServerRequest(sol: Int, camera: String) {
        lifeDataForViewToObserve.value = MarsData.Loading(null)
        val apiKey : String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("Вам нужен API ключ"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(sol, camera, apiKey).enqueue(object :
            Callback<MarsResponseData> {
                override fun onResponse(
                    call: Call<MarsResponseData>,
                    response: Response<MarsResponseData>
                ){
                    if (response.isSuccessful && response.body() != null) {
                        lifeDataForViewToObserve.value =
                            MarsData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            lifeDataForViewToObserve.value =
                                MarsData.Error(Throwable("Неизвестная ошибка"))
                        } else {
                            lifeDataForViewToObserve.value =
                                MarsData.Error(Throwable(message))
                        }
                    }
                }
                override fun onFailure(call: Call<MarsResponseData>, t: Throwable) {
                    lifeDataForViewToObserve.value = MarsData.Error(t)
                }
            })
        }
    }
}