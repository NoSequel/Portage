# Portage
Portage is a multi-purpose, extensible network, optimized core written in Kotlin. 
I currently do not recommend using it in it's current state, 
therefore I won't publish releases for this until further notice.

# Todo
- We currently do not have any form of synchronization, therefore it should ideally only be used on single-instance servers, we are planning to add this as a feature later on.
- The entire command API requires a recode, the code is terrible and was ported from Java to Kotlin without paying any attention.
- Possibly add more features, such as essential commands, etc.

# Features
We currently do not offer many features, however we offer the basic functionality most public cores do nowadays, such as:
- Ranks
- Punishments
- Granting System (including temporary grants, etc)

# Building
We use [maven](https://maven.apache.org/) to manage our dependencies and build our plugin.

## Compiling:
You should compile the plugin using the command ```mvn clean install```, this will compile the plugin and put the bukkit jar under ```bukkit/target```

## Running:
  We currently only support 1 database, which is MongoDB, and the settings for it must be changed inside of the PortageAPI class, 
  we might add configurations later, however this is currently futile since it won't be widely used.
  
# Note
You are allowed to base a project off of this, and sell it, or use it for yourself, as long as you give proper and clear credits.