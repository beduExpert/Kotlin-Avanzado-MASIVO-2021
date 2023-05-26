package org.bedu.transitions

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import org.bedu.transitions.databinding.ActivitySharedTransitionBinding

class SharedTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySharedTransitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "Emisor"

        // Definimos el tipo de transición del elemento compartido en la función startActivity
        binding.btnActivity2.setOnClickListener {

            val intent = Intent(this, SharedTransitionedActivity::class.java)

            // se obtiene el nombre de la transción para identificar nuestros diseños, crear las escenas
            val options = ViewCompat.getTransitionName(binding.imgConcert)?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, binding.imgConcert, it
                )
            }
            startActivity(intent, options?.toBundle())
        }
    }
}
