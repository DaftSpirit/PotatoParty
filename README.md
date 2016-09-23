# PotatoParty
Repo of the big project of 2048 game

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
* One with a run configuration with LauncherClient.java for the client side
 
## How to launch a game

Simply run the server wherever you want and then run as many clients as you want anywhere =)

# Have potato fun !

![potato](PotatoParty/res/potato.gif)

