package me.koz.smpcore;

import lombok.Getter;
import me.koz.smpcore.backpack.BackpackHandler;
import me.koz.smpcore.commands.backpack.BackpackCommand;
import me.koz.smpcore.commands.*;
import me.koz.smpcore.envoy.handler.EnvoyHandler;
import me.koz.smpcore.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.util.ArrayList;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    public ItemStack heart = new ItemStack(Material.RED_DYE);

    @Getter
    private Config backpackConfig, dataConfig, loot, chest;

    @Getter
    private BackpackHandler backpackHandler;

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

        backpackHandler = new BackpackHandler();

        BackpackHandler.loadAllBackpacks();

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();

        loadItems();
        heartCraft();

      //  runnable();

        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + "§6=== ENABLE START ===");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aLoading §dListeners");
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aLoading §dCommands");
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aMade by §dKoz");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== ENABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) ===");
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
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.RanksMenuListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.StaffChatListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.StaffModeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.FreezeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.InvseeListener(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.PlayerDeathEvents(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.OnPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.Listeners.OnInventoryCloseEvent(), this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.envoy.handler.EnvoyHandler(this),this);
        getServer().getPluginManager().registerEvents(new me.koz.smpcore.envoy.event.EnvoyEvent(this),this);
    }

    private void registerCommands() {
        getCommand("ranks").setExecutor(new RanksMenu());
        getCommand("mod").setExecutor(new StaffMode());
        getCommand("sc").setExecutor(new StaffChat(this));
        getCommand("freeze").setExecutor(new Freeze());
        getCommand("ptp").setExecutor(new PlayerTeleport());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("invsee").setExecutor(new InventorySee());
        getCommand("sethearts").setExecutor(new SethealthEXE());
        getCommand("hearts").setExecutor(new HeartItem());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("envoy").setExecutor(new EnvoyHandler(this));
    }

  //  public void runnable() {
     //       new BukkitRunnable() {
//
         //       public void run() {
        //            for (LivingEntity e : (getServer().getWorld("world")).getLivingEntities()) {
            //            e.setCustomName(e.getType() + "\u00a7c " + ((int)e.getHealth() + "\u00a7c♥"));
            //            e.setCustomNameVisible(true);
           //         }
          //      }

      //      }.runTaskTimer(this,5,20L);
     //   }

    public void onDisable() {
        long duration = System.currentTimeMillis();
        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + "§6=== DISABLING ===");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dListeners");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dCommands");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aMade by §dKoz");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== DISABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) =");
    }
}