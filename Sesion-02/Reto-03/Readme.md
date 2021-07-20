[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 02`](../Readme.md) > `Reto 3 `

## Reto 3: Retrofit

<div style="text-align: justify;">


### 1. Objetivos :dart:

- Implementar HttpLogging 
- Notificar al usuario si algo salió mal

### 2. Requisitos :clipboard:

1. Haber terminado TODOS los ejercicios anteriores.

### 3. Desarrollo :computer:

Para terminar con nuestro pokedex, debemos ser capaces de notificar al usuario cuando un pokemon no existe, y de rastrear detalles del tráfico.

  1. Instalar la siguiente dependencia

 ```groovy
 implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
 ```

 2.- Vamos a agregar el cliente okHttp a nuestro build de retrofit, para eso hay que definir antes el cliente por medio de esta líneas de código:

 ```groovy
 val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .build()
 ```

 El reto es donde suscribir al cliente para que tome efecto (**Hint:** es algo que ya se vio).

 El resultado es la impresión en el logcat que sucede cuando se hace una llamada con retrofit:

 <img src="images/01.png" width="33%">

 3.- Agregar un mensaje (puede ser un Toast) que le notifique al usuario cuando el pokemon que ingresó es inexistente o hay un problema de comunicación. (**Hint:** es en los callbacks de retrofit).

 El mensaje se debe ver parecido a esto: 

  <img src="images/02.png" width="33%">



[`Anterior`](../Ejemplo-03) | [`Siguiente`](../Proyecto)      

</div>

