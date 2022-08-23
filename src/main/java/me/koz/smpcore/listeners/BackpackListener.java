package me.koz.smpcore.listeners;

import lombok.Getter;
import me.koz.smpcore.Main;
import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.backpack.BackpackUser;
import me.koz.smpcore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BackpackListener implements Listener {

    private final Main main;
    private Backpack backpack;
    private final BackpackUser backpackUser = new BackpackUser();

    public BackpackListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        this.backpack = new Backpack(this.main, player);
        if (!this.backpack.exists()) {
            this.backpack.create();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        this.backpack = new Backpack(this.main, player);
        this.backpack.saveBackpack();
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            Inventory topInventory = event.getView().getTopInventory();
            if (!event.getView().getTitle().equals(CC.translate(this.main.getConfig()
                    .getString("inventory-name"))))
                return;

            List<ItemStack> i = new ArrayList<>();
            ItemStack[] items = topInventory.getContents();
            Collections.addAll(i, items);
            this.backpackUser.clear(player);
            this.backpackUser.append(player, i);
        }
    }
}
