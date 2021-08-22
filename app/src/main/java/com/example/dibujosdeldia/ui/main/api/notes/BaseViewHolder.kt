package com.example.dibujosdeldia.ui.main.api.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: DataNotesEarth)
}