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
    var data: MutableList<DataNotesEarth>
        ) : RecyclerView.Adapter<BaseViewHolder>()
{

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: DataNotesEarth) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.coord_text.text = data.coordDescription
                itemView.place_text.text = data.placeText
                itemView.compass_picture.setOnClickListener {
                    onListItemClickListener.onItemClick(data) }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: DataNotesEarth) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.mars_title.text = data.marsText
                itemView.mars_note_edit_text.hint = data.noteText
                itemView.marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data) }
                itemView.addItemImageView.setOnClickListener { addItem() }
                itemView.removeItemImageView.setOnClickListener { removeItem() }
                itemView.moveItemDown.setOnClickListener { moveDown() }
                itemView.moveItemUp.setOnClickListener { moveUp() }
            }
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition -> //it > 1 чтобы не трогать хэдер
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

            private fun moveDown() {
                layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                    data.removeAt(currentPosition).apply {
                        data.add(currentPosition + 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition + 1)
                }
            }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: DataNotesEarth) {
            itemView.setOnClickListener {onListItemClickListener.onItemClick(data) }
        }
    }

    //получаем не только parent, но и viewType!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> EarthViewHolder(inflater.inflate(R.layout.item_coord, parent, false) as View)
            TYPE_MARS -> MarsViewHolder(inflater.inflate(R.layout.item_mars, parent, false) as View)
            else -> HeaderViewHolder(inflater.inflate(R.layout.notes_title, parent, false) as View)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        when (getItemViewType(position)) {
//            TYPE_EARTH -> {
//                holder as EarthViewHolder
//                holder.bind(data[position])
//            }
//            TYPE_MARS -> {
//                holder as MarsViewHolder
//                holder.bind(data[position])
//            }
//            else -> {
//                holder as HeaderViewHolder
//                holder.bind(data[position])
//            }
//        }
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //получает позицию элемента, для которого сейчас будет создаваться вьюхолдер
    //если не переопределить, будет возвращать ноль
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].placeText.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = DataNotesEarth(null, null, "Марс", "Ваша новая заметка")

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }
}