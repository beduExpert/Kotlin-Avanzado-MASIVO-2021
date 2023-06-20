import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.bedu.mvp.R
import org.bedu.mvp.addcontact.Contact
import org.bedu.mvp.databinding.ActivityMainBinding
import org.bedu.mvp.main.MainPresenter
import org.bedu.mvp.main.RecyclerAdapter

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var mAdapter : RecyclerAdapter
    private val presenter = MainPresenter(this)
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter.setupContacts()

        binding.fab.setOnClickListener { view ->
            presenter.goToAddContact(this)
        }
    }

    override fun setupList(contactos: MutableList<Contact>) {
        binding.content.recyclerContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)

            //seteando el Adapter
            mAdapter = RecyclerAdapter(this@MainActivity, contactos)
            adapter = mAdapter
        }

    }

    override fun updateList(contacts: MutableList<Contact>) {
        mAdapter = RecyclerAdapter(this, contacts)
        binding.content.recyclerContacts.adapter = mAdapter
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