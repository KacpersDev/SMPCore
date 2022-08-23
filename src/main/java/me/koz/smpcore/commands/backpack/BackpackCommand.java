package me.koz.smpcore.commands.backpack;

import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.backpack.BackpackHandler;
import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackpackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(!(sender instanceof Player player)) {
            sender.sendMessage(CC.translate("&cYou must be a player to use this command."));
            return false;
        }
        if(args.length == 0) {
            Backpack backpack = BackpackHandler.getBackpacks().get(player.getUniqueId());
            if(backpack == null)
                backpack = new Backpack(player.getUniqueId());
            BackpackHandler.getBackpacks().put(player.getUniqueId(), backpack);
            BackpackHandler.openBackpack(player, backpack);
            return true;
        }
        if(player.hasPermission("beach.backpackadmin")) {
            if(args.length >= 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    player.sendMessage(CC.translate("&cPlayer not found."));
                    return true;
                }
                Backpack backpack = BackpackHandler.getBackpacks().get(target.getUniqueId());
                if(backpack == null)
                    backpack = new Backpack(target.getUniqueId());
                BackpackHandler.getBackpacks().put(target.getUniqueId(), backpack);
                BackpackHandler.openBackpack(player, backpack);
                return true;
            }
            player.sendMessage(CC.translate("&cUsage: /backpack open <player>"));
            return true;
        }
        return true;
    }

}
