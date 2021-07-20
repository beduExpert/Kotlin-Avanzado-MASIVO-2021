package org.bedu.transitions

import android.content.Intent
import android.os.Bundle
import androidx.core.util.Pair
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

        
        binding.btnActivity2.setOnClickListener {

            val intent = Intent(this, SharedTransitionedActivity::class.java)


            val headerTransitionName = ViewCompat.getTransitionName(binding.imgConcert)?: " "
            val titleTransitionName = ViewCompat.getTransitionName(binding.titleConcert)?: " "


            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair.create(binding.imgConcert,headerTransitionName),
                Pair.create(binding.titleConcert,titleTransitionName),

            )

            startActivity(intent, options?.toBundle())
        }
    }
}
