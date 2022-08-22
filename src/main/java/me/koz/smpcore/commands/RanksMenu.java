package me.koz.smpcore.commands;

import me.koz.smpcore.utils.CC;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RanksMenu implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&6&lRanks Menu"));

        ItemStack glasspane = new ItemStack(Material.CYAN_STAINED_GLASS_PANE, 1, (short)0);
        int slot;
        while ((slot = inv.firstEmpty()) != -1) inv.setItem(slot, glasspane);

        for (String m : Main.getInstance().getConfig().getConfigurationSection("Ranks-Menu.Ranks-Info").getKeys(false)) {
            Material material = Material.getMaterial(Main.getInstance().getConfig().getString("Ranks-Menu.Ranks-Info." + m + ".Item"));
            if(material != null){
                ItemStack RankInfo = new ItemStack(material,
                        1, (short)Main.getInstance().getConfig().getInt("Ranks-Menu.Ranks-Info." + m + ".Data"));
                ItemMeta itemmeta = RankInfo.getItemMeta();
                itemmeta.setDisplayName(
                        CC.translate(Main.getInstance().getConfig().getString("Ranks-Menu.Ranks-Info." + m + ".Name")));
                List<String> ItemLore = new ArrayList<>();
                Iterator<String> iterator = Main.getInstance().getConfig().getStringList("Ranks-Menu.Ranks-Info." + m + ".Lore").iterator();
                while (iterator.hasNext()) {
                    String FifthItemLoreL = iterator.next();
                    ItemLore.add(ChatColor.translateAlternateColorCodes('&', FifthItemLoreL));
                }
                itemmeta.setLore(ItemLore);
                RankInfo.setItemMeta(itemmeta);
                inv.setItem(Main.getInstance().getConfig().getInt("Ranks-Menu.Ranks-Info." + m + ".Slot") - 1, RankInfo);
                ((HumanEntity)sender).openInventory(inv);
            }
        }


        Player p = (Player) sender;
        p.openInventory(inv);
        return true;
    }


}



