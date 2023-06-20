package org.bedu.mvp.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.bedu.mvp.addcontact.Contact
import org.bedu.mvp.R
import org.bedu.mvp.databinding.ItemContactBinding

class RecyclerAdapter(
    private var context:Context,
    private var contacts: MutableList<Contact>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contacts.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_contact,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemContactBinding.bind(view)

        fun bind(contact: Contact, context: Context){
            with (binding) {
                tvNombre.text = contact.name
                tvStatus.text = contact.status
                tvPhone.text = contact.phone
                userImage.setImageResource(contact.idImage)
            }

            //Gestionando los eventos e interacciones con la vista
            itemView.setOnClickListener{
                Toast.makeText(context, "Llamando a ${contact.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}