package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class PlayerTeleport implements CommandExecutor {

    private final ArrayList<Player> players = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("beach.staffmode"))) {
            sender.sendMessage(CC.translate("&cYou do not have access to this command."));
            return false;
        }
        Player p = (Player) sender;
        for (Player e : Bukkit.getOnlinePlayers()) players.add(e);
        Player randomPlayer = players.get(new Random().nextInt(players.size()));
        p.teleport(randomPlayer.getLocation());
        return true;
    }
}






