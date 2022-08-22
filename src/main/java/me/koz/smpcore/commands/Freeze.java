package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Freeze implements CommandExecutor {


    public static ArrayList<UUID> FROZEN = new ArrayList<UUID>();
    public HashMap<Player, ItemStack[]> invsave = new HashMap<Player, ItemStack[]>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("beach.staffmode")) {
            if (args.length == 0) {
                sender.sendMessage(CC.translate("&cUsage: /freeze <player>"));
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(CC.translate("&cThis player isn't online."));
            } else {
                if (!FROZEN.contains(target.getUniqueId())) {
                    invsave.put(target, target.getInventory().getContents());
                    FROZEN.add(target.getUniqueId());
                    target.sendMessage(CC.translate("&f█████████"));
                    target.sendMessage(CC.translate("&f████&c█&f████"));
                    target.sendMessage(CC.translate("&f███&c█&0█&c█&f███"));
                    target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██"));
                    target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██ &eYou have been frozen by a staff member."));
                    target.sendMessage(CC.translate("&f██&c█&6█&0█&6█&c█&f██ &eIf you disconnect you will be &4&lBANNED&e."));
                    target.sendMessage(CC.translate("&f█&c█&6█████&c█&f█ &ePlease connect to our TeamSpeak."));
                    target.sendMessage(CC.translate("&c█&6███&0█&6███&c█ &7ts.smp.com"));
                    target.sendMessage(CC.translate("&c█████████"));
                    target.sendMessage(CC.translate("&f█████████"));

                    sender.sendMessage(CC.translate("&aYou have frozen " + target.getName() + "&2."));
                    return true;
                } else {
                    FROZEN.remove(target.getUniqueId());
                    target.getInventory().setContents(invsave.get(target));
                    target.sendMessage(CC.translate("&a&lYou have been unfrozen."));
                    sender.sendMessage(CC.translate("&aYou have unfrozen " + target.getName() + "&2."));
                }


            }
        }

        return false;
    }
}

