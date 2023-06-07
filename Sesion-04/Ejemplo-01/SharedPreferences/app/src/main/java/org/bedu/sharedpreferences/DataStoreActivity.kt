package org.bedu.sharedpreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.bedu.sharedpreferences.databinding.ActivityDataStoreBinding
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DataStoreActivity : AppCompatActivity() {

    private val STRING_KEY = stringPreferencesKey("string")
    private val NUMBER_KEY = floatPreferencesKey("float")
    private val BOOLEAN_KEY = booleanPreferencesKey("boolean")

    private lateinit var binding: ActivityDataStoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                dataStore.data.collect {preferences ->
                    binding.etString.setText(preferences[STRING_KEY])
                    binding.switch1.isChecked = preferences[BOOLEAN_KEY] ?: false
                    binding.etNumber.setText(preferences[NUMBER_KEY].toString())
                }
            }
        }

        with (binding) {
            button.setOnClickListener {

                //obtenemos los valores de las vistas
                val string = etString.text.toString()
                val number = etNumber.text.toString().toFloat()
                val checked = switch1.isChecked

                //las asignamos a nuestra colección y aplicamos
                lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences[STRING_KEY] = string
                        preferences[NUMBER_KEY] = number
                        preferences[BOOLEAN_KEY] = checked
                    }
                }
            }
        }
    }


}