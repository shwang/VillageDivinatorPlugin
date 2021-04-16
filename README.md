# VillageDivinatorPlugin

A Spigot-server plugin that writes the coordinates of 10,000 villages to a JSON file, `spigot1.13.2/village_locations.json`.

## Installing and Generating `village_locations.json`.

### Downloading spigot1.13.2 and compiling the package.

First install `curl` and `mvn` if necessary. Then either run `./quickstart.sh` or run:

```bash
curl -o spigot1.13.2/spigot-1.13.2.jar https://cdn.getbukkit.org/spigot/spigot-1.13.2.jar

mvn package  # Compiles the Java plugin.
```

### Generating and importing your Minecraft world files
Open a Minecraft client, version 1.12.2 and generate some world $world_name there that you would like to have village locations for. Make sure to record the seed number of the world! This is necessary for calculating village coordinates later.


After you generated the Minecraft world, copy it to the spigot directory as `world/`:

```
cp -R ~/.minecraft/saves/$world_name spigot1.13.2/world
```

Now we will record the seed number in server.properties.

```
echo level-seed=$seed > spigot1.13.2/server.properties
```

It's OK to modify `server.properties` later if the seed changes. You'll just have to reset the server to load the configuration change.

### Launching the server and generating village coordinates

```
cd spigot1.13.2/
java -jar spigot-1.13.2.jar
```

This will start the server using our VillageDivinatorPlugin. You should see a message indicating that village coordinates were written to `village_locations.json`. Rejoice!

### Next Steps
Now that you have the coordinates of various villages in this Minecraft world, can copy the world into the `CHAI/mc_unified` repository and create an experiment that starts the player inside a random village by reading village coordinates from the JSON! Rejoice!


## FAQ

### Why do I have to generate a 1.12.2 world? Why can't I just let Spigot generate its default world?


Because we have a precarious compatibility situation.

If you were to start the spigot server before importing your own world, it would generate a default world and successfully locate villages, but that save world directory isn't compatible with `mc-unified`.

Version 1.12.2 worlds are the only ones that have been tested with both this village locating setup and the CHAI/mc-unified server.

### What is CHAI/mc-unified?
The MineRL BASALT demonstration server.

### What's the relationship between Spigot, Bukkit, CraftBukkit, PaperMC, and the vanilla Minecraft server?

The vanilla Minecraft Server is an official server that runs game logic for players that connect to the server using their Minecraft clients. Using a Minecraft client legally requires a game license / purchase. Minecraft Servers are freeware (but closed-source -- CraftBukkit's GitHub repository was DMCAed).

Bukkit is an API for making Minecraft server plugins and mods. You can import the api in Java as `org.bukkit.*`.

Bukkit has several implementations. CraftBukkit, Spigot, and PaperMC are all implementations of Bukkit.

By Steven's understanding, Spigot is a Minecraft server implementation that builds on top CraftBukkit. On the other hand, PaperMC is a true fork of Spigot.

CraftBukkit is no longer maintained by the original coders. It is now maintained by the Spigot team.

### Why can't we just run a variant of this plugin in CHAI/mc-unified to dynamically generate village locations?

mc-unified is a fork of the official MineRL team's demonstration server, using PaperMC and Bukkit API 1.12.2. Bukkit 1.12.2 doesn't have an API call for locating villages and other structures, and upgrading the demonstration server to 1.13+ is likely to be hairy.
