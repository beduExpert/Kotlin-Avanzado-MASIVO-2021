[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Proyecto`

## Proyecto

<div style="text-align: justify;">

  

### 1. Objetivos :dart:

* Guardar información no sensible mediante Shared Preferences
* Utilizar Room para almacenar un set de datos locales y recuperarlos para su uso en la app.

### 2. Requisitos :clipboard:

* Librería Room

### 3. Desarrollo :computer:

En esta parte del proyecto, haremos uso de las dos principales herramientas que abordamos en la sesión. Mediante SharedPreferences, podemos guardar valores no sensibles que podrían ser algunos _flags_ indicando si ya realizamos alguna acción o aún no (un Boolean), datos de perfil no sensibles (Podríamos almacenar nickname, algunos datos de configuración de la app etc.) e incluso tokens como un jwt (esto claro, simulado).

Para la base de datos, podríamos guardar una tabla que represente algún set de elementos que tengan una importancia protagónica en nuestra app (como lista de productos, historial de transacciones, colección de tarjetas).



Lineamientos

1. Hacer uso de la librería SharedPreferences en al menos dos situaciones diferentes en la aplicación.
2. Utilizar Room al menos una vez en nuestra aplicación 
3. El uso de la librería Room debe implicar un CRUD.
4. El set de elementos debe estar relacionado a los elementos de las listas creadas en el curso Kotlin Intermedio.

[`Anterior`](../Reto-02) | [`Siguiente`](../Postwork)      

</div>

