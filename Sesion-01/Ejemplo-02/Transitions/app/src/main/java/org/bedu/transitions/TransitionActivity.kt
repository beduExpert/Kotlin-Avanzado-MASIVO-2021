package org.bedu.transitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.animation.AccelerateInterpolator
import org.bedu.transitions.R
import org.bedu.transitions.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity() {

    private lateinit var sceneOne: Scene
    private lateinit var sceneTwo: Scene
    private lateinit var currentScene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sceneOne = Scene.getSceneForLayout(binding.container, R.layout.scene_one, this)
        sceneTwo = Scene.getSceneForLayout(binding.container, R.layout.scene_two, this)
        currentScene = sceneOne

        binding.btnTransition.setOnClickListener {
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.bounds)
            TransitionManager.go(sceneTwo, transition)
        }

        binding.btnTransitionCode.setOnClickListener {
            val transition = ChangeBounds().apply {
                interpolator = AccelerateInterpolator()
                duration = 500
            }
            currentScene = if(currentScene == sceneOne){
                TransitionManager.go(sceneTwo,transition)
                sceneTwo
            } else{
                TransitionManager.go(sceneOne,transition)
                sceneOne
            }
        }
    }
}