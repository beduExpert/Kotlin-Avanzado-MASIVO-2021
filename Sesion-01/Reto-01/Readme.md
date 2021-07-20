

[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 01`](../Readme.md) > `Reto 1`

## Reto 1: Animators

<div style="text-align: justify;">


### 1. Objetivos :dart:

- Aplicar el conocimiento de Animators

### 2. Requisitos :clipboard:

1. Android Studio Instalado en nuestra computadora.

### 3. Desarrollo :computer:



Al pulsar sobre cualquier parte de nuestro Constraint Layout (excepto botones o el mismo arwing), mover la nave a dicho punto, centrando el punto con su centro.





<details>
	<summary>Solucion</summary>


private fun moveAnywhere(event: MotionEvent): Boolean{

```kotlin
        if (event.action === MotionEvent.ACTION_DOWN) {
            val x = event.x - arwing.width/2
            val y = event.y - arwing.height/2

            Toast.makeText(this, "valor: $y", Toast.LENGTH_SHORT).show()

            arwing.animate().apply {
                x(x)
                y(y)
                duration = 1000
                interpolator = AccelerateInterpolator()
                start()
            }

        }

    return true
}
```

</details>

 



[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)

</div>

