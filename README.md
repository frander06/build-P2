# Proyecto-2 Sistema Embebido Detector de Emociones

## Estudiantes:
Angelo M. Isaac Bonilla - 2016093978

Steven Rojas Cubero - 2017100749

Frander Díaz Ureña - 2016157881

Pablo Chaves Alfaro - 2017007204
        
    
## Tabla de contenidos
* [Información general](#Información-general)
* [Programa](#Programa)
* [Receta](#Receta)
* [Meta layers](#Meta-layers)
* [Protocolo SSH](#Protocolo-SSH)
* [Implementación de la imagen](#Implementación-de-la-imagen)

## Información general

Se esta creando un sistema embebido para el reconocimiento y clasificación de emociones faciales.
Primeramente, para crear nuestro sistema embebido se está haciendo uso del flujo de trabajo
de `Yocto Project`, el cual consiste en crear capas con dependencias que se derivan de recetas 
creadas por nosotros. Seguidamente, el programa de reconocimiento y clasificación de emociones
faciales esta soportado con la biblioteca de `OpenCV` que nos permite capturar imágenes de
computador para su proceso, el cual va a ser dado por algoritmos de `Machine Learning`, donde
la biblioteca de `TensorFlow-lite` de python va a hacer el trabajo. Posteriormente, nuestro
`machine target` es un Raspberry Pi 2 model B donde vamos a montar nuestro sistema 
operativo especializado, el cual está conectado a un `host` donde se va a enviar la información
recaudada bajo el protocol `SSH`.

## Programa

El programa se diseñó haciendo uso de las librerías de `TensorFlow` para crear un algoritmo
de `Machine Learning` que aprende mediante una serie de capas a reconocer las emociones en 
una imagen, la cual deberá estar suficientemente iluminada para tener una precisión alta en
el reconocimiento de la emoción como tal. Con el detector creado, se utilizó la biblioteca de 
`OpenCV` para lograr capturar fotogramas en un video utilizando la cámara del dispositivo 
y poder aplicar el modelo ya preentrenado (se utiliza un archivo con extensión .h5, que se 
obtiene al entrenar el modelo) para predecir las emociones de las imágenes entrantes. Estos 
fotogramas los estamos tomando cada segundo para poder obtener un buen análisis y reducir la
incertidumbre en emociones mal predichas.

Por otro lado, para implementar este programa en nuestro sistema embebido, la biblioteca de 
`TensorFlow` llega a ser muy pesada, debido a esto, se tiene la alternativa de utilizar 
`TensorFlow-lite`, donde debido a esto, modificamos le código para que utilizara esta 
biblioteca especializada en ser ligera para dispositivos móviles y sistemas embebidos.
Finalmente, los resultados capturados se guardan en un archivo con extensión .txt los cuales
llevan la hora de la captura junto con la emoción predicha y se guardan en la misma carpeta que 
se encuentre el programa.

## Receta

En nuestra receta la cual es llamada meta-cine, se encuentran todos los archivos fundamentales para 
el reconocimiento facial de emociones. en la dirección meta-cine/recipes-cine/cine/ se encuentra 
el archivo .bb el cual indica de donde se van a obtener los archivos necesarios para 
el algoritmo los cuales están ubicados en la dirección meta-cine/recipes-cine/cine/files
donde todos se encuentran en un archivo .tar.xz debido al flujo de trabajo de `Yocto Project`.
Finalmente, la configuración de la receta se da en el archivo de layer.conf que se encuentra
en la dirección meta-cine/conf/.

## Meta layers

Las meta-layers dentro del flujo de trabajo nos brinda las dependencias de nuestro programa
para que funcione correctamente. En nuestro caso, las layers a utilizar son:

1. `meta`: similar a ''openembedded'', que nos brinda paquetes básicos necesarios para poder correr
los bitbakes dentro del flujo de trabajo de `Yocto Project`. Proveniente de ''openembedded core''

2. `meta-poky`: referencia de poky para utilzar las dependencias del flujo de trabajo de yocto-project,
se utilizó esta debido a que sigue teniendo sopporte dentro de `GitHub` por la comunidad.

3. `meta-yocto-bsp`: layer que contiene la información del soporte que necesitan el 
hardware meta, la plataforma de hardware y grupo de dispositivos necesarios, en nuestro
caso nos proporciona las características de hardware del Raspberry pi.

4. `meta-openembedded`: colección de varias layers de ''OE-core universe''. En nuestro caso se utilizan:
    - `meta-oe`
    - `meta-python`
    - `meta-multimedia`
    - `meta-filesystems`

5. `meta-raspberrypi`: layer que contiene la información del sistema del rapsberry, es similar a 
`meta-yocto-bsp` pero se concentra solamente en las especificaciones del raspberry pi

6. `meta-tensorflow-lite`: layer que brinda las depedencias de tensorflow lite, único para
sistemas móviles y sistemas embebidos.

7. `meta-cine`: layer que contiene nuestra receta donde se encuentra el programa y toda la 
información que necesita para correrse.

## Protocolo SSH

El sistema embebido es necesario, para nuestra aplicación, estar conectado a un computador 
madre donde se van a ver los resultados obtenidos en el archivo .txt, para hacer esta conexión
se utilizó el protocolo SSH mediante las aplicaciones PuTTy y FileZilla. 
La conexión se hizo mediante un cable Ethernet entre el Raspberry Pi y la computadora madre.

Además, fue necesario agregar un archivo un archivo en blanco dentro de la sd-card el cual tiene
de nombre ''ssh'' y no posee alguna extensión. Seguidamente con el conocimiento de la ip del 
Raspberry Pi, el usuario y el puerto en el que está conectado se puede establecer la conexión
entre ambos dispositivos.


## Implementación de la imagen

En este repositorio se encuentran los archivos principales para hacer el bitbake utilizando 
el flujo de trabajo de `Yocto Project`, para poder instalar esta imagen, es necesario 
clonar este repositorio dentro de la carpeta de `poky-dunfell` la cual es el repositorio
que se utiliza como base para trabajar con `Yocto Project`. Luego de clonar este repositorio
solo es necesario iniciar el ambiente de trabajo y correr el bitbake que uno decida, en nuestro caso
utilzamos una imagen sato. Al terminar el bitbake, vamos a tener un archivo en la dirección 
/poky-dunfell/build-P2/tmp/deploy/images/raspberrypi2 el cual va a tener de nombre ''core-image-sato-raspberrypi2.rpi-sdimg''.
Este archivo es el que se utiliza en la sd-card del raspberry y listo, una vez particionado la sd-card 
el sistema embebido está funcionando.
