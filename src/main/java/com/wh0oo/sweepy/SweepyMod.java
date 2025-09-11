package com.wh0oo.sweepy;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.MinecraftServer;

public class SweepyMod implements DedicatedServerModInitializer {
    private SweepyTask task;

    @Override
    public void onInitializeServer() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK.register(server -> {
            getTask(server).run();
        });
    }

    private SweepyTask getTask(MinecraftServer server) {
        if (task == null) {
            // interval = 6000 ticks (5 minutes), warn at 5520 (60s before)
            task = new SweepyTask(server, 6000, 5520);
        }
        return task;
    }
}
