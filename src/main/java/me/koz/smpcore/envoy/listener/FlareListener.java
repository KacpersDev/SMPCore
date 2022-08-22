package me.koz.smpcore.envoy.listener;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

@Getter
public class FlareListener implements Listener {

    private final Main main;

    public FlareListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                .equalsIgnoreCase(CC.translate(this.main.getConfig()
                        .getString("Envoy.flare.name")))) {
            return;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "envoy start");
    }
}
