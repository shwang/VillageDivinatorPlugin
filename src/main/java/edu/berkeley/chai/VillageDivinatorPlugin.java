package edu.berkeley.chai;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.print.attribute.HashAttributeSet;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;

public class VillageDivinatorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        World villageWorld = getServer().getWorld("world");

        final int N_UNIQUE_SAMPLES = 10_000;
        getLogger().info(String.format("Searching for %d villages", N_UNIQUE_SAMPLES));
        HashSet<Location> locations = new HashSet<>();
        while (locations.size() < N_UNIQUE_SAMPLES) {
            Location loc = villageWorld.locateNearestStructure(
                    randomLocation(villageWorld),StructureType.VILLAGE, 100, false);
            if (locations.size() >= 1000 && locations.size() % 1000 == 0) {
                getLogger().info(String.format("... %d / %d", locations.size(), N_UNIQUE_SAMPLES));
            }
            if (locations != null) {
                locations.add(loc);
            }
        }

        JSONArray jsonLocations = new JSONArray();
        for (Location loc : locations) {
            JSONObject j = new JSONObject();
            j.put("x", loc.getX());
            j.put("y", loc.getY());
            j.put("z", loc.getZ());
            jsonLocations.add(j);
        }

        String filename = "village_locations.json";
        Path locationsOutputPath = Paths.get(filename).toAbsolutePath();
        boolean ok = false;

        try {
            Files.write(locationsOutputPath, jsonLocations.toJSONString().getBytes());
            ok = true;
        } catch (IOException e) {
            getLogger().severe("Failed to write village locations file");
            getLogger().severe(e.getMessage());
        }
        if (ok) {
            getLogger().info("Wrote " + String.valueOf(locations.size()) + " location entries to " + locationsOutputPath.toString());
        }

        Bukkit.shutdown();

    }

    private static Location randomLocation(World world) {
        Random rand = new Random();
        // "Less than a billion and you'll be fine. Jk less than a million" -- Neel Alex 2021
        final double RADIUS = 1e6 - 1;
        double x = rand.nextDouble() * 2*RADIUS - RADIUS;
        double y = 0;
        double z = rand.nextDouble() * 2*RADIUS - RADIUS;
        return new Location(world, x, y, z);
    }

    @Override
    public void onDisable() {

    }
}
