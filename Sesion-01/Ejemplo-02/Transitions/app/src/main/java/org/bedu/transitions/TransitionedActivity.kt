package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import org.bedu.transitions.databinding.ActivitySharedTransitionedBinding

class TransitionedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedTransitionedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Receptor"

        //definiendo el tipo de transición
        val transition = Slide(Gravity.TOP).apply {
            duration = 500
            excludeTarget(window.decorView.findViewById<View>(R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }




        window.enterTransition = transition
    }
}