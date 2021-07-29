package com.example.dibujosdeldia.ui.main

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.MainFragmentBinding
import com.example.dibujosdeldia.ui.main.picture.PictureOfTheDayData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : Fragment() {
    val viewModel : MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner,
            Observer<PictureOfTheDayData> { renderData(it) })

        //ищем по нажатию в википедии, сделаем три языка. Как бы это автоматизировать на все...
        input_layout.setStartIconOnClickListener {
            val myTextLength = input_edit_text?.text?.length
            if (myTextLength != null && myTextLength > 20) { //не знаю, как узнать, переполнен ли счетчик, напишу условия сама
                input_layout.isStartIconCheckable = false //И что?! Ничего не происходит
                    Toast.makeText(context, getString(R.string.text_is_too_long), Toast.LENGTH_SHORT).show()
            } else {
               startActivity(Intent(Intent.ACTION_VIEW).apply {
                   var lang = Locale.getDefault().getLanguage() //получаем язык системы
                   when {
                       lang.equals("ru") -> {
                           data = Uri.parse("https://ru.wikipedia.org/wiki/${input_edit_text.text.toString()}")
                       }
                       lang.equals("en") -> {
                           data = Uri.parse("https://com.wikipedia.org/wiki/${input_edit_text.text.toString()}")
                       }
                       lang.equals("es") -> {
                           data = Uri.parse("https://es.wikipedia.org/wiki/${input_edit_text.text.toString()}")
                       }
                       else -> {
                           data = Uri.parse("https://com.wikipedia.org/wiki/${input_edit_text.text.toString()}")
                       }
                   }
               })
            }
        }

        setBottomSheetBehavior(bottom_sheet_container)
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
//                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
//                }
//            }
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                //TODO("not implemented")
//            }
//        })
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

                        bottom_sheet_description_header.text = serverResponseData.title
                        bottom_sheet_description.text = serverResponseData.explanation
                        if(serverResponseData.title.equals("")) {
                            bottom_sheet_description.text =  getString(R.string.no_description)
                        }
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

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_DRAGGING
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}