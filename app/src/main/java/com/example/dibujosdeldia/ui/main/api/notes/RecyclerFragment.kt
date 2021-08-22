package com.example.dibujosdeldia.ui.main.api.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.RecyclerFragmentBinding
import kotlinx.android.synthetic.main.recycler_fragment.*

class RecyclerFragment : Fragment() {

    private lateinit var binding: RecyclerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecyclerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arrayListOf(
            DataNotesEarth("Париж", "48°50′ с.ш. 2°20′ в.д."),
            DataNotesEarth("Вена", "48°13′ с.ш. 16°22′ в.д."),
            DataNotesEarth(null, null, "Марс", "Сегодняшние сутки 3212"),
            DataNotesEarth("Гонконг", "22°25′ с.ш. 114°10′ в.д."),
            DataNotesEarth("Хельсинки", "60°10′ с.ш. 24°56′ в.д."),
            DataNotesEarth(null, null, "Mars", null)
        )

        recyclerView.adapter = RecyclerActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: DataNotesEarth) {
                    Toast.makeText(context, data.placeText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )
    }
}