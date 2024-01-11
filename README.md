# iw2023-2024-placeholder
proyecto IW
## Integrantes
Alejandro Arias Casquero  
Carlos Flethes Montesinos  
Alvaro Orellana Serrano  

### Link autoevaluación
https://docs.google.com/spreadsheets/d/1l7mJAbHB67L6dBHAW9Miert09DWFSZs_6LwknHJae2Y/edit?usp=sharing
### Setup Proyecto

Una vez hecho el clone del proyecto, tenemos disponibles los siguientes comandos:

- **make setup:** Realiza el build de la imagen y hace un ***make up***.

- **make up:** Iniciaremos los contenedores en modo detach (cambiar Makefile y quitar la opcion -d para correrlo en modo interactive).

- **make down:** Pararemos y eliminaremos los contenedores.

- **make status:** Comprobaremos el estado de los contenedores.

- **make console:** Entraremos con una consola dentro del contenedor.

- **make start:** Iniciaremos el servidor de Spring.