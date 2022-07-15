# Multiplayer Wordle
An online and real-time multiplayer Wordle game written in plain Java 17. The only libraries used are Log4j and JUnit.

## Architecture
3 UML Class Diagrams (one for each package) can be found in [this folder](https://github.com/tizba/multiplayer-wordle/tree/main/UML%20Class%20Diagrams).  
These are super useful to understand the clean architecture of the project

## Requirements
Java Runtime Environment (JRE) version 8. That can be downloaded [here](https://www.java.com/fr/download/manual.jsp).

## Getting started
The .jar can be found [here](https://github.com/tizba/multiplayer-wordle/releases).  

5 options are available to run the .jar file. 
___
- Run a server and 2 clients (default). The server will listen on port 5000
```
    java -jar multiplayer-wordle-1.0.0.jar
```
___
- Run a server. The server will listen on port 5000
```
    java -jar multiplayer-wordle-1.0.0.jar server
```
___
- Run a server listening on a specific port
```
    java -jar multiplayer-wordle-1.0.0.jar server <port>
```
Example
```
    java -jar multiplayer-wordle-1.0.0.jar server 6789
```
___
- Run a client
```
    java -jar multiplayer-wordle-1.0.0.jar client
```
___
- Run 2 clients
```
    java -jar multiplayer-wordle-1.0.0.jar client client
```
