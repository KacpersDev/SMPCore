package me.koz.smpcore.envoy.task;

import lombok.Getter;
import me.koz.smpcore.Main;
import me.koz.smpcore.envoy.event.EnvoyEvent;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class ServerTask extends BukkitRunnable {

    private final Main main;
    private int timer;
    private final int eTimer;
    private final EnvoyEvent event;

    public ServerTask(Main main) {
        this.main = main;
        timer = (this.main.getConfig().getInt("Envoy.start") * 60); // to seconds
        eTimer = (this.main.getConfig().getInt("Envoy.timer") * 60);
        this.event = new EnvoyEvent(this.main);
    }

    @Override
    public void run() {
        timer--;

        if (timer <= 1) {
            event.start();
            task();
            this.cancel();
        }
    }

    private void task(){

        new BukkitRunnable() {

            int eTimer = (Main.getInstance().getConfig().getInt("Envoy.timer") * 60);

            @Override
            public void run() {
                eTimer--;

                if (eTimer <= 1) {
                    event.end();
                    timer = (main.getConfig().getInt("Envoy.start") * 60);
                    new ServerTask(Main.getInstance()).runTaskTimer(Main.getInstance(), 0L,20L);
                    this.cancel();
                }
            }
        }.runTaskTimer(this.main,0L,20L);
    }
}
