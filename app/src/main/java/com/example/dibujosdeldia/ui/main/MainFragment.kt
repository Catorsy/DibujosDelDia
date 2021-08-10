package com.example.dibujosdeldia.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.api.load
import com.example.dibujosdeldia.MainActivity
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.MainFragmentBinding
import com.example.dibujosdeldia.ui.main.api.ApiActivity
import com.example.dibujosdeldia.ui.main.picture.BottomNavigationDrawerFragment
import com.example.dibujosdeldia.ui.main.picture.PictureOfTheDayData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class MainFragment : Fragment() {
    val viewModel : MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val foto = "2020-09-28"
    private lateinit var myLink : String

    val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

    val currentDateofWashington = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ZonedDateTime.now(ZoneId.of("UTC-4")).toLocalDate()
    } else {
        val currentDateofWashington = currentDate
    }
    
    val yesterdayDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //LocalDate.now().minusDays(1).toString()
        ZonedDateTime.now(ZoneId.of("UTC-4")).minusDays(1).toLocalDate()
    } else {
        val yesterdayDate = currentDate
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    val preYesterdayDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //LocalDate.now().minusDays(2).toString()
        ZonedDateTime.now(ZoneId.of("UTC-4")).minusDays(2).toLocalDate()
    } else {
        val yesterdayDate = currentDate
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData(currentDateofWashington.toString()).observe(viewLifecycleOwner, //сюда передать дату фото
            Observer<PictureOfTheDayData> { renderData(it) })

        //ищем по нажатию в википедии, сделаем три языка. Как бы это автоматизировать на все...
        binding.inputLayout.setStartIconOnClickListener {
            val myTextLength = binding.inputEditText?.text?.length
            if (myTextLength != null && myTextLength > 20) { //не знаю, как узнать, переполнен ли счетчик, напишу условия сама
                binding.inputLayout.isStartIconCheckable = false //И что?! Ничего не происходит ...может, сработало бы visability gone
                    Toast.makeText(context, getString(R.string.text_is_too_long), Toast.LENGTH_SHORT).show()
            } else {
               startActivity(Intent(Intent.ACTION_VIEW).apply {
                   val lang = Locale.getDefault().getLanguage() //получаем язык системы
                   when (lang){
                       "ru" -> {
                           data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                       }
                       "en" -> {
                           data = Uri.parse("https://com.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                       }
                       "es" -> {
                           data = Uri.parse("https://es.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                       }
                       else -> {
                           data = Uri.parse("https://com.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                       }
                   }
               })
            }
        }

        binding.imageView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(myLink)
            })
        }

        setBottomSheetBehavior(bottom_sheet_container) //это когда мы научимся анимации
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

           setBottomAppBar(view)

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
//                Toast.makeText(context, "Выбран ${it.text}",
//                    Toast.LENGTH_SHORT).show()
            }
            when {
                binding.preyesterdayFoto.isChecked -> {
                    viewModel.getData(preYesterdayDate.toString())
                }
                binding.yesterdayFoto.isChecked -> {
                        viewModel.getData(yesterdayDate.toString())
                }
                binding.todayFoto.isChecked -> {
                        viewModel.getData(currentDateofWashington.toString())
                }
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData) = with(binding) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                myLink = url!!
                    //myFoto = serverResponseData.date.toString()
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, getString(R.string.link_is_empty), Toast.LENGTH_SHORT).show()
                } else {
                    //showSuccess()
                    binding.imageView.load(url) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_lovely -> Toast.makeText(context, getString(R.string.lovely),
                Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,
                SettingsFragment()
            )?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
//            if (isMain) {
//                isMain = false
//                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_border_24))
//                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
//            } else {
//                isMain = true
//                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                fab.setImageDrawable(ContextCompat.getDrawable(context,
//                    R.drawable.ic_baseline_star_24))
//                bottom_app_bar.replaceMenu(R.menu.bottom_bar_menu)
//            }
            activity?.let { startActivity(Intent(it, ApiActivity::class.java)) }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        private var isMain = true
        private const val THEME_BASE = 0
        private const val THEME_BLACK_AND_WHITE = 1
    }
}