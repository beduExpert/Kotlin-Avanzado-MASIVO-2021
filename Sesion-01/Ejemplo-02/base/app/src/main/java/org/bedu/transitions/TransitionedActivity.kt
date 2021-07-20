package org.bedu.transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bedu.transitions.databinding.ActivityTransitionedBinding


class TransitionedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTransitionedBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}