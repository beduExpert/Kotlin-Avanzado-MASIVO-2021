
## Patrón de arquitectura: Model-View-Presenter

### OBJETIVO 

- Que el alumno refuerce el tema MVP

#### REQUISITOS 

1. Haber cursado el [Ejemplo 1](../Ejemplo-01)

#### DESARROLLO

Tomamos como base el Proyecto del [Ejemplo 1](../Ejemplo-01).

**Ejercicio:** Reemplazar el código en el MainActivity y separarlo de acuerdo al Patrón MVP (Model View Presenter).

- Recordar que el modelo, la vista y la interacción sobre ellos corre a cargo del presentador.

- Agregamos la función estática de generación de contactos iniciales al Model *Contact*, puesto que el modelo se encarga de la recuperación de datos.

- Para mayor simplicidad, no modificaremos el código dentro de la clase *RecyclerAdapter* (lo correcto sería separarlo también)

```kotlin
...
companion object CREATOR : Parcelable.Creator<Contact> {
       ...

        fun initContacts(): MutableList<Contact>{
            var contacts:MutableList<Contact> = ArrayList()

            contacts.add(
                Contact(
                    "Pablo Perez",
                    "disponible",
                    "5535576823",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Juan Magaña",
                    "on smash",
                    "553552432",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Leticia Pereda",
                    "disponible",
                    "5553454363",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Manuel Lozano",
                    "livin' la vida loca",
                    "9613245432",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Ricardo Belmonte",
                    "disponible",
                    "6332448739",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Rosalina",
                    "lookin' to the stars",
                    "7546492750",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Thalía Fernandez",
                    "no fear",
                    "5587294655",
                    R.drawable.unknown
                )
            )
            contacts.add(
                Contact(
                    "Sebastián Cárdenas",
                    "no molestar",
                    "4475655578",
                    R.drawable.unknown
                )
            )

            return contacts
        }
    }
```


<details>
	<summary>Solucion</summary>
*MainPresenter.kt*

```kotlin
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
```

*MainActivity.kt*
```kotlin

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

```

</details>




