[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Ejemplo 3`

## Ejemplo 3: Integrated tests y Tests instrumentados

<div style="text-align: justify;">
### 1. Objetivos :dart:

* Realizar pruebas de flujo mediante el emulador de android.

### 2. Requisitos :clipboard:

* Instalar las dependencias descritas a continuación.

### 3. Desarrollo :computer:

```groovy
androidTestImplementation "androidx.test.espresso:espresso-contrib:3.5.1"
androidTestImplementation "androidx.arch.core:core-testing:2.2.0"
debugImplementation "androidx.fragment:fragment-testing:1.6.0"
androidTestImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1"
androidTestImplementation "com.google.truth:truth:1.1.2"
```



Los tests instrumentados unitarios son las pruebas que corren en emuladores y dispositivos android, utilizando las APIs que el SDK proporciona, a diferencia de los Unit Test simples que no tienen dependencias relacionadas con Android.  

<img src="images/generate.png" width="45%">

Daremos ok y verificamos que la clase creada esté en ___androidTest___.

```kotlin
@SmallTest
@RunWith(AndroidJUnit4::class)
class VehicleListFragmentTest {

}
```

Ahora crearemos un test para nuestro ViewModel (el test debe ir dentro de ***androidTest\***). En este caso, AndroidJUnit4 nos proveerá de una versión de JUnit capaz de ejecutar pruebas de instrumentación en un dispositivo físico o emulador.

#### Corriendo un Fragment en Testing

Para poder lanzar un Fragment y desplegar su interfaz de usuario, se requiere de un view raíz dentro de un _Activity_ que la contenga, este view lo determinamos en el layout de nuestro activity y a este se le asignan los fragments de forma estático o dinámicamente (a través de la clase o con ayuda de un navigation grpah). Sin embargo, para realizar una prueba instrumentada no existe un root que albergue nuestro fragment y su UI, por lo cual utilizamos el método ___launchFragmentInContainer___ para adjuntar el fragment a un view container. Agregaremos el método _sleep_ para congelar el hilo 4 segundos y poder apreciar la interfaz.

```kotlin
launchFragmentInContainer<VehicleListFragment>()
Thread.sleep(4000)
```

Si corremos el test, obtendremos el siguiente error:

> line #62: Error inflating class com.google.android.material.floatingactionbutton.FloatingActionButton

La razón se muestra más abajo en el stack trace:

> Caused by: java.lang.IllegalArgumentException: The style on this component requires your app theme to be Theme.AppCompat (or a descendant).

Debido a que el fragment se lanza en un contenedor vacío  ypor ende, sin theme, debemos definir uno:

```kotlin
launchFragmentInContainer<VehicleListFragment>(null, R.style.Theme_RoomVehicles)
```

El primer argumento es nulo ya que no requerimos ningún argumento para este fragment, el segundo parámetro es el tema por defecto en nuestra aplicación. Se desplegará lo siguiente:

<img src="images/launched-fragment.png" width="75%">

 

#### Creando un Service Locator

El patrón Service Locator permite que una clase obtenga sus dependencias a través de un contenedor en específico, en vez de inyectárselas independientemente.  



<img src="images/service-locator.png" width="55%">

En nuestro caso, nos será útil debido a que no podemos instanciar el _Fragment_ que vamos a lanzar en nuestro test, eso lleva a no tener control de la creación del _viewModel_ y por lo tanto, no podemos manipular el repositorio. Por esto, crearemos un locator, que contendrá una instancia del repositorio que será utilizada tanto por el _viewModel_ como en el test para validar  la información de nuestras pruebas.

Mediante nuestro `ServiceLocator`, guardado en el package `data`, haremos lo siguiente:

* Proveer de un repositorio, si este no existe aún, crear una nueva instancia.
* Resetear el repositorio, que permitirá reiniciar los datos para cada prueba.

Una de las características de este locator es la de implementar una forma de gestionar el uso de nuestra base de datos de modo que no se creen inconsistencias de información debido a consultas simultáneas de las pruebas.

```kotlin
object ServiceLocator {

    private var database: VehicleDb? = null
    var repository: Repository? = null

    private val lock = Any()

    fun resetRepository(){

        synchronized(lock){
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }

    }

    fun provideRepository(context: Context): Repository{
        synchronized(lock){
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): Repository{
        database = VehicleDb.getInstance(context)
        val repo = VehicleRepository(database!!.vehicleDao())
        repository = repo
        return repo
    }

}
```



el repositorio guardado en la aplicación ahora debe utilizar el método ___provideRepository___ del ServiceLocator.

```kotlin
val vehicleRepository: Repository
        get() = ServiceLocator.provideRepository(this)
```



AddEditViewModel debe recibir un ___Repository___ en vez de ___VehicleRepository___, para poder usar el fake y el real.

```kotlin
class AddEditViewModel(private val vehicleRepository: Repository): ViewModel() {}
```

#### Probando el botón eliminar vehículo.

En el package `org.bedu.architecturecomponents`, crearemos un nuevo package llamado `data`, copiaremos el `FakeVehicleRepository` de `unitTest` y lo pegaremos en este, para poder hacer uso de él.

El código general de nuestra test class es el siguiente:

```kotlin
@get:Rule val instantExecutorRule = InstantTaskExecutorRule()
@get:Rule val coroutineTestRule = CoroutineTestRule()

@Volatile
private lateinit var vehicleRepository: FakeVehicleRepository
    @VisibleForTesting set

private val vento = Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true)
    private val jetta = Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)

