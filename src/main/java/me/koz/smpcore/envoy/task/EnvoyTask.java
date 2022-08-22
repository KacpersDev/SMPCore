package me.koz.smpcore.envoy.task;

import me.koz.smpcore.Main;
import me.koz.smpcore.envoy.event.EnvoyEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EnvoyTask {

    private final int starting = Main.getInstance().getConfig()
            .getInt("Envoy.start");
    private BukkitTask bukkitTask;
    private int timer;
    private final EnvoyEvent envoyEvent = new EnvoyEvent(Main.getInstance());

    public void startTask(){
        // starting task
    }

    public void cancelTask(){
        if (bukkitTask == null) {
            Bukkit.broadcastMessage("Task has not started");
        }
        bukkitTask.cancel();
        timer = 0;
        Bukkit.broadcastMessage("Task has been cancelled");
    }

    public void start(){
        this.bukkitTask =  new BukkitRunnable() {

            int seconds = (starting * 60);

            @Override
            public void run() {
                seconds--;
                timer = seconds;

                if (seconds <= 1) {
                    startTask();
                }

                Bukkit.broadcastMessage("Task " + seconds);
            }
        }.runTaskTimer(Main.getInstance(), 0L,20L);
    }

    public int getTimer() {
        return timer;
    }

    public void startEvent() {
        envoyEvent.start();
    }

    public void stopEvent() {
        envoyEvent.end();
        envoyEvent.clearChests();
    }
}
