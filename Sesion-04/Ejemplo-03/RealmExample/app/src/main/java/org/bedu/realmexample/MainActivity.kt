package org.bedu.realmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter : RecyclerAdapter
    private lateinit var contacts: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("resultados","INIT")

        val realm = Realm.getDefaultInstance()
        contacts = realm.where(Contact::class.java).findAll()

        setUpRecyclerView()

        Log.d("resultados","Results: ")
        Log.d("resultados","$contacts")
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        recyclerContacts.setHasFixedSize(true)
        recyclerContacts.layoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerAdapter( this,contacts)
        recyclerContacts.adapter = mAdapter
    }
}
