package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethealthEXE implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("beach.sethealth")){
            if(args.length > 1){
                Player p = Bukkit.getPlayer(args[0]);
                if(p != null){
                    try {
                        int health = Integer.parseInt(args[1]);
                        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                        p.setHealth(health);
                        sender.sendMessage("\u00afPlayer "+p.getName()+" has max " + health + " HP now.");
                    } catch (Exception e){ sender.sendMessage("\u00a4Incorrect number");}
                }
            }
        }
        return true;
    }
}
