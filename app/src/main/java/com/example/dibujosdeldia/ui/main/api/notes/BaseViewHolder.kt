package com.example.dibujosdeldia.ui.main.api.notes

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {
    abstract fun bind(data: Pair<DataNotesEarth, Boolean>)

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.DKGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }
}