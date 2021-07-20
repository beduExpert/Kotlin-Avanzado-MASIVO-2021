package org.bedu.transitions

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import org.bedu.transitions.databinding.ActivityActivitiesTransitionBinding

class ActivitiesTransitionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityActivitiesTransitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        title = "Emisor"


        val transitionXml = TransitionInflater
            .from(this).inflateTransition(R.transition.transition_set_activity).apply {
                excludeTarget(window.decorView.findViewById<View>(R.id.action_bar_container), true)
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
            }


        window.exitTransition = transitionXml


        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, TransitionedActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}