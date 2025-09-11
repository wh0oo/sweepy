package com.wh0oo.sweepy;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class SweepyMod implements DedicatedServerModInitializer {
    private SweepyTask task;

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            SweepyConfig cfg = SweepyConfig.loadOrCreate(server);
            task = new SweepyTask(server, cfg);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (task != null) task.run();
        });
    }
}
