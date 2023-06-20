package org.bedu.mvp.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager


import android.app.Activity
import android.widget.Toast
import org.bedu.mvp.R
import org.bedu.mvp.addcontact.AddContactActivity
import org.bedu.mvp.addcontact.Contact
import org.bedu.mvp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter : RecyclerAdapter
    private lateinit var contacts : MutableList<Contact>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setUpRecyclerView()

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent,1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                val contact = data?.getParcelableExtra<Contact>("new_contact")
                contacts.add(0,contact!!)
                mAdapter.notifyItemInserted(0)
                Toast.makeText(this,"Contacto agregado!",Toast.LENGTH_SHORT).show()


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private fun setUpRecyclerView(){
        binding.content.recyclerContacts.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            contacts = getContacts()
            mAdapter = RecyclerAdapter(this@MainActivity, contacts)
            adapter = mAdapter
        }

    }

    //generamos datos dummy con este método
    private fun getContacts() = mutableListOf(
            Contact(
                "Pablo Perez",
                "disponible",
                "5535576823",
                R.drawable.unknown
            ),
            Contact(
                "Juan Magaña",
                "on smash",
                "553552432",
                R.drawable.unknown
            ),
            Contact(
                "Leticia Pereda",
                "disponible",
                "5553454363",
                R.drawable.unknown
            ),
            Contact(
                "Manuel Lozano",
                "livin' la vida loca",
                "9613245432",
                R.drawable.unknown
            ),
            Contact(
                "Ricardo Belmonte",
                "disponible",
                "6332448739",
                R.drawable.unknown
            ),
            Contact(
                "Rosalina",
                "lookin' to the stars",
                "7546492750",
                R.drawable.unknown
            ),
            Contact(
                "Thalía Fernandez",
                "no fear",
                "5587294655",
                R.drawable.unknown
            ),
            Contact(
                "Sebastián Cárdenas",
                "no molestar",
                "4475655578",
                R.drawable.unknown
            )
        )

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}