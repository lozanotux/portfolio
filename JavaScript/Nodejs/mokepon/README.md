# MOKEPON

Este proyecto contiene un juego multijugador en linea para poner en práctica algunos conceptos de programación. Desarrollado con HTML, CSS, JavaScript para la parte del FrontEnd y con Node.js para la parte del Backend.

![snapshot](./public/assets/snapshot.png)

## Requisitos

Para probar este proyecto y poder ejecutarlo es necesario contar con los siguientes requisitos antes de proceder:
- Node.js 16 LTS
- Navegador Web de tu elección (Chrome, Firefox, etc)

## Como Probarlo?

Para ejecutar este juego en tu maquina local (PC, laptop, etc) y ocasionalmente en algun otro dispositivo conectado a la misma red (como un celular, tablet, etc). Se deben realizar los siguientes pasos para ejecutar el juego:

1. Editar los archivos [index.js](./index.js) y [mokepon.js](./public/js/mokepon.js) para que la variable `localhost` tenga el valor de tu **hostname** (en caso que tu sistema sea macOS o GNU/Linux) o la **IP** de tu maquina (en el caso de estar usando Windows).
    > **NOTA:** para obtener el **hostname** se puede utilizar el comando `hostname` y para macOS en concreto se le debe concatenar el sufijo ".local" para que funcione. Por ejemplo: **mi-mac.local**. Y para obtener la **IP** en windows puedes usar el comando `ipconfig` y buscar allí la IPv4 de tu maquina local.

2. Abrir una termina o linea de comandos y acceder a esta carpeta:
    ```bash
    $ cd Escritorio/mokepon
    ```

    > **NOTA:** dependerá mucho del sistema operativo que estés usando (Windows, macOS o GNU/Linux) de la ruta completa donde se encuentre tu carpeta `mokepon`. El comando anterior se deberá ajustar a ello.
3. Desde la misma terminal, descargamos las dependencias del proyecto y lo ejecutamos con el siguiente comando:
    ```bash
    $ npm install 
    $ node index.js
    ```

    > **NOTA:** este comando notificará cual es la dirección o URL que debe usarse en el navegador.
4. Desde un navegador web abrir la dirección o URL que el comando anterior arrojó como resultado de su ejecución. Por ejemplo: [http://localhost:8080/](http://localhost:8080/)