package com.example.dibujosdeldia.ui.main.api

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.FragmentEarthBinding
import com.example.dibujosdeldia.ui.main.api.net.earth.EarthData
import com.example.dibujosdeldia.ui.main.api.net.earth.EarthViewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class EarthFragment : Fragment() {
    private lateinit var binding : FragmentEarthBinding
    val viewModel : EarthViewModel by viewModels()
    private var lat = 55F
    private var lon = 373F

    val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

    val currentDateofWashington = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ZonedDateTime.now(ZoneId.of("UTC-4")).toLocalDate()
    } else {
        val currentDateofWashington = currentDate
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(lon, lat, currentDateofWashington.toString()).observe(viewLifecycleOwner,
            Observer<EarthData> { renderData(it) })

        binding.lotLanOk.setOnClickListener {
            lat = binding.getLat.text.toString().toFloat()
            lon = binding.getLon.text.toString().toFloat()
            if (lat > 90 || lat < -90){
                Toast.makeText(context, "Широта должна укладываться от северного полюса — +90°, до южного полюса — −90°", Toast.LENGTH_SHORT).show()
            }
            if (lon > 180 || lon < -180){
                Toast.makeText(context, "Долгота должна укладываться от 0° до +180° на восток и от 0° до −180° на запад", Toast.LENGTH_SHORT).show()
            }
            viewModel.getData(lon, lat, currentDateofWashington.toString()).observe(viewLifecycleOwner,
                Observer<EarthData> { renderData(it) })
        }
    }

    private fun renderData(data: EarthData) = with(binding) {
        when (data) {
            is EarthData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.link_is_empty), Toast.LENGTH_SHORT).show()
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        waitForIt.visibility = View.GONE
                    }
                }
            }
            is EarthData.Loading -> {
                waitForIt.visibility = View.VISIBLE
            }
            is EarthData.Error -> {
                waitForIt.visibility = View.GONE
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
