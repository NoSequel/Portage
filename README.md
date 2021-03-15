# Portage
Portage is a multi-purpose, extensible network, optimized core written in Kotlin. 
I currently do not recommend using it in it's current state, 
therefore I won't publish releases for this until further notice.

# Todo
- Possibly add more features, such as essential commands, etc.
- Synchronize ranks with redis

# Features
We currently do not offer many features, however we offer the basic functionality most public cores do nowadays, such as:
- Ranks
- Punishments
- Granting System (including temporary grants, etc)
- Redis Synchronization (currently synchronizes grants & punishments)

# Building
We use [maven](https://maven.apache.org/) to manage our dependencies and build our plugin.

### Compiling:
You should compile the plugin using the command ```mvn clean install```, this will compile the plugin and put the bukkit jar under ```bukkit/target```
  
# Note
You are allowed to base a project off of this, and sell it, or use it for yourself, as long as you give proper and clear credits.