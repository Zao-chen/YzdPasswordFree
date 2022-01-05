package yzdpasswordfree.yzdpasswordfree;

import fr.xephi.authme.libs.org.bstats.bukkit.Metrics;
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

        /*Bstats的接入（可以在bstats文件夹关闭）*/
        int pluginId = 13816;
        Metrics metrics = new Metrics(this, pluginId);

        Logger logger = this.getLogger();

        /*指令注册*/
        Objects.requireNonNull(getCommand("yzdpasswordfree")).setExecutor(new maincommand());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) //对于papi强依赖
        {
            /*一些注册*/
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
