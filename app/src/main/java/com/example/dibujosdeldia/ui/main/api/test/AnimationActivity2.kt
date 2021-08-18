package com.example.dibujosdeldia.ui.main.api.test

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.activity_animation_explode.*

class AnimationActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_explode)
        recycler_view.adapter = Adapter()
    }

    //здесь разлетаются все элементы
    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect) //передаем коорднаты прямоугольнику
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect //возвращает прямоугольник с границами нашего экрана
            }
        }
        explode.duration = 1000
        TransitionManager.beginDelayedTransition(recycler_view, explode)
        recycler_view.adapter = null
    }

    private fun explode2(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true) //исключаем из анимации кликнутое
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView)) //скрываем кнопку
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) { //вешаем листенер на конец анимации
                    transition.removeListener(this)
                    onBackPressed() //и закрываем активити
                }
            })
        TransitionManager.beginDelayedTransition(recycler_view, set)
        recycler_view.adapter = null
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animation_explode_rw_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode2(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}