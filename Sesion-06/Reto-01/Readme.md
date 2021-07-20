[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 06`](../Readme.md) > `Reto 1`

## Reto 1: Notificaciones

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Aplicar el conocimiento adquirido en el [Ejemplo 1](../Ejemplo-01)

### 2. Requisitos :clipboard:

* Haber cursado el [Ejemplo 1](../Ejemplo-01)

### 3. Desarrollo :computer:

Reforzaremos ciertos conceptos del ejemplo anterior.

1. Corremos la app en un dispositivo con OS menor a la API 26 Reproduciremos en secuencia y rápidamente los tres botones de la aplicación ¿Qué sucede? Se generaron nuevas notificaciones o se reemplazaron las mismas? Por qué creen que sucede esto? (Comentar y resolver con el instructor) y hacer que las notificaciones no se reemplacen.

<details>
	<summary>Solucion</summary>


	en cada generación de notificacción, hacer las id's diferentes para el método notify(id,builder.build()) 

</details>


2. Ahora, crear otro canal de push notifications, de modo que se vean como en la siguiente pantalla

<img src="images/01.png" width="33%"/>

la notificación simple y con botón deben pertenecer al canal *Diversos*, mientras que el de redireccionamiento debe estar en *Cursos*.

<details>
	<summary>Solucion</summary>


	Crear otro canal, suscribirlo en onCreate y asignarlo a las notificaciones correspondientes.

</details>

Apagar el switch del canal *Diversos*, abrir la app y accionar los tres botones ¿Qué sucede ahora? comentar la causa.


3. Hacer que la notificación de redireccionamiento también implemente el botón que acciona el Toast.

<details>
	<summary>Solucion</summary>


	Utiliza el cógido del PendingIntent y el método addAction del builder de la notificación en la función *buttonNotification*

</details>

[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)      

</div>

