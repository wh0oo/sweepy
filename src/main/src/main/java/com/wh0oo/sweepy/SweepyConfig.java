package com.wh0oo.sweepy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SweepyConfig {
    public int intervalSeconds = 420;
    public int[] warnSeconds = {60, 30, 10};
    public int minItemAgeSeconds = 120;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "sweepy.json";

    public static SweepyConfig loadOrCreate(MinecraftServer server) {
        Path configDir = server.getRunDirectory().toPath().resolve("config");
        Path file = configDir.resolve(FILE_NAME);

        try {
            if (!Files.exists(configDir)) Files.createDirectories(configDir);
            if (Files.exists(file)) {
                String json = Files.readString(file);
                SweepyConfig cfg = GSON.fromJson(json, SweepyConfig.class);
                return cfg != null ? cfg : writeDefault(file);
            } else {
                return writeDefault(file);
            }
        } catch (IOException | JsonSyntaxException e) {
            try { writeDefault(file); } catch (Exception ignored) {}
            return new SweepyConfig();
        }
    }

    private static SweepyConfig writeDefault(Path file) throws IOException {
        SweepyConfig def = new SweepyConfig();
        Files.writeString(file, GSON.toJson(def));
        return def;
    }

    public int intervalTicks() { return Math.max(40, intervalSeconds * 20); }
    public int minItemAgeTicks() { return Math.max(0, minItemAgeSeconds * 20); }
    public int[] warnAtTicks() {
        int it = intervalTicks();
        int[] out = new int[warnSeconds.length];
        for (int i = 0; i < warnSeconds.length; i++) {
            int w = Math.max(1, warnSeconds[i] * 20);
            out[i] = Math.max(1, it - w);
        }
        return out;
    }
}
