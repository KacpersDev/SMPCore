package me.koz.smpcore.envoy.event;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

@Getter
public class EnvoyEvent implements Listener {

    private final Main main;
    public static int chests;
    public static boolean started = false;

    public EnvoyEvent(Main main) {
        this.main = main;
    }

    public void start(){
        started = true;
        chests = Main.getInstance().getChest().getInt("chests");
        Bukkit.broadcastMessage(CC.translate(Objects.requireNonNull(this.main
                        .getConfig().getString("Envoy.messages.started"))
                .replace("%chests%", String.valueOf(chests))));
        event();
    }

    public void event(){
        for (int i = 0; i < this.main.getChest().getInt("chests"); i++) {
            Location location =
                    new Location(Bukkit.getWorld("World"),
                            this.main.getChest().getInt("Chest." + i + ".x"),
                            this.main.getChest().getInt("Chest." + i + ".y"),
                            this.main.getChest().getInt("Chest." + i + ".z"));
            location.getBlock().setType(Material.CHEST);
        }
    }

    public void end(){
        Bukkit.broadcastMessage(CC.translate(this.main.getConfig()
                .getString("Envoy.messages.end")));
        started = false;
    }

    public void clearChests(){

        for (int i = 0; i < this.main.getChest().getInt("chests"); i++) {
            Location location =
                    new Location(Bukkit.getWorld(Objects.requireNonNull(this.main.getChest().getString("Chest." + i + ".world"))),
                            this.main.getChest().getInt("Chest." + i + ".x"),
                            this.main.getChest().getInt("Chest." + i + ".y"),
                            this.main.getChest().getInt("Chest." + i + ".z"));
            location.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (!started) return;


        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)
        || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            for (int i = 0; i < this.main.getChest().getInt("chests"); i++) {
                Location configChest =
                        new Location(Bukkit.getWorld(Objects.requireNonNull(this.main.getChest().getString("Chest." + i + ".world"))),
                                this.main.getChest().getInt("Chest." + i + ".x"),
                                this.main.getChest().getInt("Chest." + i + ".y"),
                                this.main.getChest().getInt("Chest." + i + ".z"));
                Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                if (configChest.getX() == location.getX() && configChest.getY() == location.getY()
                && configChest.getZ() == location.getZ()) {
                    location.getBlock().setType(Material.AIR);
                    chests = chests - 1;
                    Bukkit.broadcastMessage(CC.translate(Objects.requireNonNull(this.main.getConfig()
                                    .getString("Envoy.messages.chest_taken"))
                            .replace("%amount%", String.valueOf((chests)))
                            .replace("%player%", player.getName())));
                    if (chests == 0) {
                        end();
                    }
                }
            }
        }
    }
}
