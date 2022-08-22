package me.koz.smpcore.backpack;

import lombok.Getter;
import me.koz.smpcore.utils.BukkitSerialization;
import me.koz.smpcore.utils.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class BackpackHandler {

    @Getter
    public static HashMap<UUID, Backpack> backpacks;

    public BackpackHandler() {
        backpacks = new HashMap<>();
    }


    public static void openBackpack(Player player, Backpack backpack) {
        Main instance = Main.getInstance();
        Player owner = Bukkit.getPlayer(backpack.getOwner());
        int size = instance.getBackpackConfig().getInt("default-slots");
        for (String key : Objects.requireNonNull(instance.getBackpackConfig().getConfigurationSection("slots")).getKeys(false)) {
            assert owner != null;
            if(owner.hasPermission(key.replace("_", ".")))
                size = instance.getBackpackConfig().getInt("slots." + key);

        }
        backpack.setSize(size);
        Inventory inventory = Bukkit.createInventory(null, size, CC.translate("&6&lBackpack"));
        inventory.setContents(backpack.getItems());
        player.openInventory(inventory);
    }

    public static void saveBackpack(Backpack backpack) {
        Main instance = Main.getInstance();
        for(int i = 0; i < backpack.getItems().length; i++){
            instance.getDataConfig().set("backpacks." + backpack.getOwner().toString()+".items.item"+i, backpack.getItems()[i]);
        }
        instance.getDataConfig().save();
    }

    public static void loadBackpack(UUID uuid) {
        Main instance = Main.getInstance();
        Backpack backpack = new Backpack(uuid);
        ItemStack[] items = new ItemStack[54];
        for(int i = 0; i < 54; i++){
            try {
                items[i] = instance.getDataConfig().getItemStack("backpacks." + backpack.getOwner().toString()+".items.item"+i);
            } catch (Exception e){}
        }
        backpack.setItems(items);
        backpacks.put(uuid, backpack);
    }

    public static void loadAllBackpacks() {
        Main instance = Main.getInstance();
        if(instance.getDataConfig().getConfigurationSection("backpacks") != null){
            for(String key : instance.getDataConfig().getConfigurationSection("backpacks").getKeys(false)){
                loadBackpack(UUID.fromString(key));
            }
        }
    }

}
