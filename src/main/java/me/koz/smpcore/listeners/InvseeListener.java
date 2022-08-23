package me.koz.smpcore.listeners;

import me.koz.smpcore.utils.CC;
import me.koz.smpcore.commands.StaffMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InvseeListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        if (!StaffMode.STAFF.contains(e.getPlayer().getUniqueId())) return;
        if (!(e.getRightClicked() instanceof HumanEntity)) return;
        ItemStack item = p.getEquipment().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) return;
        if (item.getItemMeta().getDisplayName() == null) return;
        Player target = (Player) e.getRightClicked();
        if (item.getItemMeta().getDisplayName().equals(CC.translate((target.getName() + "'s &6&lInventory")))) {
            e.getPlayer().performCommand("invsee " + target.getDisplayName());
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (StaffMode.STAFF.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }


}
