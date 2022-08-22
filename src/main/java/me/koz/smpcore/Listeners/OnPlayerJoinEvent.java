package me.koz.smpcore.Listeners;

import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.backpack.BackpackHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(BackpackHandler.getBackpacks().containsKey(player.getUniqueId()))
            return;

        Backpack backpack = new Backpack(player.getUniqueId());

        BackpackHandler.getBackpacks().put(player.getUniqueId(), backpack);
    }

}
