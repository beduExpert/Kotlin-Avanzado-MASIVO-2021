[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 03`](../Readme.md) > `Ejemplo 3`

## Ejemplo 3: Retrofit y Coroutines

<div style="text-align: justify;">
### 1. Objetivos :dart:

- Aplicar los conceptos vistos en los anteriores ejemplos.
- Utilizar la librería retrofit.

### 2. Requisitos :clipboard:

1. Haber terminado todos los ejercicios anteriores de esta sesión.
2. Haber leído el prework.
3. Conocimiento de Coroutines.

### 3. Desarrollo :computer:

Vamos a crear una pantalla de  login.

Para eso utilizaremos una API con un endpoint para simular un login con algunos correos electrónicos preestablecidos. El enlace a la API es la siguiente:  https://reqres.in/.


1. Creamos un proyecto con actividad en blanco.

2.- Definimos una interfaz adecuada para nuestro dispositivo. En resumen, este layout contiene dos edit texts para ingresar correo y contraseña, y un botón de login para ejecutar la acción.

```xml
<?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center_horizontal"
        android:orientation="vertical"
        android:padding="48dp"
        tools:context=".LoginActivity">

        <ImageView
            android:id="@+id/img_bedu"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_marginTop="48dp"
            android:layout_marginBottom="32dp"
            android:src="@drawable/beto"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="64dp"
            android:text="@string/app_name"
            android:textAppearance="?attr/textAppearanceHeadline3" />

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/layout_user"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="4dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/edit_email"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:drawableStart="@drawable/ic_user"
                android:hint="Correo electrónico"
                android:inputType="textPersonName"
                android:maxLines="1" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/layout_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="4dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/edit_password"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:drawableStart="@drawable/ic_pass"
                android:hint="Contraseña"
                android:inputType="textPassword"
                android:maxLines="1" />
        </com.google.android.material.textfield.TextInputLayout>


        <com.google.android.material.button.MaterialButton
            android:id="@+id/buttonAccept"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:text="Iniciar sesión" />

        <TextView
            android:id="@+id/textRegister"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="Regístrate aquí"
            android:textAppearance="?attr/textAppearanceBody2" />
    </LinearLayout>
```

3. Pedimos permiso de internet en el *AndroidManifest.xml*:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

4. Escribimos las dependencias necesarias en el *app/build.gradle*. **P.D.** Picasso es una librería de *Square* (los creadores de okHttp y de Retrofit) que facilita la implementación de imágenes en vistas (en este caso, para insertar la imagen del pokemon en el ImageView).

```kotlin
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation 'com.squareup.okhttp3:logging-interceptor:4.9.1'
```

5. En un nuevo package que llamaremos model, Creamos la clase LoginResponse, que tendrá tanto el token cuando el login es exitoso, y un error como String cuando nuestrarespuesta sea fallida.

```kotlin
data class LoginResponse(
    val token: String,
    val error: String
)
```

6. Creamos un package llamado api, conteniendo su interfaz, donde guardaremos los detalles de la API. 

```kotlin
interface LoginService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String): Response<LoginResponse>
}
```
7. Ahora creamos un objeto Api, donde construimermos la intancia apuntando al endpoint del login.

   ```kotlin
   object Api{
       private val loggingInterceptor = HttpLoggingInterceptor()
   
       val httpClient = OkHttpClient.Builder()
           .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
           .build()
   
   
       val loginService: LoginService =
           Retrofit.Builder()
               .baseUrl("https://reqres.in/api/")
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(LoginService::class.java)
   
   }
   ```

8. seteamos el onClickListener del botón search, y verificamos en primera instancia que ambos campos no estén vacíos.

```kotlin 
val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Valores vacíos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
```

Después de verificar que los campos estén llenos, llamamos a nuestro endpoint dentro de una corrutina.

```kotlin
CoroutineScope(Dispatchers.IO).launch {
    try {
        val result = Api.loginService.login(email, password)

        withContext(Dispatchers.Main) {
            if (result.isSuccessful) {
                onSuccess(result.body())
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Tu correo o contraseña es incorrecto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    } catch (cause: Throwable) {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                this@LoginActivity,
                "Error de la API",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
```



El resultado es la siguiente pantalla:



<img src="images/01.png" width="33%">

[`Anterior`](../Reto-02) | [`Siguiente`](../Proyecto)      

</div>

