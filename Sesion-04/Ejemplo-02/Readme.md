[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Ejemplo 2`

## Ejemplo 2: Room

<div style="text-align: justify;">
### 1. Objetivos :dart:

* Almacenar datos en un formato de base de datos.
* Proveer accesos a los datos a través de clases, reduciendo el uso de queries a llamadas a métodos.
* Hacer uso del motor SQLite a través de una capa de abstracción

### 2. Requisitos :clipboard:

1. Tener conocimiento básico de SQL

### 3. Desarrollo :computer:

Ciertas aplicaciones requieren almacenar una candtidad considerable de datos y algunos métodos cono __Preferences__ o Almacenamiento de archivos no satisfacen las necesidades , puesto a que no nos proveen una gestión  y estructuramiento de datos adecuando, es por eso que requerimos la implementación de una base de datos en nuestro proyecto.

El Framework de Android permite la implementación de __SQLite__ para su uso en la aplicación, y es ventajoso ya que es un motor de base de datos  relacional que se gestiona por medio de lenguaje __SQL__ y debido a su esquema, es mucho menos pesado que MySql, SQL server, etc. 

Podemos hacer la implementación de SQL de dos formas:

* Mediante el uso directo del paquete ___android.database.sqlite___.
* Utilizando la librería de persistencia ___Room___.

La primera opción implica crear la base de datos desde cero, eso requiere gestionar cada cambio de forma manual al editar la estructura de una tabla o crear una nueva que afecte a otra, implementando toas las queries necesarias (creación de tablas y base de datos, queries de nuestro CRUD, convertir los datos recuperados a objetos), entre otras desventajas como el costo de tiempo que implica aprender a utilizar el motor. 

La segunda opción es usar la librería ___Room___, que nos provee de una capa de abstracción que permite automatizar varios procesos de creación de nuestro sistema. Algunas de las ventajas de utilizar esta librería son:

* Obtener datos
* Evitar el código repetitivo a través de clases y anotaciones
* Manipular la información en la base de datos a traves de objetos, siendo este una ORM (Object Relational Mapping)
* Hace una verificación de las queries implementadas en _compile time_ (tiempo de compilación)

Debido a las ventajas que provee la segunda opción, haremos uso de ___Room___.



La siguiente imagen muestra la arquitectura de Room:



<img src="images/room_arch.png" width="55%">



Room consta principalmente de los siguientes componentes:

* ___Database___: Clase que se encarga y contiene la base de dato  
* ___Entities___: Clases modelo que representan tablas. Normalmente son implementaciones cortas. 
* ___DAOs___: (Data Access Objects). Nos proveen de métodos para acceder a la base de datos.



A continuación veremos la implementación de cómo usar estos elementos.



Crearemos un proyecto en blanco, y crearemos un nuevo _package_ llamado ___room___ para almacenar las clases relacionadas a la base de datos.



#### Clonando el proyecto

Utilizaremos un [directorio base](base) del proyecto para evitar desarrollar ciertos módulos. 

#### Librería de Gradle

Requeriremos escencialmente dos dependencias en ___build.gradle___:

```kotlin
implementation 'androidx.room:room-runtime:2.2.5'
kapt 'androidx.room:room-compiler:2.2.5'
```

en el mismo archivo, activamos el plugin ___kapt___

```kotlin
plugins {
	  ...
    id 'kotlin-kapt'
}
```



#### Definiendo un Entity (Una tabla)

Crearemos una tabla para nuestra app, se llamará ___Vehicle___ y contendrá los siguientes campos:

* ___id___: Identificador numérico de nuestro coche
* ___brand___: Marca del coche
* ___model___: Modelo del coche
* ___platesNumber___: Número de placas
* ___isWorking___: Si el vehículo está habilitado

Con esto, crearemos un data class con estos datos. Para diferenciarlo de una clase simple, utilizaremos distintos _annotations_.

```kotlin
@Entity
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val model: String?,
    @ColumnInfo val brand: String?,
    @ColumnInfo(name = "plates_number") val platesNumber: String?,
    @ColumnInfo(name="is_working") val isWorking: Boolean
)
```

La anotación ___@Entity___ se coloca una línea anterior al data class definido. ___@PrimaryKey___ enuncia la llave primaria de nuestra tabla, y para nuestro caso utilizaremos un número que se incrementará automáticamente, por tanto usaremos ___autoGenerate=true___ (la asignación también puede ser manual). ___@ColumnInfo___ denota un campo en nuestra tabla, y podemos observar que tiene como parámetro opcional ___name="plates_number"___, lo que implica que en la tabla, el campo lleve el nombre dado en vez del de la variable.



#### Creando nuestro Data Access Object (DAO)

 Un ___DAO___ es una interfaz que define métodos representando ___queries___ a nuestra base de datos.

Definiremos algunos métodos para nuestro CRUD.

```kotlin
@Dao
interface VehicleDao {

    @Insert
    fun insertVehicle(vehicle: Vehicle)

    @Update
    fun updateVehicle(vehicle: Vehicle)

    @Delete
    fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM Vehicle WHERE id=:id")
    fun removeVehicleById(id: Int)

