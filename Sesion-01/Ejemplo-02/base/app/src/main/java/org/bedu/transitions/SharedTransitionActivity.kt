package org.bedu.transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bedu.transitions.databinding.ActivitySharedTransitionBinding


class SharedTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedTransitionBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
