package org.bedu.animations

import android.animation.*
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity: AppCompatActivity() {

    private lateinit var frameObjAnim: ConstraintLayout
    private lateinit var arwing: ImageView

    private lateinit var btnStart: Button
    private lateinit var btnBarrel: Button
    private lateinit var btnAlpha: Button
    private lateinit var btnTiny: Button
    private lateinit var btnSin: Button
    private lateinit var btnPivot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameObjAnim = findViewById(R.id.frameObjAnim)

        btnStart = findViewById(R.id.btnStart)
        btnBarrel = findViewById(R.id.btnBarrel)
        btnAlpha = findViewById(R.id.btnAlpha)
        btnTiny = findViewById(R.id.btnTiny)
        btnSin = findViewById(R.id.btnSin)
        btnPivot = findViewById(R.id.btnPivot)


        arwing = findViewById(R.id.arwing)


        btnStart.setOnClickListener { moveArwingToStart() }

        btnBarrel.setOnClickListener {
            barrelRoll()
        }
        btnAlpha.setOnClickListener { blinkArwing() }

        btnSin.setOnClickListener { arwingDodging() }

        btnTiny.setOnClickListener { shrinkArwing() }

        btnPivot.setOnClickListener { changePivot() }

    }

    private fun barrelRoll() {
        //el valor de nuestro animator va del 0 a 720, dos veces 360º (dos rotaciones de 360º)
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)

        //en cada update del animador asignamos la rotación requerida
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float //obteniendo el valor actual
            // 2
            arwing.rotationY = value //asignando la posición de rotación
        }



        valueAnimator.interpolator = AccelerateDecelerateInterpolator() //el interpolador es lineal
        valueAnimator.duration = 1000 //la duración es de 1 segundo
        valueAnimator.start() //correr la animaciónn
    }

    private fun arwingDodging() {

        /*ObjectAnimator.ofFloat(arwing, "translationX", 200f).apply {
            duration = 3000
            interpolator = CycleInterpolator(1f)
            start()
        }*/

            AnimatorInflater.loadAnimator(this, R.animator.dodging).apply {
            setTarget(arwing)
            start()
        }
    }


    private fun blinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.invincible).apply {
            setTarget(arwing)
            addListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Toast.makeText(applicationContext, "iniciando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Toast.makeText(applicationContext, "terminando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Toast.makeText(applicationContext, "blinking cancelado", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Toast.makeText(applicationContext, "repitiendo parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

            })
            start()
        }
    }

    private fun shrinkArwing() {
        AnimatorInflater.loadAnimator(this, R.animator.shrink).apply {
            setTarget(arwing)
            start()
        }
    }

    //mover en el eje y a nuestra nave
    private fun moveArwingToStart() {
        arwing.animate().apply {
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
            ObjectAnimator.ofPropertyValuesHolder(arwing, initPivotX, initPivotY, transparent)
        animation1.duration = 500

        val pivotCenterX = arwing.width.toFloat() / 2f
        val pivotCenterY = arwing.height.toFloat() / 2f
        val centerPivotX = PropertyValuesHolder.ofFloat("pivotX", pivotCenterX)
        val centerPivotY = PropertyValuesHolder.ofFloat("pivotY", pivotCenterY)
        val opacy = PropertyValuesHolder.ofFloat("alpha", 1f)

        val animation2 =
            ObjectAnimator.ofPropertyValuesHolder(arwing, centerPivotX, centerPivotY, opacy).apply {
                duration = 500
                startDelay = 4000
            }

        AnimatorSet().apply {
            playSequentially(animation1, animation2)
            start()
        }
    }
}





