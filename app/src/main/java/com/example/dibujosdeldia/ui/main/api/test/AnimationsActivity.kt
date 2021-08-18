package com.example.dibujosdeldia.ui.main.api.test

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.activity_animations.*
import kotlinx.android.synthetic.main.test_const_file.button

class AnimationsActivity : AppCompatActivity() {
    private var textIsVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations)
        button.setOnClickListener {
            TransitionManager.beginDelayedTransition(transitions_container, Slide(Gravity.TOP))
            //AutoTransition() по умолчанию, если не даем второго аргумента. Это все анимации снизу:
                // ChangeBounds() двигает кнопку
            //Fade() плавное затухание и воявление текста
                //и FadeOut()

            //Slide(Gravity.END) элемент выезжает из-за границы экрана
            textIsVisible = !textIsVisible
            text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            text2.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }
    }
}