    @Delete
    fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM Vehicle")
    fun getVehicles(): List<Vehicle>

    @Query("SELECT * FROM Vehicle WHERE id = :id")
    fun getVehicleById(id: Int): Vehicle
}
```

Los métodos enlistados anteriormente reciben algún parámetro de entrada requerido para hacer algun movimiento en la tabla. Las anotaciones en la cabecera de la funciones enlistan el tipo de acción que se está ejecutando.

* ___@Delete___ nos permite eliminar un elemento pasando el objeto que lo representa.
* ___@Insert___ crea en la tabla el nuevo elemento que pasemos como parámetro.
* ___@Update___ permite actualizar un elemento de la tabla, siendo nuestro _Primary Key_ el valor para identificarlo.
* ___@Query___ admite una query personalizada a nuestra base de datos. los parámetros de entrada en la función se usan en el query precedida por dos puntos (__:__) . Los queries con algún error, serán identificados en tiempo de compilación.





#### Setup de la base de datos

Requerimos una representación de la base de datos para obtener acceso a ella y a la información que nos proveen los DAO's.  

Crearemos una clase abstracta que herede de ___RoomDatabase___.

```kotlin
@Database(entities = arrayOf(Vehicle::class), version = 1)
abstract class BeduDb : RoomDatabase(){
  
    abstract fun vehicleDao(): VehicleDao

}
```

La clase anterior contiene un método que nos provee acceso a nuestro _DAO_, que nos dará acceso a los datos.



La anotación ___@Database___ se coloca una línea antes de nuestra clase y  puede incluir los siguientes campos en su constructor:

```
Propiedad | Descripción | Parámetro
------ | ------ | --------
entities   | lista de entidades en la base de datos | Array<KClass<*>>
exportSchema | Bandera que decide si crear un historial del esquema de la base de datos en una carpeta | Boolean 
version | número de versión de la base de datos | Int
views | Lista de views en la base de datos | Array<KClass<*>>
```

En nuestro caso, declaramos únicamente como entidad a ___Vehicle___ y definimos como versión 1.

Dentro de nuestra clase, crearemos un método estático implementando el patrón __Singleton__, para asegurarnos de tener una sola instancia de nuestra base de datos.



```kotlin
companion object {
  private var beduInstance: BeduDb? = null

  const val DB_NAME = "Bedu_DB"

  fun getInstance(context: Context) : BeduDb?{
    if(beduInstance==null){

      synchronized(BeduDb::class){
        beduInstance = Room.databaseBuilder(
          context.applicationContext,
          BeduDb::class.java,
          DB_NAME)
        .fallbackToDestructiveMigration() // al cambiar de version, destruir info en vez de migrar
        .build()
      }
    }

    return beduInstance
  }
}
```



#### Enlistando nuestros vehículos

Ahora utilizaremos nuestros elementos en una lista de vehículos. Para eso abriremos ___VehicleListFragment___ y en algún punto de ___onCreateView___, crearemos un executor,que gestionará una tarea que corre en un hilo, estopara evitar que las queries a las bases de datos bloqueen el ___Main Thread___. ___NOTA:___ Evitaremos el uso de ___AsyncTask___ debido a que está obsoleto desde la API 30 (Android 11). 



```kotlin
 val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {

            val vehicleArray = BeduDb
                .getInstance(context = requireContext())
                ?.vehicleDao()
                ?.getVehicles() as MutableList<Vehicle>


            Handler(Looper.getMainLooper()).post(Runnable {
                adapter = VehicleAdapter(vehicleArray, getListener())
                recyclerVehicle.adapter = adapter
            })
        })
