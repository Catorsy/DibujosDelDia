package com.example.dibujosdeldia.ui.main.api.test

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.activity_animations_bonus_start.*

class AnimationsActivityBonus : AppCompatActivity() {

    //!!!для контейнера обязательно должен быть прописан айди!
    private var show = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_bonus_start)
        backgroundImage.setOnClickListener { if (show) hideComponents() else
            showComponents() }
    }

    private fun showComponents() {
        show = true
        val constraintSet = ConstraintSet()
        //этот класс позволяет программно создавать constraints для вашего layout
        constraintSet.clone(this, R.layout.activity_animations_bonus_end)
        val transition = ChangeBounds() //изменение границ view
        transition.interpolator = AnticipateOvershootInterpolator(1.0f) //уход за границы
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(constraint_container,
            transition) //и это старт
        constraintSet.applyTo(constraint_container) //старт анимации
    }

    private fun hideComponents() {
        show = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_start)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        TransitionManager.beginDelayedTransition(constraint_container,
            transition)
        constraintSet.applyTo(constraint_container)
    }
}