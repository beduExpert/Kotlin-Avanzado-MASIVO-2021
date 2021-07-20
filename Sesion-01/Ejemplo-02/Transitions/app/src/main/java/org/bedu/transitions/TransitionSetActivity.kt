package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import org.bedu.transitions.databinding.ActivityTransitionSetBinding

class TransitionSetActivity : AppCompatActivity() {

    private lateinit var sceneOne: Scene
    private lateinit var sceneThree: Scene
    private lateinit var currentScene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionSetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sceneOne = Scene.getSceneForLayout(binding.container, R.layout.scene_one, this)
        sceneThree = Scene.getSceneForLayout(binding.container, R.layout.scene_three, this)
        currentScene = sceneOne

        binding.btnTransitionSet.setOnClickListener {
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.transition_set)
            currentScene = if(currentScene == sceneOne){
                TransitionManager.go(sceneThree,transition)
                sceneThree
            } else{
                TransitionManager.go(sceneOne,transition)
                sceneOne
            }
        }

    }
}