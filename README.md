# Portage
Portage is a multi-purpose, extensible network, optimized core written in Kotlin.

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
  ```mvn clean install```
  This compiles the plugin and will put the plugin inside of the /bukkit/target/ folder, under Portage-Bukkit-(version)-SNAPSHOT.jar

## Running:
  We currently only support 1 database, which is MongoDB, and the settings for it must be changed inside of the PortageAPI class, 
  we might add configurations later, however this is currently futile since it won't be widely used.