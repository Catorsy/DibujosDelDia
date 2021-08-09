package com.example.dibujosdeldia.ui.main.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.FragmentEarthBinding

class EarthFragment : Fragment() {
    private lateinit var binding : FragmentEarthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }
}
