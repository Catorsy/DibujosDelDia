package com.example.dibujosdeldia.ui.main.api.test

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.dibujosdeldia.R
import kotlinx.android.synthetic.main.activity_animations_path_transitions.*

class AnimationActivity4 : AppCompatActivity() {

    private var toRightAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_path_transitions)
        button.setOnClickListener {
            val changeBounds = ChangeBounds() //класс для перемещения по плавным траекториям
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = 500
            TransitionManager.beginDelayedTransition(
                transitions_container,
                changeBounds
            )
            toRightAnimation = !toRightAnimation
            val params = button.layoutParams as FrameLayout.LayoutParams
            params.gravity =
                if (toRightAnimation) Gravity.END or Gravity.BOTTOM else
                    Gravity.START or Gravity.TOP
            button.layoutParams = params
        }
    }
}