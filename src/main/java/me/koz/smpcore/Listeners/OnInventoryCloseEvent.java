package me.koz.smpcore.Listeners;

import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.backpack.BackpackHandler;
import me.koz.smpcore.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OnInventoryCloseEvent implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            Inventory topInventory = event.getView().getTopInventory();
            if (!event.getView().getTitle().equals(CC.translate("&6&lBackpack")))
                return;

            Backpack backpack = BackpackHandler.getBackpacks().get(player.getUniqueId());
            List<ItemStack> itemStacks = new ArrayList<>(List.of(topInventory.getContents()));
            for (int i = 0; i < itemStacks.size(); i++) {
                if (itemStacks.get(i) == null || itemStacks.get(i).getType() == Material.AIR) {
                    itemStacks.remove(i);
                }
                i--;
            }
            backpack.setItems(itemStacks.toArray(new ItemStack[0]));
            BackpackHandler.saveBackpack(backpack);
        }
    }
}