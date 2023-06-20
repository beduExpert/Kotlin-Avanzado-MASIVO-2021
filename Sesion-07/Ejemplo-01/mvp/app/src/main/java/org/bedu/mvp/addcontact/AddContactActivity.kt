package org.bedu.mvp.addcontact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import org.bedu.mvp.databinding.ActivityAddContactBinding


class AddContactActivity : AppCompatActivity(),AddContactPresenter.View {

    //nueva instancia de nuestro presentador
    private val presenter = AddContactPresenter(this)

    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonAdd.setOnClickListener{
                addContact()
            }

            //Cuando el texto cambia (onTextChanged), el presenter hace una actualización de nuestro nombre
            editName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    presenter.updateName(s.toString())
                }
            })

            //Cuando el texto cambia (onTextChanged), el presenter hace una actualización de nuestro teléfono
            editPhone.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    presenter.updatePhone(s.toString())
                }
            })
        }
    }

    //implementación de la interfaz definida en presenter, en este caso sólo llama a la función del presenter
    //pero aquí podría actualizarse algún estado de un elemento de la Vista
    override fun addContact() {
        presenter.addContact(this)
    }

}