package com.example.dibujosdeldia.ui.main.api.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dibujosdeldia.R
import com.example.dibujosdeldia.databinding.RecyclerFragmentBinding
import kotlinx.android.synthetic.main.recycler_fragment.*

class RecyclerFragment : Fragment() {

    private lateinit var binding: RecyclerFragmentBinding
    private lateinit var adapter: RecyclerActivityAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

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
            Pair(DataNotesEarth("Париж", "48°50′ с.ш. 2°20′ в.д."), false),
            Pair(DataNotesEarth("Вена", "48°13′ с.ш. 16°22′ в.д."), false),
            Pair(DataNotesEarth(null, null, "Марс", "Сегодняшние сутки 3212"), false),
            Pair(DataNotesEarth("Гонконг", "22°25′ с.ш. 114°10′ в.д."), false),
            Pair(DataNotesEarth("Хельсинки", "60°10′ с.ш. 24°56′ в.д."), false),
            Pair(DataNotesEarth(null, null, "Mars", null), false),
        )
        data.add(0, Pair(DataNotesEarth(null), false))

        adapter = RecyclerActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: DataNotesEarth) {
                    Toast.makeText(context, data.placeText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : RecyclerActivityAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder) //startDrag слушатель тяги за рукоятку
                }
            }
        )
        recyclerView.adapter = adapter

        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }

            //к рукоятке
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(recyclerView)
    }
}