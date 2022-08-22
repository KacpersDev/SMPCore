package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeartItem implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("beach.lifesteal")) {
            if (args.length < 3) {
                sender.sendMessage(CC.translate("&cUsage: /hearts give <player> <amount>"));
                return true;
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    int amount = Integer.parseInt(args[2]);
                    for (int i = 0; i < 35; i++) {
                        if (target.getInventory().getItem(i) == null) {
                            ItemStack heart = Main.getInstance().heart.clone();
                            heart.setAmount(amount);
                            target.getInventory().setItem(i, heart);
                            target.sendMessage(CC.translate("&cYou have received a heart(s)."));
                            sender.sendMessage(CC.translate("&eYou have given " + target.getName() + " &ea heart(s)."));
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }
}




