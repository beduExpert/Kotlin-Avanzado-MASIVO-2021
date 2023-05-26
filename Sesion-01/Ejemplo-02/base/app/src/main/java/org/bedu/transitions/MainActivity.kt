package org.bedu.transitions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.bedu.transitions.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.run {
            btnTransitions.setOnClickListener(this@MainActivity)
            btnTransitionSet.setOnClickListener(this@MainActivity)
            btnNoScene.setOnClickListener(this@MainActivity)
            btnActivityTransition.setOnClickListener(this@MainActivity)
            btnSharedElement.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {

        val activity = when(v?.id) {
            R.id.btnTransitions ->  TransitionActivity::class.java
            R.id.btnTransitionSet ->  TransitionSetActivity::class.java
            R.id.btnNoScene ->  NoSceneActivity::class.java
            R.id.btnActivityTransition ->  ActivitiesTransitionActivity::class.java
            R.id.btnSharedElement ->  SharedTransitionActivity::class.java
            else -> return
        }

        val intent = Intent(this, activity)
        startActivity(intent)
    }


}