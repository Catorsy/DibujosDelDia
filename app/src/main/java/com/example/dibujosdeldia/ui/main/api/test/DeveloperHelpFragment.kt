package com.example.dibujosdeldia.ui.main.api.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.R
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

        binding.textView.setOnClickListener {
            activity?.let{
                startActivity(Intent(it, AnimationsActivity::class.java))
            }
        }

        binding.textView2.setOnClickListener {
            activity?.let{
                startActivity(Intent(it, AnimationActivity2::class.java))
            }
        }

        binding.textView3.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationActivity3::class.java))
            }
        }

        binding.textView6.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationActivity4::class.java))
            }
        }

        binding.textView7.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationsActivity5::class.java))
            }
        }

        binding.textView8.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationActivity6::class.java))
            }
        }

        binding.textView9.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationActivity7::class.java))
            }
        }

        binding.textView10.setOnClickListener {
            activity?.let {
                startActivity(Intent(it, AnimationsActivityBonus::class.java))
            }
        }
    }
}