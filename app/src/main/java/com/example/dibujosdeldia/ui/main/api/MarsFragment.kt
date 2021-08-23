package com.example.dibujosdeldia.ui.main.api


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.FragmentMarsBinding
import com.example.dibujosdeldia.ui.main.api.net.mars.MarsData
import com.example.dibujosdeldia.ui.main.api.net.mars.MarsViewModel
import com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol.MarsMaxApiData
import com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol.MarsMaxApiViewModel
import com.squareup.picasso.Picasso
import java.lang.NumberFormatException

class MarsFragment : Fragment() {
    private lateinit var binding: FragmentMarsBinding
    val viewModel: MarsViewModel by viewModels()
    val viewModelForMaxSol : MarsMaxApiViewModel by viewModels()

    private var startSol = 2523
    private var maxSol = 3206
    private var startSolRandom = (26..maxSol).random()
    private val frontCamera = "fhaz"
    private val backCamera = "rhaz"
    private val mastCamera = "mast"
    private val navCamera = "navcam"
    private val chemCamera = "chemcam"
    private val mahliCamera = "mahli"
    private var camera = frontCamera
    private lateinit var myLink : String
    private var myChoisedSol = 0

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observerMaxSol = Observer<MarsMaxApiData> { renderDataSol(it) }
        val observer = Observer<MarsData> { try {renderData(it)} catch (e: NoSuchElementException) {
            Toast.makeText(context, "На сол ${startSol} изображения с выбранной камеры нет.", Toast.LENGTH_LONG).show()
        } }
        viewModelForMaxSol.getData().observe(viewLifecycleOwner, observerMaxSol)
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

        binding.takeMySolButton.setOnClickListener {
            try{
                myChoisedSol = binding.enterMySol.text.toString().toInt()
            } catch (e : NumberFormatException) {
                e.printStackTrace()
            }
            if (myChoisedSol in 26..maxSol) {
                startSol = myChoisedSol
                getData()
                hideKeyboard()
            } else Toast.makeText(context, getString(R.string.wrong_sol), Toast.LENGTH_LONG).show()
        }

        binding.frontRadioButton.setOnClickListener {
            camera = frontCamera
            getData()
        }
        binding.backRadioButton.setOnClickListener {
            camera = backCamera
            getData()
        }
        binding.matchRadioButton.setOnClickListener {
                camera = mastCamera
                getData()
        }
        binding.chemRadioButton.setOnClickListener {
            camera = chemCamera
            getData()
        }
        binding.navRadioButton.setOnClickListener {
            camera = navCamera
            getData()
        }
        binding.mahliRadioButton.setOnClickListener {
            camera = mahliCamera
            getData()
        }

        binding.curiosityInfo.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.
           // setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.
            setCustomAnimations(R.animator.start_animation, R.animator.end_animation)?.
            replace(R.id.api_container, CuriosityInfoFragment())?.
            addToBackStack("")?.commit()
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
                    binding.myRandomSol.text = startSol.toString()
                    binding.earthDate.text = earthData
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

    private fun renderDataSol(data: MarsMaxApiData) = with(binding) {
        when (data) {
            is MarsMaxApiData.Success -> {
                val serverResponseData = data.serverResponseData
                maxSol = serverResponseData.photo_manifest.max_sol!!
                startSol = maxSol
                getData()
                enterMySol.hint = "с 26 по ${maxSol} сол"
            }
            is MarsMaxApiData.Loading -> {
                enterMySol.setHint("...")
            }
            is MarsMaxApiData.Error -> {
                waitForIt.visibility = View.GONE
                enterMySol.setHint("NASA не отвечает")
            }
        }
    }

    private fun getData() {
        viewModel.getData(startSol, camera)
    }

    private fun getRandomNumber(){
        startSolRandom = (26..maxSol).random()
    }
}