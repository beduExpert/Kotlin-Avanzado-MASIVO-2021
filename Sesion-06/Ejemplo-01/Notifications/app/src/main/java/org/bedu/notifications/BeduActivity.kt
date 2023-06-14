package org.bedu.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.notifications.databinding.ActivityBeduBinding

class BeduActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBeduBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}