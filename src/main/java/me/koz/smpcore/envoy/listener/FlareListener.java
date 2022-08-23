package me.koz.smpcore.envoy.listener;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import me.koz.smpcore.envoy.task.EnvoyTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class FlareListener implements Listener {

    private final Main main;
    private final EnvoyTask envoyTask = new EnvoyTask();

    public FlareListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getInventory().getItemInMainHand() == null
            || player.getInventory().getItemInMainHand().getItemMeta() == null ||
            player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) return;
            if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
                    .equalsIgnoreCase(CC.translate(this.main.getConfig()
                            .getString("Envoy.flare.name")))) {
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "envoy start");
            envoyTask.startEvent();
            new BukkitRunnable() {

                @Override
                public void run() {
                    removeItem(player);
                }
            }.runTaskLater(this.main, 10L);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                .getDisplayName().equalsIgnoreCase(CC.translate(this.main.getConfig().getString("Envoy.flare.name")))) {
            event.setCancelled(true);
        }
    }

    private void removeItem(Player player){

        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }
}
