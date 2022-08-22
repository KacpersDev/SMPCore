package me.koz.smpcore.commands;


import de.myzelyam.api.vanish.VanishAPI;
import me.koz.smpcore.utils.CC;
import me.koz.smpcore.Main;
import me.koz.smpcore.TeamAction;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StaffMode implements CommandExecutor {

    public HashMap<Player, ItemStack[]> invsave = new HashMap<Player, ItemStack[]>();
   public static ArrayList<UUID> STAFF = new ArrayList<UUID>();

    String prefix = CC.translate("&b[&aS&b]");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        ItemStack compassm = new ItemStack(Material.COMPASS);
        ItemMeta compassmeta = compassm.getItemMeta();
        compassmeta.setDisplayName(CC.translate("&a&lCompass"));
        compassm.setItemMeta(compassmeta);

        ItemStack freezem = new ItemStack(Material.PACKED_ICE);
        ItemMeta icemeta = freezem.getItemMeta();
        compassmeta.setDisplayName(CC.translate("&e&lFreeze"));
        freezem.setItemMeta(compassmeta);

        ItemStack blazerodm = new ItemStack(Material.BLAZE_ROD);
        ItemMeta blazemeta = blazerodm.getItemMeta();
        blazemeta.setDisplayName(CC.translate("&6&lRandom Player Teleport"));
        blazerodm.setItemMeta(blazemeta);

        ItemStack bookm = new ItemStack(Material.BOOK);
        ItemMeta bookmeta = bookm.getItemMeta();
        bookmeta.setDisplayName(CC.translate("&d&lInventory Viewer"));
        bookm.setItemMeta(bookmeta);

        Player p = (Player) sender;

        if (!(p.hasPermission("beach.staffmode"))) {
            p.sendMessage(CC.translate("&cYou do not have permission to use this command."));
            return true;
        }

        if (!STAFF.contains(p.getUniqueId())) {
            invsave.put(p, p.getInventory().getContents());
            STAFF.add(p.getUniqueId());
            p.getInventory().clear();
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            VanishAPI.hidePlayer(p);
            p.getInventory().setBoots(null);
            p.getInventory().addItem(compassm);
            p.getInventory().setItem(1, new ItemStack(freezem));
            p.getInventory().setItem(2, new ItemStack(bookm));
            p.getInventory().setItem(8, new ItemStack(blazerodm));
            NametagChanger.changePlayerName(p, Main.getInstance().getConfig().getString("Prefix"), TeamAction.CREATE);
            p.setGameMode(GameMode.CREATIVE);
            p.setFlying(true);
            p.sendMessage(CC.translate(prefix + " &bStaff mode enabled."));
            return true;
        } else {
            p.getInventory().setContents(invsave.get(p));
            STAFF.remove(p.getUniqueId());
            VanishAPI.showPlayer(p);
            NametagChanger.changePlayerName(p, "", TeamAction.UPDATE);
            p.setGameMode(GameMode.SURVIVAL);
            p.setFlying(false);
            p.sendMessage(CC.translate(prefix + " &bStaff mode disabled."));
        }
        return true;


      }
    }


