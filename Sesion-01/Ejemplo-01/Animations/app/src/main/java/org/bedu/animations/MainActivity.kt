package org.bedu.animations

import android.animation.*
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.bedu.animations.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener { moveArwingToStart() }
            btnBarrel.setOnClickListener {
                barrelRoll()
            }
            btnAlpha.setOnClickListener { blinkArwing() }
            btnSin.setOnClickListener { arwingDodging() }
            btnTiny.setOnClickListener { shrinkArwing() }
            btnPivot.setOnClickListener { changePivot() }
        }
    }

    private fun barrelRoll() {
        //el valor de nuestro animator va del 0 a 720, dos veces 360º (dos rotaciones de 360º)
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)

        valueAnimator.run {
            // En cada update del animador asignamos la rotación requerida
            addUpdateListener {
                val value = it.animatedValue as Float
                 binding.arwing.rotationY = value
            }

            interpolator = AccelerateDecelerateInterpolator() // El interpolador es lineal
            duration = 1_000
            start()
        }
    }

    private fun arwingDodging() {

        /*ObjectAnimator.ofFloat(arwing, "translationX", 200f).apply {
            duration = 3000
            interpolator = CycleInterpolator(1f)
            start()
        }*/
        AnimatorInflater.loadAnimator(this, R.animator.dodging).run {
            setTarget(binding.arwing)
            start()
        }
    }


    private fun blinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.invincible).apply {
            setTarget(binding.arwing)
            addListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    Toast.makeText(this@MainActivity, "iniciando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationEnd(animation: Animator) {
                    Toast.makeText(this@MainActivity, "terminando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationCancel(animation: Animator) {
                    Toast.makeText(this@MainActivity, "blinking cancelado", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationRepeat(animation: Animator) {
                    Toast.makeText(this@MainActivity, "repitiendo parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            start()
        }
    }

    private fun shrinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.shrink).apply {
            setTarget(binding.arwing)
            start()
        }
    }

    //mover en el eje y a nuestra nave
    private fun moveArwingToStart() {
        binding.arwing.animate().apply {
            translationX(0f)
            translationY(0f)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun changePivot() {
        val initPivotX = PropertyValuesHolder.ofFloat("pivotX", 0f)
        val initPivotY = PropertyValuesHolder.ofFloat("pivotY", 0f)
        val transparent = PropertyValuesHolder.ofFloat("alpha", 0.6f)
        val animation1 =
            ObjectAnimator.ofPropertyValuesHolder(binding.arwing, initPivotX, initPivotY, transparent)
        animation1.duration = 500

        val pivotCenterX = binding.arwing.width.toFloat() / 2f
        val pivotCenterY = binding.arwing.height.toFloat() / 2f
        val centerPivotX = PropertyValuesHolder.ofFloat("pivotX", pivotCenterX)
        val centerPivotY = PropertyValuesHolder.ofFloat("pivotY", pivotCenterY)
        val opacy = PropertyValuesHolder.ofFloat("alpha", 1f)

        val animation2 =
            ObjectAnimator.ofPropertyValuesHolder(binding.arwing, centerPivotX, centerPivotY, opacy)
                .apply {
                    duration = 500
                    startDelay = 4000
                }

        AnimatorSet().apply {
            playSequentially(animation1, animation2)
            start()
        }
    }
}





