package com.example.dibujosdeldia.ui.main.api.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.item_coord.view.*
import kotlinx.android.synthetic.main.item_mars.view.*

class RecyclerActivityAdapter(
    val onListItemClickListener: OnListItemClickListener,
    var data: List<DataNotesEarth>
        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataNotesEarth) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.coord_text.text = data.coordDescription
                itemView.place_text.text = data.placeText
                itemView.compass_picture.setOnClickListener {
                    onListItemClickListener.onItemClick(data) }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataNotesEarth) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.mars_title.text = data.marsText
                itemView.mars_note_edit_text.hint = data.noteText
                itemView.marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data) }
            }
        }
    }

    //получаем не только parent, но и viewType!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_EARTH) {
            EarthViewHolder(inflater.inflate(R.layout.item_coord, parent, false) as View
            )
        } else {
            MarsViewHolder(inflater.inflate(R.layout.item_mars, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_EARTH) {
            holder as EarthViewHolder
            holder.bind(data[position])
        } else {
            holder as MarsViewHolder
            holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //получает позицию элемента, для которого сейчас будет создаваться вьюхолдер
    //если не переопределить, будет возвращать ноль
    override fun getItemViewType(position: Int): Int {
        return if (data[position].coordDescription.isNullOrBlank()) TYPE_MARS else
            TYPE_EARTH
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
    }
}