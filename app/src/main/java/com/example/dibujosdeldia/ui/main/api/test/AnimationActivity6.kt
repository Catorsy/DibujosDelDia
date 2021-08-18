package com.example.dibujosdeldia.ui.main.api.test

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.activity_animations_fab.*

class AnimationActivity6 : AppCompatActivity() {

    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_fab)
        setFAB()
    }

    //проверяем, была ли нажата фаб или нет
    private fun setFAB() {
        setInitialState()
        fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    //задает начальные параметры вью
    //без затенени, опции прозрачны и некликабельны
    private fun setInitialState() {
        transparent_background.apply {
            alpha = 0f
        }
        option_two_container.apply {
            alpha = 0f
            isClickable = false
        }
        option_one_container.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        ObjectAnimator.ofFloat(plus_imageview, "rotation", 0f, 225f).start()
        ObjectAnimator.ofFloat(option_two_container, "translationY",
            -130f).start()
        ObjectAnimator.ofFloat(option_one_container, "translationY",
            -250f).start()
        option_two_container.animate()
            .alpha(1f) //прозрачность
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    option_two_container.isClickable = true
                    option_two_container.setOnClickListener {
                        Toast.makeText(this@AnimationActivity6, "Option 2",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            })

        option_one_container.animate()
            .alpha(1f)
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    option_one_container.isClickable = true
                    option_one_container.setOnClickListener {
                        Toast.makeText(this@AnimationActivity6, "Option 1",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            })

        transparent_background.animate()
            .alpha(0.9f)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparent_background.isClickable = true
                }
            })
    }

    private fun collapseFab() {
        isExpanded = false
        ObjectAnimator.ofFloat(plus_imageview, "rotation", 0f, -180f).start()
        ObjectAnimator.ofFloat(option_two_container, "translationY", 0f).start()
        ObjectAnimator.ofFloat(option_one_container, "translationY", 0f).start()
        option_two_container.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    option_two_container.isClickable = false
                    option_one_container.setOnClickListener(null)
                }
            })
        option_one_container.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    option_one_container.isClickable = false
                }
            })
        transparent_background.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparent_background.isClickable = false
                }
            })
    }
}
