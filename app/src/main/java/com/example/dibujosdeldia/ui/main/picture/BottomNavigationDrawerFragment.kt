package com.example.dibujosdeldia.ui.main.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dibujosdeldia.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navig_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.bottom_navig_layout, container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_about -> Toast.makeText(context, "Приложение создано в июле 2021го",
                    Toast.LENGTH_SHORT).show()
                R.id.navigation_consejo -> Toast.makeText(context, "Используйте красный фонарик!",
                    Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}