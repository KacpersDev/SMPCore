package me.koz.smpcore.backpack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BackpackUser {

    private final HashMap<UUID, List<ItemStack>> backpacks = new HashMap<>();

    /*

    public void addIem(Player player, ItemStack item){
        if (!backpacks.containsKey(player.getUniqueId())) {
            List<ItemStack> items = new ArrayList<>();
            items.add(item);
            backpacks.put(player.getUniqueId(), items);
        } else {
            List<ItemStack> old = new ArrayList<>(backpacks.get(player.getUniqueId()));
            List<ItemStack> newValue = new ArrayList<>(backpacks.get(player.getUniqueId()));
            newValue.add(item);
            backpacks.replace(player.getUniqueId(), old, newValue);
        }
    }

    public void removeItem(Player player, ItemStack itemStack){
        List<ItemStack> old = new ArrayList<>(backpacks.get(player.getUniqueId()));
        List<ItemStack> newValue = new ArrayList<>(backpacks.get(player.getUniqueId()));
        newValue.remove(itemStack);
        backpacks.replace(player.getUniqueId(), old, newValue);
    }
     */

    public List<ItemStack> getItems(Player player) {
        return backpacks.get(player.getUniqueId());
    }

    public void clear(Player player){
        backpacks.get(player.getUniqueId()).clear();
    }

    public void append(Player player, List<ItemStack> items){
        backpacks.put(player.getUniqueId(), items);
    }

    public int getSize(Player player){

        if (player.hasPermission("backpack.18")) {
            return 18;
        } else if (player.hasPermission("backup.27")) {
            return 27;
        } else if (player.hasPermission("backpack.36")) {
            return 36;
        } else if (player.hasPermission("backpack.45")) {
            return 45;
        } else if (player.hasPermission("backpack.54")) {
            return 54;
        }
        return 9;
    }
}
