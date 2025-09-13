package com.wh0oo.sweepy;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SweepyTask implements Runnable {
    private final MinecraftServer server;
    private final SweepyConfig config;
    private final SweepyLogger logger;
    private final int intervalTicks;
    private final int minItemAgeTicks;
    private final int[] warnAtTicks;
    private long tick;
    private final Set<Integer> warnedThisCycle = new HashSet<>();
    private static final int LOG_MAX_PER_WORLD = 200;

    public SweepyTask(MinecraftServer server, SweepyConfig config) {
        this.server = server;
        this.config = config;
        this.logger = new SweepyLogger(server);
        this.intervalTicks = config.intervalTicks();
        this.minItemAgeTicks = config.minItemAgeTicks();
        this.warnAtTicks = config.warnAtTicks();
        this.tick = 0;
    }

    @Override
    public void run() {
        if (server.getPlayerManager() == null) return;

        tick++;
        int pos = (int)(tick % intervalTicks);
        if (pos == 0) pos = intervalTicks;

        for (int warnPos : warnAtTicks) {
            if (pos == warnPos && !warnedThisCycle.contains(warnPos)) {
                int secondsLeft = Math.max(1, (intervalTicks - warnPos) / 20);
                broadcast("[Sweepy] Ground items would be removed in " + secondsLeft + " seconds (dry run).");
                warnedThisCycle.add(warnPos);
            }
        }

        if (pos == intervalTicks) {
            int wouldRemoveTotal = 0;
            List<String> lines = new ArrayList<>();
            lines.add("Sweepy dry-run pass: interval=" + config.intervalSeconds + "s, warn=" + arr(config.warnSeconds)
                    + ", minAge=" + config.minItemAgeSeconds + "s");

            for (World world : server.getWorlds()) {
                int scanned = 0;
                int wouldRemoveWorld = 0;
                int logged = 0;
                String dim = world.getRegistryKey().getValue().toString();

                Box searchBox = new Box(-30_000_000, -2048, -30_000_000, 30_000_000, 4096, 30_000_000);
                List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, searchBox, e -> true);

                for (ItemEntity item : items) {
                    scanned++;
                    if (item.age >= minItemAgeTicks) {
                        if (logged < LOG_MAX_PER_WORLD) {
                            ItemStack stack = item.getStack();
                            Identifier id = Registries.ITEM.getId(stack.getItem());
                            int seconds = item.age / 20;
                            int x = (int)Math.floor(item.getX());
                            int y = (int)Math.floor(item.getY());
                            int z = (int)Math.floor(item.getZ());
                            lines.add(dim + " | age=" + seconds + "s | " + stack.getCount() + "x " + id + " @ (" + x + ", " + y + ", " + z + ")");
                            logged++;
                        }
                        wouldRemoveWorld++;
                        wouldRemoveTotal++;
                    }
                }

                lines.add(dim + " | scanned=" + scanned + " | wouldRemove=" + wouldRemoveWorld + (logged >= LOG_MAX_PER_WORLD ? " | (truncated)" : ""));
            }

            logger.writeBlock(lines);
            broadcast("[Sweepy] Dry run: would remove " + wouldRemoveTotal + " dropped items. See logs/sweepy.log");
            warnedThisCycle.clear();
        }
    }

    private void broadcast(String msg) {
        for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
            p.sendMessage(Text.literal(msg), false);
        }
    }

    private static String arr(int[] a) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i]);
        }
        return sb.append("]").toString();
    }
}