```



Dentro del método execute de nuestro executor encontramos una instancia de nuestra base de datos mediante ___getInstance___, obtenemos nuestro ___DAO___ y utilizamos uno de sus métodos: ___getVehicles___, para obtener la lista de vehículos disponible. dentro del método ___post()___ de nuestro ___Handler___ hacemos procesos dentro del ___UI Thread___, que en este caso es crear y asignar el adapter para la lista con los vehículos como parámetro.



#### Agregando un nuevo vehículo

Ahora abriremos ___AddEditFragment___, y dentro de ___onCreateView___  pondremos el código que nos permitirá agregar un nuevo elemento a nuestra tabla. Colocaremos un click listener para nuestro botón ___Agregar___ y ahí colocaremos el código. En esta ocasión no evaluaremos nada. El layout es el siguiente:

<img src="images/AddEdit.png" width="35%">

La implementación comienza por crear un nuevo objeto vehículo con los datos de los campos.

```kotlin
addButton.setOnClickListener{
            val vehicle = Vehicle(
                    brand = brandEdit.text.toString(),
                    platesNumber = platesEdit.text.toString(),
                    model = modelEdit.text.toString(),
                    isWorking = workingSwitch.isEnabled
            )
  

        ...
```



Este objeto debe ser la entrada de nuestro método ___insertVehicle___ definido en nuestro ___DAO__, por lo que crearemos un hilo con esta petición:

```kotlin
val executor: ExecutorService = Executors.newSingleThreadExecutor()

executor.execute(Runnable {
  BeduDb
  .getInstance(context = requireContext())
  ?.vehicleDao()
  ?.insertVehicle(vehicle)

  Handler(Looper.getMainLooper()).post(Runnable {
    findNavController().navigate(
      R.id.action_addEditFragment_to_vehicleListFragment
    )
  })
})
```

Nótese que la acción a ejecutar posterior a la tarea del hilo es redirigirse a la lista de vehículos (aquí hay un pequeño truco, debido que la navegación remueve el _Fragment_ de lista previo y reemplaza por uno nuevo, permitiendo tener una lista actualizada).

Recreamos el flujo y comprobamos que nuestro nuevo elemento se encuentre en la lista. Cerramos la aplicación y la información debería persistir.

<img src="images/saved.png" width="35%">



#### Eliminando un elemento de la lista

Ahora eliminaremos un elemento, para esto ya hay dos métodos en nuestro ___VehicleListFragment___ provenientes de una interfaz, uno de ellos es ___onDelete()___ y recibe como parámetro un vehículo, que en este caso será el elemento a eliminar:

```kotlin
    override fun onDelete(vehicle: Vehicle) {
        Executors
            .newSingleThreadExecutor()
            .execute(Runnable {
                BeduDb
                    .getInstance(context = requireContext())
                    ?.vehicleDao()
                    ?.removeVehicleById(vehicle.id)

                Handler(Looper.getMainLooper()).post(Runnable {
                    adapter.removeItem(vehicle)
                    Toast.makeText(context,"Elemento eliminado!",Toast.LENGTH_SHORT).show()
                })
            })
    }
```



Al eliminar un objeto, veremos el siguiente comportamiento:

<img src="images/remove-vehicle.gif" width="35%">



#### Filtrando elementos

Agregaremos al menos 4 vehículos a nuestra lista, dos deben coincidir en la marca del coche. 

<img src="images/list-vehicles.png" width="35%">

Crearemos un nuevo método en el DAO que nos de una lista filtrada de vehículos que tengan una misma marca.

```kotlin
    @Query("SELECT * FROM Vehicle WHERE plates_number = :platesNumber")
    fun getVehicleByPlates(platesNumber: String) : Vehicle
```



Ahora vamos a reemplazar el método ___getVehicles()___ por ___getVehiclesByBrand()___ en el _onCreateView_ de ___VehicleListFragment___.

```kotlin
val vehicleArray = BeduDb
                .getInstance(context = requireContext())
                ?.vehicleDao()
                ?.getVehiclesByBrand("Volkswagen")
```

Y observamos el resultado.

<img src="images/filtered-vehicles.png" width="35%">

Podemos jugar también con los siguientes dos métodos:

* ___getVehicleById(id)___: Nos regresará un único vehículo debido a que el id es único, por lo que el valor obtenido hay qué meterlo a un List. (imprime primero todos tus vehículos y extrae el id del que quieras filtrar).
* getVehicleByPlates(plates): Filtrará por placas. 

#### Valores únicos para las placas

Hasta ahora, podemos agregar placas del mismo valor; creamos dos vehículos con placas iguales para probarlo y luego eliminamos uno para que no quede guardado. Después, agregaremos esto en la cabecera de nuestro ___Entity___:

```kotlin
@Entity(indices = {@Index(value = {"first_name", "last_name"},
        unique = true)})
```

Al querer correr, nos va a dar un error debido a que estamos cambiando el esquema de nuestra tabla, esto implica elevar a una nueva versión de la tabla. Tendremos nuestra lista eliminada, por lo que agregaremos nuevamente un par de vehículos con mismas placas. Esta vez, nos saldrá este error.

<img src="images/error-unique.png" width="95%">

#### Podemos gestionar este error de forma más inteligente de diferentes formas. Te lo dejamos como reto!

#### Reducir campos en una petición

En nuestra lista, únicamente mostramos el modelo y las placas del cochedatos (el id es necesario), y otros valores como la marca o su disponibilidad no se muestran, por lo que costaría menos proceso reducirlo a estos.

Crearemos una nueva clase que contenga dichos valores:

```kotlin
data class ReducedVehicle(
    val id: Int,
    val model: String?,
    val platesNumber: String?
)
```

 y en nuestro DAO, un método que únicamente recupere estos datos:

```kotlin
@Query("SELECT id,model,plates_number as platesNumber FROM Vehicle")
    fun getReducedVehicles(): List<ReducedVehicle>
```

  Ahora reemplazamos el método actual para obtener ahora la versión reducida de vehículos. 

```kotlin
val vehicleArray = BeduDb
                .getInstance(context = requireContext())
                ?.vehicleDao()
                ?.getReducedVehicles() as MutableList<ReducedVehicle>
```

Esto implicará cambios de clases en otras instancias en nuestro ___VehicleListFragment___, ___VehicleAdapter___ e ___ItemListener___. Después de realizarlos, imprimimos la nueva lista en el log y verifiquemos que ahora solo salgan los datos demandados. El flujo debería funcionar de la misma forma.

[`Anterior`](../Reto-01) | [`Siguiente`](../Reto-02)      

</div>









