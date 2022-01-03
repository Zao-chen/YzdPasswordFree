package yzdpasswordfree.yzdpasswordfree;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import yzdpasswordfree.yzdpasswordfree.commands.maincommand;
import yzdpasswordfree.yzdpasswordfree.events.joinevent;
import yzdpasswordfree.yzdpasswordfree.events.loginevent;

import java.util.Objects;
import java.util.logging.Logger;

public final class YzdPasswordFree extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        //Objects.requireNonNull(getCommand("ypf")).setExecutor(new maincommand());
        Objects.requireNonNull(getCommand("yzdpasswordfree")).setExecutor(new maincommand());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            saveDefaultConfig();
            logger.info(ChatColor.YELLOW+"[YPS] "+ChatColor.GREEN+"PlaceholderAPI function has been accessed(papi已接入)");
            getServer().getPluginManager().registerEvents(new joinevent(),this);
            getServer().getPluginManager().registerEvents(new loginevent(),this);
            this.saveResource("setting.yml",false);
        }
        else
        {
            logger.info(ChatColor.YELLOW+"[YPS] "+ChatColor.GREEN+"PlaceholderAPI cannot be found, PlaceholderAPI feature has been automatically disabled,plugin can't work(papi未接入,无法加载插件)");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
