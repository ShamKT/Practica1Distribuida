# Practica 1 Cliente-Servidor

Repositorio para la practica 1 del curso de computación distribuida.<br>
Todos los comandos se ejecutan desde el directorio raiz de la practica.

## Comando para compilar el cliente.

> javac -classpath "client/lib/Linux/lib/\*" -d client/build client/src/main/java/records/\*.java client/src/main/java/client/*.java ; cp client/GUI.fxml client/build/

## Comando para ejecutar el cliente en Linux.

> java -classpath "client/build:client/lib/Linux/lib/\*" --module-path ./client/lib/Linux/lib --add-modules javafx.controls,javafx.fxml client.Main

## Comando para ejecutar el cliente en Windows.

> java -classpath "client/build;client/lib/Windows/lib/\*" --module-path ./client/lib/Windows/lib --add-modules javafx.controls,javafx.fxml client.Main

## Comandos para iniciar el servidor y la base de datos.

> docker-compose build ; docker-compose up


 
