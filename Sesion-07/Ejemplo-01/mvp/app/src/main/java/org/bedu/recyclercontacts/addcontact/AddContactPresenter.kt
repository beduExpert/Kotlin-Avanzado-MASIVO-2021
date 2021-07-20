package org.bedu.recyclercontacts.addcontact

import android.app.Activity
import android.content.Intent


class AddContactPresenter( view: View) { //view es la vista a la que estará atado (AddContactPresenter)

    //el Model al que estamos atados
    var contact=Contact()

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el nombre
    fun updateName(name: String){
        contact.name = name
    }

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el teléfono
    fun updatePhone(phone: String){
        contact.phone = phone
    }

    //Acción a tomar cuando se presiona el botón addContact
    fun addContact(activity:Activity){

        val returnIntent = Intent()
        returnIntent.putExtra("new_contact", contact)
        activity.setResult(Activity.RESULT_OK, returnIntent)
        activity.finish()
    }

    //interfaz que define nuestra vista
    interface View{
        fun addContact()
    }



}