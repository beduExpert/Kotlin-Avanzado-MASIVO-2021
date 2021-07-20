

[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 01`](../Readme.md) > `Reto 2`

## Reto 1: Animators

<div style="text-align: justify;">


### 1. Objetivos :dart:

- Aplicar el conocimiento de Animators

### 2. Requisitos :clipboard:

1. Android Studio Instalado en nuestra computadora.

### 3. Desarrollo :computer:

A partir del ejercicio de ___Shared Transition___ del [ejemplo 2](../Ejemplo-02/), crear la siguiente transición:

<img src="Images/exercise.gif" width="70%">



Esto implica agregar el título __Conciertos__ en ambos _layouts_ de los _activities_ y crear su animación. Como en este caso estamos compartiendo más de un _View_, la escena se construye con la siguiente nomenclatura:

>  ActivityOptionsCompat.makeSceneTransitionAnimation(context, ..sharedElements)

Donde:

* __context__ es el contexto de nuestro _activity_
* __sharedElements__ es uno o varios objetos de la clase ___androidx.core.util.Pair___, que son pares ___view,string___ donde se ingresa el _view_ a compartir y su *transitionName*.





<details>
	<summary>Solucion</summary>

```kotlin
binding.btnActivity2.setOnClickListener {

    val intent = Intent(this, SharedTransitionedActivity::class.java)

    val headerTransitionName = ViewCompat.getTransitionName(binding.imgConcert)?: " "
    val titleTransitionName = ViewCompat.getTransitionName(binding.titleConcert)?: " "

val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
    this,
    Pair.create(binding.imgConcert,headerTransitionName),
    Pair.create(binding.titleConcert,titleTransitionName),

)

startActivity(intent, options?.toBundle())
}
```



</details>

 



[`Anterior`](../Reto-02/Readme.md) | [`Siguiente`](../Proyecto/Readme.md)

</div>