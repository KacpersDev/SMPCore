package me.koz.smpcore.Listeners;

import me.koz.smpcore.utils.CC;
import me.koz.smpcore.commands.StaffMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class StaffModeListener implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.translate("&cYou can not break blocks in staff mode."));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.translate("&cYou can not place blocks in staff mode."));

        }
    }

    @EventHandler
    public void DropItemEvent(PlayerDropItemEvent e) {
        if (StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (StaffMode.STAFF.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        if (!StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) return;
        if (!(e.getRightClicked() instanceof HumanEntity)) return;
        ItemStack item = p.getEquipment().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        Player target = (Player) e.getRightClicked();
        if (item.getItemMeta().getDisplayName().equals(CC.translate("&e&lFreeze"))) {
            e.getPlayer().performCommand("freeze " + target.getDisplayName());
        }


    }

    @EventHandler
    public void PickupItemEvent(PlayerPickupItemEvent e) {
        if (StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

}






