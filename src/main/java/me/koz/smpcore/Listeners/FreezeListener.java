package me.koz.smpcore.Listeners;

import me.koz.smpcore.commands.Freeze;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class FreezeListener implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Freeze.FROZEN.contains(p.getUniqueId())) {
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onPLayerOpenInv(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (Freeze.FROZEN.contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void whenImmortal(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Freeze.FROZEN.contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }

    }
}

