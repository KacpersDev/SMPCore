package me.koz.smpcore.perks.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoPickupListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        Player player = event.getPlayer();
        if (!player.hasPermission("beach.autopickup")) return;
        player.getInventory().addItem(new ItemStack(event.getBlock().getType()));
        event.setDropItems(false);
    }
}
