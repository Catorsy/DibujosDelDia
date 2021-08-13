package com.example.dibujosdeldia.ui.main.api

import android.content.Intent
import android.net.Uri
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
import com.example.dibujosdeldia.databinding.FragmentMarsBinding
import com.example.dibujosdeldia.ui.main.api.net.mars.MarsData
import com.example.dibujosdeldia.ui.main.api.net.mars.MarsViewModel
import com.squareup.picasso.Picasso

class MarsFragment : Fragment() {
    private lateinit var binding: FragmentMarsBinding
    val viewModel: MarsViewModel by viewModels()

    private var startSol = 309
    private var startSolRandom = (26..3200).random()
    private val camera = "fhaz"
    private val camera2 = "rhaz"
    private lateinit var myLink : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<MarsData> { renderData(it) }
        viewModel.getData(startSol, camera).observe(viewLifecycleOwner, observer)
        getData()

        binding.imageView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(myLink)
            })
        }

        binding.getRandomButtom.setOnClickListener {
            binding.myRandomSol.text = startSolRandom.toString()
            startSol = startSolRandom
            getData()
            getRandomNumber()
        }
    }

    private fun renderData(data: MarsData) = with(binding) {
        when (data) {
            is MarsData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.photos.first().url
                if (url != null) {
                    myLink = url
                }
                val earthData = serverResponseData.photos.first().earth_date

                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.link_is_empty), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.ic_no_photo_vector)
                        .error(R.drawable.ic_load_error_vector)
                        .into(imageView, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                //set animations here

                            }
                            override fun onError(e: java.lang.Exception?) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                            }
                        })
                    waitForIt.visibility = View.GONE
//                    binding.imageView.load(url) {
//                        lifecycle(this@MarsFragment)
//                        error(R.drawable.ic_load_error_vector)
//                        placeholder(R.drawable.ic_no_photo_vector)
//                        waitForIt.visibility = View.GONE
                    //}
                }
            }
            is MarsData.Loading -> {
                waitForIt.visibility = View.VISIBLE
            }
            is MarsData.Error -> {
                waitForIt.visibility = View.GONE
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData() {
        viewModel.getData(startSol, camera)
    }

    private fun getRandomNumber(){
        startSolRandom = (26..3200).random()
    }
}