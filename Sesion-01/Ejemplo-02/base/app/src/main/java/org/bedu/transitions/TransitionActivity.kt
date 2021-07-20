package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.transitions.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}