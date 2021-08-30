package com.example.dibujosdeldia.ui.main.api

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.FragmentCuriosityInfoBinding

//ДЗ делается
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
        textDecor()
        binding.texMarsView.text = spannableMarsDescription
    }

    private fun textDecor() {
        //FHAZ
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.fab_back, typedValue, true)
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(requireContext(),
            typedValue.resourceId)), 539, 540, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 539, 543,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //RHAZ
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(
            requireContext(), R.color.purple_500)), 578, 579, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 578, 582,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //MAST
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(
            requireContext(), R.color.purple_500)), 615, 616, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 615, 619,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //CHEMCAM
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(
            requireContext(), R.color.purple_500)), 664, 671, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 664, 672,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //NAVCAM
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(
            requireContext(), R.color.purple_500)), 730, 735, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 730, 736,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //MAHLI
        spannableMarsDescription.setSpan(BulletSpan(8, ContextCompat.getColor(
            requireContext(), R.color.purple_500)), 765, 766, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 765, 770,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //marcers
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 9, 21,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.BOLD), 840, 849,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableMarsDescription.setSpan(StyleSpan(Typeface.ITALIC), 1082, 1147,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}