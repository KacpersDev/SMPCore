package me.koz.smpcore.envoy;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class Envoy {

    private final Main main;

    public Envoy(Main main) {
        this.main = main;
    }

    public void giveFlare(Player target, int amount) {

    }

    public void editMode(Player player){

        ItemStack item = new ItemStack(Material.valueOf(Main.getInstance()
                .getConfig().getString("Envoy.edit.item")));
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(CC.translate(Main.getInstance().getConfig().getString("Envoy.edit.name")));
            ArrayList<String> lore = new ArrayList<>();
            for (final String i : Main.getInstance().getConfig().getStringList("Envoy.edit.lore")) {
                lore.add(CC.translate(i));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        player.getInventory().setItemInMainHand(item);
    }

    public void removeEditMode(){
        if (this.main.getChest().getString("Chest." + 0 +".world") == null) return;

        for (int i = 0; i < this.main.getChest().getInt("chests"); i++) {
            Location location =
                    new Location(Bukkit.getWorld("World"),
                            this.main.getChest().getInt("Chest." + i + ".x"),
                            this.main.getChest().getInt("Chest." + i + ".y"),
                            this.main.getChest().getInt("Chest." + i + ".z"));
            location.getBlock().setType(Material.AIR);
        }
    }

    public void addChest(Location location, int slot){
        if (this.main.getChest().getString("chests") == null) {
            this.main.getChest().set("chests", 0);
            this.main.getChest().save();
        }

        this.main.getChest().set("Chest." + slot + ".x", location.getX());
        this.main.getChest().set("Chest." + slot + ".y", location.getY());
        this.main.getChest().set("Chest." + slot + ".z", location.getZ());
        this.main.getChest().set("Chest." + slot + ".world", Objects.requireNonNull(location.getWorld()).getName());
        this.main.getChest().set("chests", (this.main.getChest().getInt("chests") + 1));
        this.main.getChest().save();
    }

    public void removeChest(Location location) {
        this.main.getChest().set("Chest." + getChestByLocation(location), null);
        this.main.getChest().save();
    }

    public int getChestByLocation(Location location){

        for (int i = 0; i < this.main.getChest().getInt("chests"); i++) {
            if (this.main.getChest().getInt("Chest." + i + ".x") == location.getX()
            && this.main.getChest().getInt("Chest." + i + ".y") == location.getY()
            && this.main.getChest().getInt("Chest." + i + ".z") == location.getZ()) {
                return i;
            }
        }
        return 0;
    }
}
