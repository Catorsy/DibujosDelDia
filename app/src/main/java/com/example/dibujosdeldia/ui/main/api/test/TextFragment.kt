package com.example.dibujosdeldia.ui.main.api.test

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.databinding.TextFragmentBinding


class TextFragment : Fragment() {

    private lateinit var binding : TextFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = TextFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.textTestTextView.typeface = Typeface.createFromAsset(activity?.assets, "Aerodynamic_aGag.otf")
    }
}