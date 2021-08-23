package com.example.dibujosdeldia.ui.main.api.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.item_coord.view.*
import kotlinx.android.synthetic.main.item_mars.view.*

class RecyclerActivityAdapter(
    val onListItemClickListener: OnListItemClickListener,
    var data: MutableList<Pair<DataNotesEarth, Boolean>>,
    private val dragListener: OnStartDragListener
        ) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter
{

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<DataNotesEarth, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.coord_text.text = data.first.coordDescription
                itemView.place_text.text = data.first.placeText
                itemView.compass_picture.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first) }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#BFB2B9CA"))
        }

    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<DataNotesEarth, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.mars_title.text = data.first.marsText
                itemView.mars_note_edit_text.hint = data.first.noteText
                itemView.marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first) }
                itemView.addItemImageView.setOnClickListener { addItem() }
                itemView.removeItemImageView.setOnClickListener { removeItem() }
                itemView.moveItemDown.setOnClickListener { moveDown() }
                itemView.moveItemUp.setOnClickListener { moveUp() }
                itemView.mars_note_edit_text.visibility =
                    if (data.second) View.VISIBLE else View.GONE
                itemView.mars_title.setOnClickListener { toggleText() }
                itemView.marsImageView.setOnClickListener { toggleText() }
                itemView.dragHandleImageView.setOnTouchListener { _, event ->
                    if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
            }
        }

        //если потребуются какие эффекты переноса-выделения. Сделать в лайауте фон белый/серый.
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#BFB2B9CA"))
        }

//        override fun onItemClear() {
//            itemView.setBackgroundColor(Color.WHITE)
//        }

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

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<DataNotesEarth, Boolean>) {
            itemView.setOnClickListener {onListItemClickListener.onItemClick(data.first) }
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
            data[position].first.placeText.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            if (toPosition != 0) {
                data.add(toPosition, this)
            } else {
                data.add(fromPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
            }
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(DataNotesEarth(
        null, null, "Марс", "Ваша новая заметка"), false)

    interface OnStartDragListener{
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }
}