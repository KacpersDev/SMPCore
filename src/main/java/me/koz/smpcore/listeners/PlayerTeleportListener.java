package me.koz.smpcore.listeners;

import me.koz.smpcore.CC;
import me.koz.smpcore.commands.StaffMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerTeleportListener implements Listener {

    boolean sleep = false;

    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = p.getEquipment().getItemInMainHand().clone();
            if (item.getType() == Material.RED_DYE) {
                if (item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta.hasLore()) {
                        ArrayList<String> lore = new ArrayList<>(meta.getLore());
                        if (lore.get(0).equals("\u00a77Use to get one more heart on the Health bar.")) {
                            if (item.getAmount() > 1) {
                                item.setAmount(item.getAmount() - 1);
                                p.getEquipment().setItemInMainHand(item);
                            } else {
                                p.getEquipment().setItemInMainHand(null);
                            }
                            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
                            p.setHealth(p.getHealth() + 2);
                        }
                    }
                }
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(sleep) {
                sleep = false;
                ItemStack item = p.getEquipment().getItemInMainHand().clone();
                if (item.getType() == Material.RED_DYE) {
                    if (item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasLore()) {
                            ArrayList<String> lore = new ArrayList<>(meta.getLore());
                            if (lore.get(0).equals("\u00a77Use to get one more heart on the Health bar.")) {
                                if (item.getAmount() > 1) {
                                    item.setAmount(item.getAmount() - 1);
                                    p.getEquipment().setItemInMainHand(item);
                                } else {
                                    p.getEquipment().setItemInMainHand(null);
                                }
                                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);
                                p.setHealth(p.getHealth() + 2);
                            }
                        }
                    }
                }
            } else sleep = true;
        }
        if (!(StaffMode.STAFF.contains(e.getPlayer().getUniqueId()))) return;
        ItemStack item = p.getEquipment().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) return;
        if (item.getItemMeta().getDisplayName().equals(CC.translate("&6&lRandom Player Teleport"))) {
            e.getPlayer().performCommand("ptp");
        }


    }
}