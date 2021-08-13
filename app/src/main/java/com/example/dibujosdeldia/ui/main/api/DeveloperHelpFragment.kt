package com.example.dibujosdeldia.ui.main.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.databinding.TestConstFileBinding
import kotlinx.android.synthetic.main.test_const_file.*

class DeveloperHelpFragment : Fragment() {
    private lateinit var binding : TestConstFileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestConstFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if(needHide.visibility == View.GONE){
                needHide.visibility = View.VISIBLE
            }else needHide.visibility = View.GONE
        }
    }
}