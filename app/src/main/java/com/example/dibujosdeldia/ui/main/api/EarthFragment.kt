package com.example.dibujosdeldia.ui.main.api

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.FragmentEarthBinding
import com.example.dibujosdeldia.ui.main.api.net.earth.EarthData
import com.example.dibujosdeldia.ui.main.api.net.earth.EarthViewModel
import com.example.dibujosdeldia.ui.main.api.notes.RecyclerFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_earth_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_earth.*
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class EarthFragment : Fragment() {
    private lateinit var binding : FragmentEarthBinding
    val viewModel : EarthViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var lat = 30F
    private var lon = -93F
    private var dim = ZoomHolder.BIGZOOM //управляет тем, сколько на картинку поместится
    private var bigZoom = true

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
        val observer = Observer<EarthData> { renderData(it) }
        viewModel.getData(lon, lat, dim, currentDateofWashington.toString()).observe(viewLifecycleOwner, observer)
        getData()

        setBottomSheetBehavior(bottom_earth_sheet_container)

        binding.lotLanOk.setOnClickListener {
            try {
                lat = binding.getLat.text.toString().toFloat()
                lon = binding.getLon.text.toString().toFloat()
                if (lat > 90 || lat < -90){
                    Toast.makeText(context, getString(R.string.lat_help), Toast.LENGTH_SHORT).show()
                }
                if (lon > 180 || lon < -180){
                    Toast.makeText(context, getString(R.string.lon_help), Toast.LENGTH_SHORT).show()
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Вы ничего не ввели.", Toast.LENGTH_LONG).show()
            }
            getData()
            hideKeyboard()
        }

        binding.closerLongerButton.setOnClickListener {
            if(bigZoom){
                dim = ZoomHolder.LITTLEZOOM
                bigZoom = false
                binding.closerLongerButton.text = getString(R.string.closer)
            } else {
                dim = ZoomHolder.BIGZOOM
                binding.closerLongerButton.text = getString(R.string.longer)
                bigZoom = true
            }
            getData()
        }

        binding.addMyCoordButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.
            //setCustomAnimations(R.animator.start_animation, R.animator.end_animation)?.
            replace(R.id.api_container, RecyclerFragment())?.
            addToBackStack("")?.commit()
        }

    }

    private fun getData(){
        viewModel.getData(lon, lat, dim, currentDateofWashington.toString())
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_DRAGGING
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
                        closerLongerButton.visibility = View.VISIBLE
                    }
                }
            }
            is EarthData.Loading -> {
                waitForIt.visibility = View.VISIBLE
            }
            is EarthData.Error -> {
                waitForIt.visibility = View.GONE
                closerLongerButton.visibility = View.GONE
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

object ZoomHolder {
    const val LITTLEZOOM = 0.25F
    const val BIGZOOM = 0.1F
}
