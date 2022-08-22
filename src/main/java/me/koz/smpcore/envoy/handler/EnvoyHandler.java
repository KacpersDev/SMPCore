package me.koz.smpcore.envoy.handler;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import me.koz.smpcore.envoy.Envoy;
import me.koz.smpcore.envoy.task.EnvoyTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Getter
public class EnvoyHandler implements CommandExecutor, Listener {

    private final Main main;
    private final EnvoyTask envoyTask;
    private final Envoy envoy;
    public static ArrayList<UUID> edit = new ArrayList<>();

    public EnvoyHandler(Main main) {
        this.main = main;
        this.envoyTask = new EnvoyTask();
        this.envoy = new Envoy(Main.getInstance());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Timer " + this.envoyTask.getTimer());
        } else if (args[0].equalsIgnoreCase("additem")) {
            if (args.length == 1) {
                wrongUsage(player);
            } else {
                int amount = Integer.parseInt(args[1]);
                ItemStack item = player.getInventory().getItemInMainHand();
                this.envoy.addItem(item, amount);
            }
        } else if (args[0].equalsIgnoreCase("start")) {
            this.envoyTask.startEvent();
        } else if (args[0].equalsIgnoreCase("stop")) {
            this.envoyTask.stopEvent();
        } else if (args[0].equalsIgnoreCase("edit")) {
            if (!edit.contains(player.getUniqueId())) {
                edit.add(player.getUniqueId());
                this.envoy.editMode(player);
                player.sendMessage("Enabled");
            } else {
                edit.remove(player.getUniqueId());
                this.envoy.removeEditMode();
                player.sendMessage("Disabled");
            }
        } else if (args[0].equalsIgnoreCase("flare")) {
            if (args.length == 1) {
                wrongUsage(player);
            } else if (args[1].equalsIgnoreCase("give")) {
                if (args.length == 2) {
                    wrongUsage(player);
                } else {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (args.length == 3) {
                        wrongUsage(player);
                    } else {
                        int amount = Integer.parseInt(args[3]);
                        this.envoy.giveFlare(target, amount);
                    }
                }
            }
        } else {
            wrongUsage(player);
        }
        return true;
    }

    private void wrongUsage(Player player){
        for (final String i : this.main.getConfig()
                .getStringList("Envoy.wrong-usage")) {
            player.sendMessage(CC.translate(i));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        if (!edit.contains(player.getUniqueId())) return;

        if (Objects.requireNonNull(player.getInventory().getItemInMainHand()
                .getItemMeta()).getDisplayName().equalsIgnoreCase(CC.translate(this.main
                .getConfig().getString("Envoy.edit.name")))) {
            Location location = event.getBlock().getLocation();
            int current = this.main.getChest().getInt("chests");
            this.envoy.addChest(location, current);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        if (!edit.contains(player.getUniqueId())) return;
        if (Objects.requireNonNull(player.getInventory().getItemInMainHand()
                .getItemMeta()).getDisplayName().equalsIgnoreCase(CC.translate(this.main
                .getConfig().getString("Envoy.edit.name")))) {

            this.envoy.removeChest(event.getBlock().getLocation());
        }
    }
}
