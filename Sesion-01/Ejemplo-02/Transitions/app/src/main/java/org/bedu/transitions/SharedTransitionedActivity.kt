package org.bedu.transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bedu.transitions.databinding.ActivitySharedTransitionedBinding


class SharedTransitionedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedTransitionedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Receptor"
    }
}