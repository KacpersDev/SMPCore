package me.koz.smpcore;

import lombok.Getter;
import me.koz.smpcore.backpack.Backpack;
import me.koz.smpcore.commands.backpack.BackpackCommand;
import me.koz.smpcore.commands.*;
import me.koz.smpcore.envoy.handler.EnvoyHandler;
import me.koz.smpcore.envoy.task.ServerTask;
import me.koz.smpcore.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    public ItemStack heart = new ItemStack(Material.RED_DYE);
    private Config backpackConfig, dataConfig, loot, chest;

    private final ServerTask task = new ServerTask(this);
    private final Backpack backpack = new Backpack(this);

    void loadItems(){
        ItemMeta mHeart = heart.getItemMeta();
        mHeart.setDisplayName("\u00a7cHeart");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("\u00a77Use to get one more heart on the Health bar.");
        mHeart.setLore(lore);
        heart.setItemMeta(mHeart);
    }

    public void onEnable() {
        long duration = System.currentTimeMillis();
        instance = this;

        backpackConfig = new Config(instance, "backpack");
        dataConfig = new Config(instance, "data");
        loot = new Config(instance, "loot");
        chest = new Config(instance, "chest");

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();

        loadItems();
        heartCraft();


        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + "§6=== ENABLE START ===");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aLoading §dListeners");
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aLoading §dCommands");
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aMade by §dKoz");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== ENABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) ===");
        new ServerTask(this).runTaskTimer(this, 0L,20L);
    }

    void heartCraft(){
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(instance, "heart"), heart);

        r.shape("AAA", "ABA", "AAA");
        r.setIngredient('A', Material.NETHERITE_INGOT);
        r.setIngredient('B', Material.BEACON);

        try{
            getServer().addRecipe(r);
        } catch (Exception e){}
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.RanksMenuListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.StaffChatListener(this), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.StaffModeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.FreezeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.InvseeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.PlayerDeathEvents(this), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.OnPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.listeners.BackpackListener(this), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.envoy.handler.EnvoyHandler(this),this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.envoy.event.EnvoyEvent(this),this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.envoy.listener.FlareListener(this),this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.perks.listener.JellyLegsListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.perks.listener.AutoPickupListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.perks.listener.AutoSmeltListener(this), this);
    }

    private void registerCommands() {
        getCommand("ranks").setExecutor(new RanksMenu(this));
        getCommand("mod").setExecutor(new StaffMode());
        getCommand("sc").setExecutor(new StaffChat(this));
        getCommand("freeze").setExecutor(new Freeze());
        getCommand("ptp").setExecutor(new PlayerTeleport());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("invsee").setExecutor(new InventorySee());
        getCommand("sethearts").setExecutor(new SethealthEXE());
        getCommand("hearts").setExecutor(new HeartItem());
        getCommand("backpack").setExecutor(new BackpackCommand(this));
        getCommand("envoy").setExecutor(new EnvoyHandler(this));
    }

    public void onDisable() {
        this.backpack.saveAllBackpacks();
        instance = null;
        long duration = System.currentTimeMillis();
        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + "§6=== DISABLING ===");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dListeners");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dCommands");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aMade by §dKoz");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== DISABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) =");
    }

    public static Main getInstance() {
        return instance;
    }
}