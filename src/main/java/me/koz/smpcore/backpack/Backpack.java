package me.koz.smpcore.backpack;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Backpack {

    private final Main main;
    private Player player;
    private BackpackUser backpackUser;

    public Backpack(Main main, Player player) {
        this.main = main;
        this.player = player;
        this.backpackUser = new BackpackUser();
    }

    public Backpack(Main main) {
        this.main = main;
    }

    public void create(){
        this.main.getDataConfig().set("backpack." + player.getUniqueId() + ".items", 0);
        this.main.getDataConfig().set("backpack." + player.getUniqueId() + ".size", 9);
        this.main.getDataConfig().save();
    }

    public void open(){
        final Inventory inventory = Bukkit.createInventory(player, this.backpackUser.getSize(player),
                CC.translate(this.main.getBackpackConfig()
                        .getString("inventory-name")));
        List<ItemStack> items = backpackUser.getItems(player);
        for (ItemStack i : items) {
            inventory.addItem(i);
        }
        player.openInventory(inventory);
    }


    public void saveBackpack(){
        final List<ItemStack> items = this.backpackUser.getItems(player);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null) {
                this.main.getDataConfig().set("backpack." + player.getUniqueId() + ".item." + i, items.get(i));
                this.main.getDataConfig().save();
            }
        }
    }

    public void saveAllBackpacks(){
        for (final Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            final List<ItemStack> items = this.backpackUser.getItems(onlinePlayers);
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) != null) {
                    this.main.getDataConfig().set("backpack." + onlinePlayers.getUniqueId() + ".item." + i, items.get(i));
                    this.main.getDataConfig().save();
                }
            }
        }
    }

    public boolean exists(){
        return this.main.getDataConfig().contains("backpack." + player.getUniqueId());
    }
}
