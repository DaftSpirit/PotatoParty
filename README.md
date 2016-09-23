![potato](PotatoParty/res/potato.gif)
# PotatoParty
Repo of the big project of 2048 game <br/>
Made by @Karuskrokro && @DaftSpirit

## How to configure the game

You just have to update the server IPV4 address in the two classes :
* LauncherClient.java
    line **23** :
```java
InetAddress.getByName("localhost"), 9090);
```
* LauncherServer.java
    line **11** :
```java
new Thread(new Server(InetAddress.getByName("localhost"), 9090, worker)).start();
```

## How to build the game

You have to make **2** runnable .jar files 
* One with a run configuration with LauncherServer.java for the server side
```
    $> javac -classpath . *.java
    $> jar cvfm PotatoPartyServer.jar META-INF/MANIFEST.MF *.class
    $> java -cp ./PotatoPartyServer.jar LauncherServer  
```
* One with a run configuration with LauncherClient.java for the client side
```
    $> javac -classpath . *.java
    $> jar cvfm PotatoPartyClient.jar META-INF/MANIFEST.MF *.class
    $> java -cp ./PotatoPartyClient.jar LauncherClient  
```
 
## How to launch a game

Simply run the server wherever you want and then run as many clients as you want anywhere =)

## Sources

[2048 game model] (http://java-articles.info/articles/?p=516)

# Have potato fun !

![potato](PotatoParty/res/potato.gif)

