# Practica 1 Cliente-Servidor

Repositorio para la practica 1 del curso de computación distribuida.<br>
Todos los comandos se ejecutan desde el directorio raiz.

## Comando para compilar el cliente.

> javac -classpath "client/lib/Linux/*" -d client/build client/src/main/java/records/* client/src/main/java/client/*.java

## Comando para ejecutar el cliente en Linux.

> java -classpath client/build:client/lib/Linux/* --module-path ./client/lib/Linux --add-modules javafx.controls,javafx.fxml client.Main

## Comando para ejecutar el cliente en Windows.

> java -classpath client/build:client/lib/Windows/* --module-path ./client/lib/Windows --add-modules javafx.controls,javafx.fxml client.Main

## Comando para iniciar el servidor y la base de datos.

> docker-compose up


 
