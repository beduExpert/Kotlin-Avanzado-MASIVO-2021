[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo `

## Postwork

<div style="text-align: justify;">


### 1. Objetivos :dart:

* Estructurar de forma correcta nuestro proyecto
* Hacer uso de patrones de diseño y arquitectura en nuestro proyecto

### 2. Requisitos :clipboard:

* Instalación de dependencias como lifecyclecomponents

### 3. Desarrollo :computer:

Implementar un patrón de arquitectura es sumamente importante en un proyecto, debido a que aporta escalabilidad, flexibilidad y modularidad. 



#### Hamcrest

Esta arquitectura propuesta por Jetpack, Está conformada por los siguientes componentes:

* ___ViewModel___: Gestiona, recupera y provee de datos atados a un componente que conste de un ciclo de vida (Activity, Fragment, etc.). Como gestor de estado, depende únicamente de los repositorios con los que se comunica.
* ___LiveData___: Wrapper que permite volver una variable observable, respetando los ciclos de vida del componente que lo consuma.
* ___LifeCycleOwner___: Es una interfaz implementada en un ___AppCompactActivity___ o ___Fragment___ y nos permite suscribir componentes a su ciclo de vida, volviéndolos ***lifecycle-aware***. 



#### Tips

* Maneja los datos del ___View___ directamente mediante data binding.
* Bajo ninguna circunstancia, manejes datos en tu ___Activity___ o ___Fragment___, gestionalos mediante métodos y asignaciones desde el ___ViewModel___.

[`Anterior`](../) | [`Siguiente`](../)      

</div>

