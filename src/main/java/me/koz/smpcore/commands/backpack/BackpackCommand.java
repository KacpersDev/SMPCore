package me.koz.smpcore.commands.backpack;

import lombok.Getter;
import me.koz.smpcore.CC;
import me.koz.smpcore.Main;
import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.backpack.BackpackUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Getter
public class BackpackCommand implements CommandExecutor {

    private final Main main;
    private Backpack backpack;
    public BackpackCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        this.backpack = new Backpack(this.main, player);
        this.backpack.open();

        return true;
    }
}
