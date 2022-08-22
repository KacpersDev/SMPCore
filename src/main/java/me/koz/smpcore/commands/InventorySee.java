package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class InventorySee implements CommandExecutor {

    public static ArrayList<UUID> InvTarget = new ArrayList<UUID>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        Player p = (Player) sender;
        ItemStack applem = new ItemStack(Material.APPLE);
        ItemMeta applemeta = applem.getItemMeta();
        applemeta.setDisplayName(CC.translate("&cPlayer Health: " + "&6" + p.getHealth() + "&7/" + "&6" + p.getMaxHealth()));
        applem.setItemMeta(applemeta);

        ItemStack carrotm = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta carrotmeta = carrotm.getItemMeta();
        carrotmeta.setDisplayName(CC.translate("&ePlayer Ping: " + "&d"));
        carrotm.setItemMeta(carrotmeta);

        Player target = Bukkit.getServer().getPlayer(args[0]);
        Inventory inv = Bukkit.createInventory(target, 45, CC.translate(target.getName() + "'s &6&lInventory"));
        inv.setContents(target.getInventory().getContents());
        inv.setItem(44, applem);
        inv.setItem(43, carrotm);


        if (!(sender.hasPermission("beach.staffmode"))) {
            sender.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return true;
        }
                p.openInventory(inv);
                p.sendMessage(CC.translate("&aOpening &b" + target.getName() + "&a's inventory..."));
        return true;
    }

        }





