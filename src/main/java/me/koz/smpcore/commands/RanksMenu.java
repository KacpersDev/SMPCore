package me.koz.smpcore.commands;

import lombok.Getter;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class RanksMenu implements CommandExecutor {

    private final Main main;
    private RanksMenu(Main main){
        this.main = main;
    }
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&6&lRanks Menu"));

        ItemStack glasspane = new ItemStack(Material.CYAN_STAINED_GLASS_PANE, 1, (short)0);
        int slot;
        while ((slot = inv.firstEmpty()) != -1) inv.setItem(slot, glasspane);

        for (String m : this.main.getConfig().getConfigurationSection("Ranks-Menu.Ranks-Info").getKeys(false)) {
            Material material = Material.getMaterial(this.main.getConfig().getString("Ranks-Menu.Ranks-Info." + m + ".Item"));
            if(material != null){
                ItemStack RankInfo = new ItemStack(material,
                        1, (short)this.main.getConfig().getInt("Ranks-Menu.Ranks-Info." + m + ".Data"));
                ItemMeta itemmeta = RankInfo.getItemMeta();
                itemmeta.setDisplayName(
                        CC.translate(this.main.getConfig().getString("Ranks-Menu.Ranks-Info." + m + ".Name")));
                List<String> ItemLore = new ArrayList<>();
                Iterator<String> iterator = this.main.getConfig().getStringList("Ranks-Menu.Ranks-Info." + m + ".Lore").iterator();
                while (iterator.hasNext()) {
                    String FifthItemLoreL = iterator.next();
                    ItemLore.add(ChatColor.translateAlternateColorCodes('&', FifthItemLoreL));
                }
                itemmeta.setLore(ItemLore);
                RankInfo.setItemMeta(itemmeta);
                inv.setItem(this.main.getConfig().getInt("Ranks-Menu.Ranks-Info." + m + ".Slot") - 1, RankInfo);
                ((HumanEntity)sender).openInventory(inv);
            }
        }


        Player p = (Player) sender;
        p.openInventory(inv);
        return true;
    }


}



