package org.bedu.mvp.main

import android.app.Activity
import android.content.Intent
import org.bedu.mvp.addcontact.AddContactActivity
import org.bedu.mvp.addcontact.Contact

class MainPresenter(view:View) {

    //Nuestro Model
    private  var contacts = Contact.initContacts()
    private val view = view

    fun goToAddContact(context: Activity){
        val intent = Intent(context, AddContactActivity::class.java)
        context.startActivityForResult(intent,1)
    }

    fun setupContacts(){
        view.setupList(contacts)
    }


    fun addContact(data: Intent?){
        val contact = data!!.getParcelableExtra<Contact>("new_contact")
        contacts.add(0,contact!!)
        view.updateList(contacts)

    }

    interface View{
        fun setupList(contacts: MutableList<Contact>)
        fun updateList(contacts:MutableList<Contact>)
    }

}