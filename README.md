# Proyecto 3 - BD 2022 - Vuelos
Proyecto de Vuelos con MVC

Leer primero el [enunciado del proyecto 3](https://moodle.uns.edu.ar/moodle/pluginfile.php/1207755/mod_resource/content/6/Proyecto%203-BD2022.pdf), donde se encuentran todos los detalles de la aplicacion a completar, documentación, condiciones y fechas de entrega.

En la carpeta [doc](https://github.com/drg-dcic-uns/proyectoBD2022/tree/master/doc) podra encontrar dos diagramas de secuencia para dos casos de uso, que muestran la interacción entre las diferentes capas y clases que implementan de la aplicación.

## Preparación para comenzar con el proyecto

Se deberá descargar en forma local el proyecto, ya sea descargandolo o clonando el mismo desde la línea de comandos con:
```bash
git https://github.com/drg-dcic-uns/proyectoBD2022.git
```

## Requisitos del sistema

### 1. Java JDK instalado y configurado (JAVA_HOME y PATH)

### 2. Git

### 3. MySQL o MariaDB

### 4. Maven


## Como ejecutar un proyecto
El proyecto puede ser compilado y ejecutado desde Eclipse como fue explicado en el ejemplo de la [clase de Patron MVC en JAVA y MySQL](https://moodle.uns.edu.ar/moodle/course/view.php?id=12255#section-20). Tambien es posible hacerlo con [Maven](https://maven.apache.org/index.html). 
Aquí hay una guía rápida de este software [Maven en 5 minutos](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

En primer lugar es necesario disponer de un archivo JAR que usualmente estará alojado en la carpeta target del proyecto. 

Para ejecutar el proyecto, nos ubicamos donde está el archivo JAR, y lo ejecutamos con java y la opción -jar seguido del nombre del archivo.

```bash
cd target
java -jar proyecto-vuelos-jar-with-dependencies.jar
```
También está disponible la opción de ejecución con el plugin de maven a traves del siguiente comando
```bash
maven exec:exec
```


## Como generar un jar con las dependencias

Para generar con maven el archivo jar existen varias formas que dependerá del momento en que lo estemos realizando y lo que quisieramos que se modifique.

### Generar el jar inicial

Cuando es la primera vez que vamos a generar el jar, necesitamos que maven compile el codigo fuente y que descargue todas las dependencias que se encuentran especificadas en el archivo pom.xml. Por esa razón, deberemos ejecutar en la carpeta principal del proyecto el siguiente comando
```bash
mvn package
```
Una explicación sobre las distintas fases de maven la puede encontrar [aqui](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#running-maven-tools). Allí puede verse que package es una de las fases del ciclo de vida por defecto (Default).

### Borrar todo lo generado previamente por maven 

Para borrar las compilaciones previas y dependecias generadas o descargadas de puede ejecutar el comando:
```bash
mvn clean
```
También se puede ejecutar ambos ciclos de vida juntos, produciendo que se borre todo y se compile todo nuevamente.
```bash
mvn clean package
```
