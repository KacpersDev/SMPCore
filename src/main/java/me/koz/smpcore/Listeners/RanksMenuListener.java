package me.koz.smpcore.Listeners;

import me.koz.smpcore.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RanksMenuListener implements Listener {

    @EventHandler
    public void MainMenu(InventoryClickEvent e) {
        if (e.getView().getTitle().contains(CC.translate("&6&lRanks Menu"))) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
        }
    }

}

