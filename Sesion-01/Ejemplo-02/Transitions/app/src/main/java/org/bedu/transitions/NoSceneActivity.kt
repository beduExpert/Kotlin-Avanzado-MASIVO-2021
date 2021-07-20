package org.bedu.transitions

import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginEnd
import org.bedu.transitions.databinding.ActivityNoSceneBinding


class NoSceneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNoSceneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.switch1.setOnCheckedChangeListener{ _, isChecked ->

            val transition = Slide(Gravity.END)
            TransitionManager.beginDelayedTransition(view, transition)

            binding.textEmail.visibility = if(isChecked) View.INVISIBLE else View.VISIBLE
        }

        binding.button.setOnClickListener {

            val editText = EditText(this).apply {
                id = View.generateViewId()
                hint = "Teléfono"
                layoutParams = ViewGroup.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            }


            val editTextTransition = Fade(Fade.IN).apply {
                addTarget(editText)
            }

            val buttonTransition = Fade(Fade.OUT).apply {
                addTarget(binding.button)
            }


            val transitionSet = TransitionSet().apply {
                addTransition(editTextTransition)
                addTransition(buttonTransition)
            }

            TransitionManager.beginDelayedTransition(view, transitionSet)


            binding.constraintLayout.addView(editText)

            ConstraintSet().apply {
                clone(binding.constraintLayout)
                connect(
                    editText.id,
                    ConstraintSet.TOP,
                    R.id.switch1,
                    ConstraintSet.BOTTOM,
                    32
                )
                connect(
                    editText.id,
                    ConstraintSet.START,
                    R.id.constraintLayout,
                    ConstraintSet.START,
                    64
                )
                connect(
                    editText.id,
                    ConstraintSet.END,
                    R.id.constraintLayout,
                    ConstraintSet.END,
                    64
                )
                applyTo(binding.constraintLayout)
            }

            binding.button.visibility = View.GONE

        }
    }
}