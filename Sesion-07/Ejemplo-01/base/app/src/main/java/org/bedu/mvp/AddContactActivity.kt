package org.bedu.mvp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bedu.mvp.databinding.ActivityAddContactBinding


class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonAdd.setOnClickListener{
                val name = editName.text.toString()
                val phone = editPhone.text.toString()
                val status = "disponible"
                val imgProfile = R.drawable.unknown

                val contact = Contact(name,status,phone,imgProfile)

                val returnIntent = Intent()
                returnIntent.putExtra("new_contact", contact)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

}