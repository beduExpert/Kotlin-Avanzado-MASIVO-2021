[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 06`](../Readme.md) > `Reto 2 `

## Reto 2: Notificaciones Avanzadas

<div style="text-align: justify;">




### 1. Objetivos :dart:

Poner a prueba y enriquecer el conocimiento adquirido en el [Ejemplo 2](../Ejemplo-02)

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Utilizaremos como base el proyecto del [Ejemplo 2](../Ejemplo-02)

1. Modificar el método ***touchNotification*** de tal forma que cada que se ejecute, la notificcación tenga un id diferente al anterior y se puedan acumular varias de ellas. ¿Qué sucede cuando son muchas? Comentar con el grupo

2. Modificar el método ***cancelNotifications*** para que elimine únicamente las notificaciones expansibles, probar código.

<details>
	<summary>Solucion</summary>


Reemplazar el método ***cancelAll()*** por:

```kotlin
cancel(<id_de_la_notificación>)
```

3. Cambiar la prioridad de la notificación expandable por PRIORITY_MAX y luego por PRIORITY_LOW ¿Qué diferencias notas? Experimentar con los otros niveles.

[`Anterior`](../Ejemplo-02) | [`Siguiente`](../Proyecto)      

</div>

