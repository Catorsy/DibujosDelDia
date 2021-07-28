package com.example.dibujosdeldia.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.MainFragmentBinding
import com.example.dibujosdeldia.ui.main.picture.PictureOfTheDayData
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner,
            Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) = with(binding) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.link_is_empty), Toast.LENGTH_SHORT).show()
                } else {
                    //showSuccess()
                    image_view.load(url) {
                        lifecycle(this@MainFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        waitForIt.visibility = View.GONE
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                waitForIt.visibility = View.VISIBLE
            }
            is PictureOfTheDayData.Error -> {
                waitForIt.visibility = View.GONE
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}