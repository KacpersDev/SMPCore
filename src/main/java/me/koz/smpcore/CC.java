package me.koz.smpcore;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;


public class CC {
    public static String translate(String input) {
        if (input == null)
            return "null";
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static List<String> translate(List<String> input) {
        return (List<String>)input.stream().map(CC::translate).collect(Collectors.toList());
    }
}


