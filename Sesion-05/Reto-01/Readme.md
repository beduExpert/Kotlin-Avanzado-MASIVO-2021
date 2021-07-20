[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Reto 1 `

## Reto 1: Broadcast Recerivers

<div style="text-align: justify;">




### 1. Objetivos :dart:



### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

1. Tenemos una aplicación que permite grabar llamadas, siempre y cuando haya una y tengamos permiso de checar el estado del teléfono. Implementar las siguientes medidas"

a)  Agregar un botón de "Grabar llamada" que grabe únicamente cuando haya una  llamada en curso y con la condición dei tener el permiso de leer el estado del teléfono. En caso contrario, se debe mostrar un Toast especificando el por qué no se puede grabar.

b) Si se cumplen los casos anteriores, se procede a mostrar un mensaje que indique que se está grabando.

c) Al momento de colgar o cerrar la aplicación, se debe mostrar un mensaje indicando la finalización de la grabación. En este momento, el receiver se debe dar de baja. 



* Para verificar si tienes permiso de leer el estado del teléfono, puedes usar este método:

```kotlin
    private fun hasPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE ) == PackageManager.PERMISSION_GRANTED

    }
```

* Para pedir el permiso de leer el teléfono, puedes usar esta función:

```kotlin
  private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(READ_PHONE_STATE), 1)
        }
    }
```



* Para verificar si el usuario otorgó el permiso, se utiliza este callback:

```kotlin
 override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {

           
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
               // Aqui va el código si aceptó el permiso
            } else {
                Toast.makeText(
                    this,
                    "No puedes acceder sin este permiso",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    }
```



* Se requiere agregar el permiso ___"android.permission.READ_PHONE_STATE"___ en el manifest.
* Mediante ___telephonyManager.callState___ se puede leer el estado actual del teléfono, esta instancia se obtiene por el siguiente bloque de código:

```kotlin
val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
```

* TelephonyManager reconoce tres estados: CALL_STATE_OFFHOOK, CALL_STATE_RINGING y CALL_STATE_IDLE. El usuario debe buscar el valor de cada uno en la [documentación](https://developer.android.com/about/versions/11/reference/broadcast-intents-30)



2. Crear una aplicación que envíe un broadcast implícito que transmita datos de una sesión  y otra que la reciba, interoperarlas para observar su correcto funcionamiento

   Los datos que se tienen qué compartir obedecen a estos parámetros del bundle:

   ```kotlin
   val name = bundle?.getString("NAME")
   val isUser = bundle?.getBoolean("IS_USER")
   val version = bundle?.getString("APP_VERSION")
   val credit = bundle?.getInt("CREDIT")
   ```

   El receptor tiene qué imprimir estos datos en cuanto le sean proporcionados.

[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)      

</div>

