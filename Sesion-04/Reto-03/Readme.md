[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Reto 3`

## Reto 3: Realms

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Agregar y eliminar datos en la base de datos Realm

### 2. Requisitos :clipboard:

* Haber cursado el [Ejemplo 2](../Ejemplo-02)

### 3. Desarrollo :computer:

Utilizaremos el proyecto del [Ejemplo 2](../Ejemplo-02) para seguir añadiéndole características a nuestra aplicación de contactos, en este preciso caso añadiremos:

- Eliminar contactos
- Agregar nuevo contacto

Para ello, necesitamos hacer los siguientes ajustes:

- Agregar un botón de agregar usuario, se sugiere un Floating Action Button, que se debe embeber junto al layout raíz con un CoordinatorLayout como lo siguiente: 

```xml 
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <LinearLayout
    ...
    </LinearLayout>
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="18dp"
        app:srcCompat="@android:drawable/ic_input_add" />
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- El botón de agregar contacto deberá llevar a la pantalla de agregar (podemos usar como base la Activity creada en el [Reto 3](../../Sesion-02/Reto-03) ) para obtener los datos en el MainActivity, usamos un *StartActivityForResult()* y regresamos los valores así: 

```kotlin
val returnIntent = Intent()
            returnIntent.putExtra("name",name)
            returnIntent.putExtra("job",job)
            returnIntent.putExtra("company",company)
            returnIntent.putExtra("city",city)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
```

- Para crear un nuevo contacto en la tabla de Contactos, se crea un nuevo objeto, Como Realm no genera pk's con autoincrement, nosotros lo generamos así: 

```kotlin
 var pk = realm.where(Contact::class.java!!).max("id") ?: 0
 pk = pk.toInt() + 1
```

se setean los campos, similar a cuando se setearon los valores iniciales en ***MainApp***.

```kotlin
  val contact = realm.createObject...
```

no olvidar actualizar el Adapter después de cambiar a la DB:

```kotlin
private fun updateRecycler() {
        contacts = realm.where(Contact::class.java).findAll() //volviendo a obtener los contactos (incluído el agregado)
        mAdapter.notifyDataSetChanged() //notificando al adapter del cambio
    }
```

- Hay dos formas de hacer transacciones con Realm:

```kotlin
realm.beginTransaction()

//Código de la transacción aquí

realm.commitTransaction()
```

y esta:

```kotlin
realm.executeTransaction {

//Código de la transacción aquí

}
```

Usar ambas.

- Para eliminar contactos, creamos un botón para eliminar contacto en la vista del item de contacto.

- Crear la variable en el ViewHolder que representa nuestro botón borrar

- Hacer binding del click listener del botón de eliminación en el método *OnBindViewHolder* de nuestro *RecyclerAdapter*.

```kotlin
  holder.removeBtn.setOnClickListener {
            val realm = Realm.getDefaultInstance()

            
            realm.executeTransaction {
                //Eliminar item de la base de datos y notificar de la eliminación del elemento 
            }
        }
```

La eliminación se hace con:

```kotlin
 objetoAEliminar.deleteFromRealm() 
```

obtener la posición del elemento a eliminar (para la notificación):

```kotlin
notifyItemRemoved(holder.adapterPosition)
```

El ejercicio debe quedar similar a esto:

<img src="images/solution.gif" width="33%"/>



[`Anterior`](../Ejemplo-03) | [`Siguiente`](../Proyecto/Readme.md)      

</div>