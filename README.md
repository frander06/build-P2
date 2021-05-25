# Tarea-4 Uso de bibliotecas para aprendizaje supervisado

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

1. `meta`:

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

## protocolo SSH
