[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo 2`

## Ejemplo 3: RxKotlin

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Entender el patrón Observable y su implementación para ReactiveX
- Comprender el uso de RxKotlin (Wrapper de RxJava)

### 2. Requisitos :clipboard:

* Haber estudiado previamente los temas en el prework relacionados a este.

### 3. Desarrollo :computer:

1. Instalamos las dependencias

```kotlin
implementation "io.reactivex.rxjava2:rxkotlin:2.4.0"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
```

2. Generamos una lista con números del 1 al 8 e imprimimos em pantalla sus potencias cuadradas: 

```kotlin  
val numsObservable = listOf(1,2,3,4,5,6,7,8) //lista del uno al ocho
            .toObservable() //Volveéndolo observable
            .observeOn(AndroidSchedulers.mainThread()) //correr en el main thread
            .map {number -> number*number} //número al cuadrado en la lista
            .subscribeBy ( //Gestionando los tres callbacks : 
                onError =  { it.printStackTrace() }, //cuando alguno de la iteración falla
                onNext = { println("numero: $it") }, //cuando se reproducjo una nueva iteración
                onComplete = {  } //cuando se completó la o
            )
```

Corremos la app y consultamos el logcat, visualizaremos lo siguiente:

<img src= "01.png" width="100%"/>


3. Ahora, guardaremos una lista de nombres por medio de un observable, para eso necesitamos un Layout con un *ListView* llamado ***lista***. 

4. Inicializamos una lsita de nombres y creamos un ArrayAdapter para simplificar la construcción de la lista. 

```kotlin
var names = arrayListOf("Juan")
val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,names)
lista.adapter = adapter
```

5. Y seteamos nuestro observable, que hara que se agregue el resto de nombres de la lista

```kotlin
val observable = listOf("Manuel", "Agnès", "Frida", "Anaïs")
            .toObservable()
            .observeOn(Schedulers.computation()) //correr en el main thread
            .subscribeBy (
                    onError =  { it.printStackTrace() },
                    onNext = {
                        names.add(it)
                        adapter.notifyDataSetChanged()
                    },
                    onComplete = {
                        progress.visibility = View.GONE
                    }
                )
```

Corre el proyecto, en la pantalla debe aparecer esta pantalla: 

<img src="02.png" width="33%"/>

[`Anterior`](../Reto-01) | [`Siguiente`](../Ejemplo-03)      

</div>





