package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import org.bedu.transitions.databinding.ActivityTransitionSetBinding

class TransitionSetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionSetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //sceneOne = Scene.getSceneForLayout(binding.container, R.layout.scene_one, this)
        //sceneThree = Scene.getSceneForLayout(binding.container, R.layout.scene_three, this)
        //currentScene = sceneOne
    }
}