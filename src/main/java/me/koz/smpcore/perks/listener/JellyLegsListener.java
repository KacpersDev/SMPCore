package me.koz.smpcore.perks.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class JellyLegsListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {

        if (event.getEntity() != null && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.hasPermission("beach.jellylegs")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setDamage(0);
                }
            }
        }
    }
}
