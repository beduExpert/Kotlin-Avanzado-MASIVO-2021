[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Ejemplo 1`

## Ejemplo 1: Shared Preferences

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Aprender el funcionamiento de SharedPreferences
- Utilizarlo para guardar/cargar información

### 2. Requisitos :clipboard:

1. Haber leído previamente el Prework
2. Tomar la clase de de esta sesión

#### 

### 3. Desarrollo :computer:

Vamos a construir una aplicación sencilla pero capaz de almacenar y cargar tres tipos de dato:

- Un booleano
- Un String
- Un Float

Para ello utilizaremos un EditText de String, un EditText numérico y un Switch para el booleano

1. Abrir un nuevo proyecto con Activity en blanco
2. Editar el layout de main_activity, no importa el contenedor así que puede ser un LinearLayout, lo importante es elegir los EditText

```xml
android:inputType="textPersonName"
```

Esta propiedad es la que define qué tipo de input es, si se requiere un numérico, reemplazar por "number"

el layout que elegimos es el siguiente:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Guardar"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/guideline"
        app:layout_constraintVertical_bias="0.19999999" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_begin="440dp" />

    <EditText
        android:id="@+id/etNumber"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="number"
        app:layout_constraintBottom_toTopOf="@+id/etString"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Switch
        android:id="@+id/switch1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Switch"
        app:layout_constraintBottom_toTopOf="@+id/etNumber"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/etString"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="textPersonName"
        android:text="Name"
        app:layout_constraintBottom_toTopOf="@+id/guideline"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

1. En el MainActivity, declaramos nuestras constantes: PREFS_NAME es el nombre de nuestra colección de preferencias, mientras que los otros tres son los keys en el key-pair

```kotlin
private val PREFS_NAME = "org.bedu.sharedpreferences"
    private val STRING_KEY = "string_key"
    private val NUMBER_KEY = "number_key"
    private val BOOLEAN_KEY = "boolean_key"
```

1. Declaramos también nuestra variable preferences, sin asignarle valor aún:

```kotlin
private lateinit var preferences: SharedPreferences
```

1. declaramos sharedPreferences

```kotlin
preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //Modo privado
```

1. para guardar los valores en nuestras vistas cada que abrimos la app, obtenemos los valores de SharedPreferences y los atamos a sus vistas

```kotlin
fun setValues(){
				val string = preferences.getString(STRING_KEY,"")
        val boolean = preferences.getBoolean(BOOLEAN_KEY, false)
        val number = preferences.getFloat(NUMBER_KEY,0f)

        with (binding) {
            //los atamos a sus vistas
            etString.setText(string)
            switch1.isChecked = boolean
            etNumber.setText(number.toString())
        }
    }
```

y lo ponemos después de crear sharedPreferences en onCreate() :

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        ...
        setValues()
}
```

1. declaramos el listener del botón guardar,

```kotlin
with (binding) {
          button.setOnClickListener {
                  // obtenemos los valores de las vistas
                  val string = etString.text.toString()
                  val number = etNumber.text.toString().toFloat()
                  val checked = switch1.isChecked

                    // las asignamos a nuestra colección y aplicamos
                  preferences.edit()
                      .putString(STRING_KEY, string)
                        .putBoolean(BOOLEAN_KEY,checked)
                        .putFloat(NUMBER_KEY,number)
                        .apply()
                }
        }
```

Corremos el proyecto, debemos tener algo similar a esto:

<img src="images/01.png" width="33%">



#### Usando DataStore

Para hacer una breve diferencia entre SharedPreferences, y la nueva versión de persistencia que nos proporciona jetpack, haremos el mismo ejercicio en un nuevo Activity. Para esto requeriremos seguir los siguientes pasos:

1. Agregar estas dependencias  (DataStore y lifecycle).

   ```groovy
   implementation "androidx.datastore:datastore-preferences:1.0.0"
   
   implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
   ```

2. Crear una nueva DataStoreActivity. Copiaremos el layout de MainActivity.

3. En MainActivity, setear un long click listener para su botón que nos envíe a nuestra siguiente pantalla:

   ```kotlin
   setOnLongClickListener {
       val intent = Intent(this@MainActivity, DataStoreActivity::class.java)
       this@MainActivity.startActivity(intent)
       true
   }
   ```

4. Creamos nuestra instancia de DataStore afuera de nuestra clase DataStoreActivity:

   ```kotlin
   val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
   ```

5. Dentro de la clase, seteamos las llaves para nuestro DataStore:

   ```kotlin
   private val STRING_KEY = stringPreferencesKey("string")
   private val NUMBER_KEY = floatPreferencesKey("float")
   private val BOOLEAN_KEY = booleanPreferencesKey("boolean")
   ```

6. Nos suscribimos a los eventos arrojados por nuestro DataStore, para ponerlos en la UI.

   ```kotlin
   lifecycleScope.launch{
       repeatOnLifecycle(Lifecycle.State.CREATED) {
           dataStore.data.collect {preferences ->
               binding.etString.setText(preferences[STRING_KEY])
               binding.switch1.isChecked = preferences[BOOLEAN_KEY] ?: false
               binding.etNumber.setText(preferences[NUMBER_KEY].toString())
           }
       }
   }
   ```

7. Finalmente, creamos nuestro código para editar los valores de DataStore cuando el botón de update es pulsado:

   ```kotlin
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
   ```

La funcionalidad debería ser idéntica a la de SharedPreferences.

[`Anterior`](../Readme.md) | [`Siguiente`](../Reto-01)      

</div>

