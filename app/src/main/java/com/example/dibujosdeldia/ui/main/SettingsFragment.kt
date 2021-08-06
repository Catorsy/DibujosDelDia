package com.example.dibujosdeldia.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.databinding.SettingsFragmentBinding

import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment() {
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        choose_theme_default.setOnClickListener {
            saveMyThemeToDisk(ThemeHolder.THEME_DEFAULT)
            requireActivity().recreate()
        }

        choose_theme_black_and_white.setOnClickListener {
            saveMyThemeToDisk(ThemeHolder.THEME_BLACK_AND_WHITE)
            requireActivity().recreate()
        }

        choose_theme_bright.setOnClickListener {
            saveMyThemeToDisk(ThemeHolder.THEME_BRIGHT)
            requireActivity().recreate()
        }
    }

    private fun saveMyThemeToDisk(myTheme: Int) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(ThemeHolder.THEME_KEY, myTheme)
                apply()
            }
        }
    }
}

object ThemeHolder {
    const val THEME_KEY = "MY_THEME"
    const val THEME_DEFAULT = 0
    const val THEME_BLACK_AND_WHITE = 1
    const val THEME_BRIGHT = 2
    var myTheme = 0
}