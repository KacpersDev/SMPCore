package me.koz.smpcore.perks.listener;

import lombok.Getter;
import me.koz.smpcore.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

@Getter
public class AutoSmeltListener implements Listener {

    private final Main main;

    public AutoSmeltListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        Player player = event.getPlayer();
        if (!player.hasPermission("beach.autosmelt")) return;
        Material block = event.getBlock().getType();
        if (block == Material.IRON_ORE || block == Material.DEEPSLATE_IRON_ORE) {
            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
            event.setDropItems(false);
        } else if (block == Material.DIAMOND_ORE || block == Material.DEEPSLATE_DIAMOND_ORE) {
            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.DIAMOND));
            event.setDropItems(false);
        } else if (block == Material.GOLD_ORE || block == Material.DEEPSLATE_GOLD_ORE) {
            event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
            event.setDropItems(false);
        }
    }
}
