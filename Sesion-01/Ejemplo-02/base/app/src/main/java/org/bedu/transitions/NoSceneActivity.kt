package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.transitions.databinding.ActivityNoSceneBinding

class NoSceneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNoSceneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }



}