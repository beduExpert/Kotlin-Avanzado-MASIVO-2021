[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 06`](../Readme.md) > `Ejemplo 3`

## Ejemplo 3: Notificaciones y FCM

<div style="text-align: justify;">


### 1. Objetivos :dart:

* Instalar y utilizar el SDK de Firebase Message Cloud para el uso de push notifications

### 2. Requisitos :clipboard:

1. Haber cursado los temas previos sobre notificaciones
2. Leer todo lo relacionado a FCM en el prework
3. Un dispositivo (emulador o físico) con Google Play Services

### 3. Desarrollo :computer:

Para este ejemplo utilizaremos el [Proyecto base](./base) que tiene algunos componentes y recursos preestablecidos.

#### Firebase - Setup inicial

Antes de implementar firebase en nuestra app, debemos configurar un proyecto en la Firebase console. Para esto seguiremos los siguientes pasos:

a) Abriremos la [Firebase Console](https://console.firebase.google.com/?hl=es) con una cuenta google que poseamos y crearemos un proyecto nuevo.

<img src="./img/setup/01.png" width="40%"/>

b) Asignamos un nombre (en este caso, le llamaremos BeduPracticas)

c) Aceptaremos Google Analytics 

<img src="./img/setup/02.png" width="40%"/>

d) Seleccionamos México como *Ubicación de Analytics*, aceptaremos todos los términos y click en *Crear proyecto*

<img src="./img/setup/03.png" width="40%"/>

e) En la pantalla de inicio del proyecto, buscar el ícono de android y dar click sobre él

<img src="./img/setup/04.png" width="40%"/>

f) Registrar el nombre del paquete de la aplicación y su nick

<img src="./img/setup/05.png" width="40%"/>

g) Descargar el archivo *google-services.json* y moverlo a la carpeta app del proyecto, como se indica en la imagen

<img src="./img/setup/06.png" width="40%"/>


Vamos a comenzar instalando lo necesario para hacer funcionar Firebase

1. Abrir el archivo *build.gradle* que está en la raíz de nuestro proyecto.

2. Copiar los repositorios necesarios tal como se muestra a continuación:

```kotlin
plugins {
 	...
    id 'com.google.gms.google-services' version '4.3.15' apply false
}

```

2. Abrir *app/build.gradle* y aplicar el plugin de fabric después del plugin *com.android.application*:

```groovy
plugins{
  ...
	id 'com.google.gms.google-services'
}
```



3. Ahora, agregaremos la BoM de Firebase (Bill of Materials), que nos sirve para tener qué declarar nuestra versión de firebase únicamente en el mismo BoM.

   ```groovy
   implementation platform('com.google.firebase:firebase-bom:32.1.0')
   ```

4. En el mismo archivo, agregar la dependencia de FCM

```kotlin
implementation 'com.google.firebase:firebase-messaging-ktx'
```

#### Implementando FCM

1. Creamos un clase que extienda de FirebaseMessagingService e Incluimos el servicio de FCM como service en el Manifiesto de nuestra aplicación. Esto sirve para opciones más allá de recibir notificaciones en background (mensajes en foreground, entre otras opciones).

```kotlin
class FirebaseMessaging: FirebaseMessagingService() {

}
```

```xml
 <service
            android:name=".FirebaseMessaging"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>
```

2. En el manifest pondremos estas líneas para agregaar un ícono si desde el servicio no se define uno, al igual que el otro metadata para notification channels.

```xml
<meta-data
    android:name="com.google.firebase.messaging.default_notification_icon"
    android:resource="@drawable/bedu_icon" />
<meta-data
    android:name="com.google.firebase.messaging.default_notification_channel_id"
    android:value="DEFAULT_CHANNEL" />
```

3. En el *onCreate* del *MainActivity*, obtener el token FCM de nuestro dispositivo cliente e imprimirlo en el log (para pruebas).

```kotlin
FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Error", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("FCM_TOKEN",token)
            Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
        })
```

4. Ejecutaremos una prueba de notificación desde nuestra consola Firebase. En la barra lateral del menú principal, click a la opción *CloudMessaging*.

<img src="img/05.png" width="40%"/>

5. Click en ***Send your first message***

<img src="img/06.png" width="40%"/>

6. Escribir el título y el contenido de nuestra notificación.

<img src="img/07.png" width="40%"/>

7. Correr la aplicación, copiar el token como viene en el logcat (Filtrar el resultado del logcat con el Tag *FCM_TOKEN*) y cerrar la app.

<img src="img/08.png" width="40%"/>

8. Dar click en *Enviar mensaje de prueba*

9. Ingresar el token copiado en la ventana, agregarlo y pulsar Test. **Nota: Importante cerrar la app o minimizarla**

<img src="img/09.png" width="40%"/>

10. Recibirán una notificación como esta: 

<img src="img/10.png" width="40%"/>

#### Declarando el canal de notificación

Vamos a declarar el canal de notificación a utilizar, En el `FirebaseApp`.

```kotlin
class FirebaseApp: Application() {

    companion object {
        const val CHANNEL_ID = "DEFAULT_CHANNEL"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Canal Generico",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "CANAL GENERICO"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}
    
```



#### Aplicación en primer plano

Ahora vamos a correr la aplicación, mantenerla en primer plano y enviar otro mensaje de prueba por medio de la consola ¿Qué sucedió? No se recibe ningún mensaje porque se necesita habilitar un servicio que gestione la notificación en primer plano (la clase vacía que creamos previamente y que la declaramos como servicio en el Manifiesto).

En nuestra clase *FirebaseMessaging*, haremos un *override* del método onMessageReceived para gestionar el mensaje arrivante. Los parámetros de la notificación (como título y cuerpo) los obtenemos de nuestro objeto ___remoteMessage___:

```kotlin
override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }
```

En el método anterior, mandamos a llamar al método ___sendNotification()___, cuyo propósito es detonar nuestra notificación a través de nuestro ___NotificationManager___. Por lo cual la declaramos de la siguiente forma:

```kotlin
    @SuppressLint("MissingPermission")
    private fun sendNotification(title: String?, body: String?) {
        Log.d("FCM_MESSAGE", "Cuerpo de la notificación: $body")

        val notificationBuilder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(title)
            .setContentText(body)


        //lanzamos la notificación
        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build()) //en este caso pusimos un id genérico
        }
    }
```

Esta función nos permite mostrar una notificación personalizada con el título y el cuerpo de nuestra notificación. Sin embargo, podemos personalizar nuestra notificación de acuerdo a la función a cumplir (botones personalizados, poder enviar un mensaje, rechazar una llamada, etc.).

[`Anterior`](../Reto-02) | [`Siguiente`](../Ejemplo-04)      

</div>
