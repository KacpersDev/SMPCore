package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import me.koz.smpcore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffChat implements CommandExecutor {

    public Main plugin;

    public StaffChat(Main pl) {
        this.plugin = pl;
    }

    public static ArrayList<Player> Insc = new ArrayList<Player>();

    String prefix = CC.translate("&b[&aSC&b]");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&c&lYou must be a player to use this command."));
            return true;
        }

        Player p = (Player) sender;

        if(args.length == 0) {
            if(!(p.hasPermission("beach.staffchat"))) {
                p.sendMessage(CC.translate("&c&lYou do not have permission to use this command."));
                return true;
            }
            if(Insc.contains(p)) {
                Insc.remove(p);
                p.sendMessage(CC.translate("&cStaff Chat disabled."));
                return true;
            } else {
                Insc.add(p);
                p.sendMessage(CC.translate("&aStaff Chat enabled."));
            }


        }


        return false;
    }
}
