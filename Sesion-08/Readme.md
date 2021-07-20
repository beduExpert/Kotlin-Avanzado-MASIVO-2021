[`Kotlin-Avanzado`](../Readme.md) > `Sesión 8`

## Sesión 8: Testing

<img src="images/testing.png" align="right" height="120" hspace="10">

<div style="text-align: justify;">

### 1. Objetivos :dart: 

- Automatizar el testing de una aplicación
- Implementar la metodología Test-Driven Development
- Corregir errores detectados por nuestro sistema

### 2. Introducción

En general, al desarrollar el código fuente de un software requerimos estar constantemente probando las características implementadas, ya que muchas veces estas no presentan el comportamiento esperado. Un desarrollo que no tome en cuenta una etapa de pruebas para corregir estos errores tiende a llevar retrasos y a dejar bugs en su versión final; es por eso que requerimos una planeación que tome en cuenta una capa de testing.

TDD (Test-Driven Development) es una metodología de desarrollo donde se desarrollan ___Test Cases___ para validar automáticamente el código de las distintas características de nuestro programa. Estos tests incluyen simples exámenes de comportamientos en un elemento aislado hasta la comprobación de un flujo de navegación completo. Cada Test Case se debe crear incluso antes de haber creado el feature.



<img src="images/test flow.png" align="center" width="35%" hspace="10">



#### Tipos de tests

Google divide los tipos de tests en tres categorías diferentes:

* ___Unit tests___ (small tests): Son casos de pruebas que evalúan funcionalidades específicas y aisladas de nuestro código. Por ejemplo, una clase que evalue si una contraseña es válida será aislada de cualquier dependencia de la que se apoye y se probarán distintas contraseñas para comprobar que valide correctamente. Estos tests se pueden ejecutar en nuestra IDE como en un dispositivo físico o emulado. Estos tests son llamados _Unit Tests_ o pruebas unitarias. 
* ___Integration tests___ (medium tests): Las pruebas unitarias verifican la función específica de una tarea, sin embargo, las tareas tienden a interactuar entre sí. En estos tests se ejecutan una serie de Unit Tests para evaluar su comportamiento en conjunto.
* ___End-to-End tests___ (UI tests o large tests): Estas pruebas emulan la interacción de un usuario con la interfaz, específicamente los flujos principales de nuestra aplicación para verificar que no exista ningún error en esta. estas son las más lentas de ejecutar, más complejas de desarrollar y requieren ser probadas en dispositivos con distintos tamaños de pantalla y hardware diferente.



### 3. Contenido :blue_book:

 

#### <ins>Unit Tests</ins>

Generaremos pruebas unitarias para una aplicación sencilla.

- [**`EJEMPLO 1`**](Ejemplo-01/Readme.md)
- [**`Reto 1`**](Reto-01/Readme.md)
- [**`EJEMPLO 2`**](Ejemplo-02/Readme.md)

#### <ins>Integrated tests y Tests instrumentados</ins>

Generaremos pruebas unitarias para una aplicación sencilla.

- [**`EJEMPLO 3`**](Ejemplo-03/Readme.md)



### 3. Proyecto :hammer:

Aplica los lineamientos que vienen en esta guía para definir y comenzar el desarrollo de tu proyecto.

- [**`PROYECTO SESIÓN 8`**](Proyecto/Readme.md)

### 4. Postwork :memo:

Esta es una guía anexa de consejos para una mejor planeación de tu proyecto para este módulo.

- [**`POSTWORK SESIÓN 8`**](Postwork/Readme.md)

<br/>

[`Anterior`](../Sesion-03/Readme.md) | [`Siguiente`](../Sesion-05/Readme.md)      

</div>

