package com.example.dibujosdeldia.ui.main.api

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.databinding.FragmentCuriosityInfoBinding

class CuriosityInfoFragment : Fragment() {
    private lateinit var binding: FragmentCuriosityInfoBinding
    private val myLink = "https://mars.nasa.gov/msl/mission/where-is-the-rover/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuriosityInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToCuriosity.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(myLink)
            })
        }
    }


}