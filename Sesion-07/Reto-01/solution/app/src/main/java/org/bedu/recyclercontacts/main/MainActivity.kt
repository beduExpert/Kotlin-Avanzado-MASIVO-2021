package org.bedu.recyclercontacts.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.app.Activity
import android.widget.Toast
import org.bedu.recyclercontacts.addcontact.AddContactActivity
import org.bedu.recyclercontacts.addcontact.Contact
import org.bedu.recyclercontacts.R


class MainActivity : AppCompatActivity(),MainPresenter.View {

    private lateinit var mAdapter : RecyclerAdapter
    private val presenter = MainPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       presenter.setupContacts()

        fab.setOnClickListener { view ->
            presenter.goToAddContact(this)
        }
    }

    override fun setupList(contactos: MutableList<Contact>) {
        recyclerContacts.setHasFixedSize(true)
        recyclerContacts.layoutManager = LinearLayoutManager(this)

        //seteando el Adapter
        mAdapter = RecyclerAdapter(this, contactos)
        recyclerContacts.adapter = mAdapter
    }

    override fun updateList(contactos: MutableList<Contact>) {
        mAdapter = RecyclerAdapter(this, contactos)
        recyclerContacts.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                presenter.addContact(data)
                Toast.makeText(this,"¡Contacto agregado!",Toast.LENGTH_SHORT).show()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    //Funciones predeterminadas por Android Studio
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