@Before
fun setup() {
    vehicleRepository = FakeVehicleRepository()
		val vehicles = listOf(vento, jetta)
		vehicleRepository.populateVehicles(vehicles)
		ServiceLocator.repository = vehicleRepository
}

@After
fun tearDown() {
    ServiceLocator.resetRepository()
}
```



En nestro método de test ***removeButton_RemovesVehicle***, poblamos nuestro repositorio con un par de vehículos.

```kotlin
val vehicles = listOf(vento, jetta)
vehicleRepository.populateVehicles(vehicles)
```



Se lanza el fragmento ___VehicleListFragment___, y mediante espresso, activaremos la acción de eliminar un vehículo simulando un click en el botón delete.

```kotlin
Thread.sleep(1000)
Espresso.onView(withId(R.id.list))
    .perform(
        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            ViewMatchers.hasDescendant(
                ViewMatchers.withText("Jetta")
            ),
            itemAction(R.id.button_delete)
        ))
Thread.sleep(1000)
```



donde `itemAction` implementa `ViewAction`: 

```kotlin
fun itemAction(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on action button"

    override fun perform(uiController: UiController?, view: View?) {
        val button = view?.findViewById<ImageButton>(viewId)
        button?.performClick()
    }
}
```



Finalmente, debemos corroborar que el elemento eliminado ya no se encuentre en nuestro repositorio de vehículos.

```kotlin
assertThat(vehicleRepository.getVehicles().value).doesNotContain(jetta)
assertThat(vehicleRepository.getVehicles().value).contains(vento)
```

Si corremos la aplicación, probablemente obtendremos el siguiente error:

> on view 'Animations or transitions are enabled on the target device.

Esto se debe a que tenemos habilitados los servicios de animación que vuelven más lento el proceso de testing. Para que el test funcione bien, habría qué deshabilitarlos. 

Settings > System > Advanced > About (emulated device) > Build number (click múltiples veces)

Settings > System > Advanced > Developer options . Aquí hay qué deshabilitar los siguientes parámetros:

- Window animation scale
- Transition animation scale
- Animator duration scale



Al correr nuevamente esto, la prueba funcionará. Veremos en la parte superior de la opción __Run__ el siguiente mensaje:



<img src="images/passed-test.png" width="60%">



 El siguiente gif muestra la reproducción del test:

<img src="images/passed-test.gif" width="80%">



__NOTA:__ Con motivo de visualizar la UI, se utilizó ___Thread.sleep()___, sin embargo no debe utilizarse porque realentiza de forma considerable los tests instrumentados.





[`Anterior`](../Ejemplo-02) | [`Siguiente`](../Proyecto/Readme.md)      

</div>

