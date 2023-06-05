[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 03`](../Readme.md) > `Reto 2`

## Reto 2: Cámara

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Aprender el flujo para pedir permiso de cámara dentro de la misma Activity

### 2. Requisitos :clipboard:

1. Haber realizado el [Ejemplo 2](Ejemplo-02)

### 3. Desarrollo :computer:

Reemplazar la Acitvity de inicio por la de la cámara.

Para esto, vamos a requerir trasladar la petición de permisos a la pantalla de la cámara.

El flujo debe ser el siguiente:

- Al iniciarse la app sin ningún permiso, pedirlo. Si se da el permiso, inicializar la cámara; si no, el botón de la cámara debe realizar la petición del permiso cuando se le de click.

- Al iniciarse la app con permiso, iniciar directamente la app.

<details>
	<summary>Solucion</summary>
1. En el archivo de Manifiesto, Reemplazar 
Elimina la declaración del CameraActivity actual

```xml
<activity android:name=".CameraActivity">
```

Y reemplaza la declaración de MainActivity por el de la cámara

```xml
<activity
            android:name=".CameraActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```

2. Englobar el startCamera y el listener del botón en un método setupCamera:

   ```kotlin
    private fun setupCamera() {
           lifecycleScope.launch {
               startCamera()
           }
   
           binding.captureButton.setOnClickListener {
               takePhoto()
           }
       }
   ```

​	3. Escribir esta condicionante

```kotlin
if(checkCameraPermission()){
            setupCamera()
        } else{
            requestPermissions()
            binding.captureButton.setOnClickListener {
                requestPermissions()
            }
        }
```

4. copiar y pegar las funciones ***onRequestPermissionResult***, ***checkCameraPermission*** y ***requestPermissions*** en *CameraActivity*.  Si el permiso es aceptado en onRequestPermissionResult, llamar a setupCamera().

</details>



[`Anterior`](../Ejemplo-02) | [`Siguiente`](../Proyecto)      

</div>

