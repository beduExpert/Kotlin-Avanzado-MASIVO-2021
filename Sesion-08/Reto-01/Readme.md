[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Reto 1`

## Reto 1: Pruebas unitarias

<div style="text-align: justify;">

### 1. Objetivos :dart:

* Realizar pruebas de clases y métodos ailsaldos  en específico.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

En el ejercicio anterior, pudimos generar el método ___getNumberOfVehicles___. Ahora, lo haremos para ___activeVehiclesPercentage___.

a) Crear las pruebas unitarias para los siguientes casos:

1. Dada una lista de vehículos vacía, obtener de ___activeVehiclesPercentage___ un 0%.
2. Dada una lista de vehículos nula, obtener de ___activeVehiclesPercentage___ un 0%.
3. Dada una lista de vehículos con dos vehículos (uno con el valor isActive= true y el otro false), obtener de ___activeVehiclesPercentage___ un 50%.



<details>
	<summary>Solucion</summary>

```kotlin
@Test
fun activeVehiclesPercentage_empty_returnsZero(){
    val vehicles = listOf<Vehicle>()

    val result = activeVehiclesPercentage(vehicles)

    assertThat(result).isEqualTo(0)
}

@Test
fun activeVehiclesPercentage_null_returnsZero(){
    val vehicles = null

    val result = activeVehiclesPercentage(vehicles)

    assertThat(result).isEqualTo(0)
}

@Test
fun activeVehiclesPercentage_two_returnsFifty(){
    val vehicles = listOf(
            Vehicle(
                    0,
                    "pointer",
                    "Volkswagen",
                    "SMT01",
                    true
            ),
            Vehicle(
                    1,
                    "Vento",
                    "Volkswagen",
                    "GTA05",
                    false
            )
    )

    val result = activeVehiclesPercentage(vehicles)

    assertThat(result).isEqualTo(50f)
}
```


  </details>



b) Corregir el error encontrado en el Test Unitario.

<details>
	<summary>Solucion</summary>

```kotlin
internal fun activeVehiclesPercentage(vehicles: List<Vehicle>?): Float{

    if(vehicles == null || vehicles.isEmpty()){
        return 0f
    }

    val activeVehicles = vehicles.count{it.isWorking}
    val totalVehicles = vehicles?.size
    return ( (totalVehicles- activeVehicles)/totalVehicles.toFloat() ) * 100f
}
```


</details>



[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)      

</div>

