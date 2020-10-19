FROM openjdk:14-alpine

WORKDIR /app

COPY ./src ./src
COPY ./lib ./lib
COPY ./db.properties .

RUN javac -cp .:./lib/* -d build src/main/java/records/*.java src/main/java/server/*.java

CMD ["java", "-cp","./lib/*:build", "server.Main"]



