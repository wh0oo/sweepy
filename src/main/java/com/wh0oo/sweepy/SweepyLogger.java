package com.wh0oo.sweepy;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SweepyLogger {
    private final Path logFile;
    private static final DateTimeFormatter TS = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public SweepyLogger(MinecraftServer server) {
        Path logsDir = server.getRunDirectory().resolve("logs");
        this.logFile = logsDir.resolve("sweepy.log");
        try {
            if (!Files.exists(logsDir)) Files.createDirectories(logsDir);
            if (!Files.exists(logFile)) Files.createFile(logFile);
        } catch (IOException ignored) {}
    }

    public void writeBlock(List<String> lines) {
        try {
            Files.writeString(
                logFile,
                "[" + TS.format(LocalDateTime.now()) + "]\n" + String.join("\n", lines) + "\n\n",
                StandardOpenOption.APPEND
            );
        } catch (IOException ignored) {}
    }
}
