[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 06`](../Readme.md) > `Ejemplo 1`

## Ejemplo 1: Notificaciones

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Crear una notificación básica y activarla desde la aplicación.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Para este ejemplo, estaremos usando el [proyecto base](./base) dentro de esta sección. Estaremos utilizando la siguiente pantalla para lanzar nuestras notificaciones:

<img src="images/01.png" width="33%"/>

1. Desde android Oreo, tenemos qué registrar canales de push notifications, que agruparan estos en diferentes clasificaciones. los canales llevan un nombre, una descripción y un grado de importancia. 

Vamos a setear nuestro canal desde la creación de la aplicación (en ___NotificationApp___), para que permanezca	disponible en el ciclo de vida de la app:

```kotlin
@RequiresApi(Build.VERSION_CODES.O)
private fun setNotificationChannel(){
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
```

y lo implementamos en el método `onCreate` de la siguiente forma:

```kotlin
// Para android Oreo en adelante, s obligatorio registrar el canal de notificación
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    setNotificationChannel()
}
```

2. Creamos un nuevo archivo `NotificationUtils.kt` donde guardaremos métodos para lanzar nuestras notificaciones. Dentro de ese objeto crearemos un nuevo método que muestre una notificación básica:

```kotlin
@SuppressLint("MissingPermission")
fun simpleNotification(context: Context){
    with(context) {
        val builder = NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.triforce) 
            .setColor(getColor(R.color.triforce)) 
            .setContentTitle(getString(R.string.simple_title)) 
            .setContentText(getString(R.string.simple_body)) 
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) 

        //lanzamos la notificación
        NotificationManagerCompat
            .from(this)
            .notify(20, builder.build()) // en este caso pusimos un id genérico
    }
}
```

y reproducimos esa función como listener del botón de notificaciones simples en nuestro `MainActivity`.

```kotlin
with (binding) {
    btnNotify.setOnClickListener{
        executeOrRequestPermission(this@MainActivity) {
            simpleNotification(this@MainActivity)
        }
    }
  }
```

Si corremos la aplicación en el dispositivo con OS menor a android Oreo (API 26) y abrimos la configuración de nuestra app y damos click a notficaciones, veremos esta pantalla: 

<img src="images/02.png" width="33%"/>

3. Corremos la app mediante un dispositivo con API 26 para arriba. Al ver la configuración de la app, en nuestras notificaciones, veremos esta pantalla:

<img src="images/03.png" width="33%"/>

Cuál es la diferencia entre las dos? Por qué se da esta(s) diferencia(s)? Discutir en clase.

Damos click al botón de notificaciones simples, se emitirá una notificación. Desplaza hacia abajo el menú desplegable de arriba y visualiza la notificación: 

<img src="images/04.png" width="33%"/>



4. Ahora crearemos una notificación que nos redireccione a otra pantalla al hacer click sobre ella, para eso usaremos un PendingIntent (un Intent que puede ejecutar una acción de la app con permisos de la misma app pero desde afuera). El PendingIntent pasa como parámetro en *setContentIntent*, que ejecuta el Intent como acción del click y *setAutoCancel* borra la notificación al ser pulsada.

```kotlin
@SuppressLint("MissingPermission")
fun touchNotification(activity: Activity) {
    / /Un PendingIntent para dirigirnos a una actividad pulsando la notificación
    val intent = Intent(activity, BeduActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        activity,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(activity, NotificationApp.CHANNEL_ID)
        .setSmallIcon(R.drawable.bedu_icon)
        .setContentTitle(activity.getString(R.string.action_title))
        .setContentText(activity.getString(R.string.action_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent) // se define aquí el content intend
        .setAutoCancel(true) // la notificación desaparece al dar click sobre ella

    with(NotificationManagerCompat.from(activity)) {
        notify(20, builder.build())
    }
}
```

utilizamos este método dentro del click listener del botón correspondiente:

```kotlin
btnActionNotify.setOnClickListener {
    executeOrRequestPermission(this@MainActivity) {
        touchNotification(this@MainActivity)
    }
}
```

Seteamos este métoodo como listener de su respectivo botón y corremos la app. Debe mostrarse una notificación similar a esta: 

<img src="images/06.png" width="33%"/>

dar Click sobre ella, deberemos ser redirigidos a la siguiente pantalla:

<img src="images/05.png" width="33%"/>

5. Creamos una nueva notificación que tenga un botón, que emitirá un Toast cuando se pulse. Para eso, asignamos un nombre a la acción que detonará el botón como un valor estático y lo guardamos en `NotificationUtils.kt`:

```kotlin

// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"

```

La acción del botón se creará por medio de un BroadcastReceiver (escucharemos la acción), creamos una nueva clase:

```kotlin
class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        // Sólo escucharemos las acciones del tipo ACTION_RECEIVED (detonada por la notificacción)
        if(intent?.action == ACTION_RECEIVED){
            Toast.makeText(context, "Conectado exitosamente", Toast.LENGTH_SHORT).show()
        }
    }
}
```

Creamos el método que ejecutará nuestra notificación. Para ejecutar la acción, creamos otro *PendingIntent* que ejecutará el *NotificationManager* y lo asignamos a la función *addAction* que pide como parámetros un drawable, el texto de nuestro botón y el *PendingIntent*.

```kotlin
@SuppressLint("MissingPermission")
fun buttonNotification(activity: Activity) {

    //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
    val acceptIntent = Intent(activity, NotificationReceiver::class.java).apply {
        action = ACTION_RECEIVED
    }
    //creamos un PendingIntent que describe el pending anterior
    val acceptPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(activity, 0, acceptIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
        .setSmallIcon(R.drawable.bedu_icon)
        .setContentTitle(activity.getString(R.string.button_title))
        .setContentText(activity.getString(R.string.button_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(R.drawable.bedu_icon, activity.getString(R.string.button_text), // agregamos la acción
            acceptPendingIntent)

    with(NotificationManagerCompat.from(activity)) {
        notify(20, builder.build())
    }
}

```

asignamos la función al listener del respectivo botón. Corremos la app y damos click al tercer botón, deberá salir una push notification como la siguiente:

<img src="images/07.png" width="33%"/>

al darle click al botón, deberá reproducirse el Toast, tal como en la imagen:

<img src="images/08.png" width="33%"/>





[`Anterior`](../Readme.md) | [`Siguiente`](../Reto-01)      

</div>

