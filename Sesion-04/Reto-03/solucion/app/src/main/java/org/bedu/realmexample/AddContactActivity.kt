package org.bedu.realmexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*
import android.app.Activity
import android.content.Intent

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        buttonAdd.setOnClickListener{
            val name = editName.text.toString()
            val job = editJob.text.toString()
            val company = editCompany.text.toString()
            val city = editCity.text.toString()

            val returnIntent = Intent()
            returnIntent.putExtra("name",name)
            returnIntent.putExtra("job",job)
            returnIntent.putExtra("company",company)
            returnIntent.putExtra("city",city)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }


}