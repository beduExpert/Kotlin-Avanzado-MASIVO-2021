package org.bedu.recyclercontacts.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.bedu.recyclercontacts.addcontact.AddContactActivity
import org.bedu.recyclercontacts.addcontact.Contact

class MainPresenter constructor(view:View) {

    //Nuestro Model
    private  var contactos = Contact.initContacts()
    private val view = view

    fun goToAddContact(context: Activity){
        val intent = Intent(context, AddContactActivity::class.java)
        context.startActivityForResult(intent,1)
    }

    fun setupContacts(){
        view.setupList(contactos)
    }


    fun addContact(data: Intent?){
        val contact = data!!.getParcelableExtra<Contact>("new_contact")
        contactos.add(0,contact)
        view.updateList(contactos)

    }

    interface View{
        fun setupList(contactos: MutableList<Contact>)
        fun updateList(contactos:MutableList<Contact>)
    }

}