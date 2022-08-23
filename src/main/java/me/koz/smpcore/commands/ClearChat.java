package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;
        if (!p.hasPermission("beach.staffmode")) {
            p.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return false;
        }
        if(!(sender instanceof Player)) {
            p.sendMessage(CC.translate("&cYou must be a player to use this command"));
        }
        for (int x = 0; x < 150; x++) {
            Bukkit.broadcastMessage("");
        }
            Bukkit.broadcastMessage(CC.translate("&7&l|-------------------+====+-------------------|")); {
            Bukkit.broadcastMessage(CC.translate("&6&lThe chat has been cleared by &c&l" + sender.getName()));
            Bukkit.broadcastMessage(CC.translate("&7&l|-------------------+====+-------------------|"));
            return true;
        }

    }
}
