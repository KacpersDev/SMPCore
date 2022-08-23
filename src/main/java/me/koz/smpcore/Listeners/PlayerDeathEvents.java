package me.koz.smpcore.Listeners;

import lombok.Getter;
import me.koz.smpcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@Getter
public class PlayerDeathEvents implements Listener {
    private final Main main;

    public PlayerDeathEvents(Main main) {
        this.main = main;
    }
    @EventHandler
    public void PDE(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < 3) {
            long time = this.main.getConfig().getLong("Deathbans.default");
            try {
                for (String str : this.main.getConfig().getConfigurationSection("Deathbans.permissions").getKeys(false)) {
                    if (p.hasPermission("Deathbans.permissions." + str.replace("_", ".")))
                        time = this.main.getConfig().getLong("Deathbans.permissions." + str);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + p.getName() + " " + time + "s Deathban");
        } else
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
    }
}
