package yzdpasswordfree.yzdpasswordfree;

import fr.xephi.authme.libs.org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import yzdpasswordfree.yzdpasswordfree.commands.maincommand;
import yzdpasswordfree.yzdpasswordfree.events.joinevent;
import yzdpasswordfree.yzdpasswordfree.events.loginevent;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class YzdPasswordFree extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger logger = this.getLogger();

        // Plugin startup logic
        /*检查更新*/
        new UpdateChecker(this, 98996).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version))
            {
                logger.info(ChatColor.YELLOW+"[YPF] "+ChatColor.GREEN+"The plugin is up to date(插件已是最新版):"+version);
            } else {
                    logger.info(ChatColor.YELLOW+"[YPF] "+ChatColor.GREEN+"New version found(发现新版):"+ version+ "\nhttps://www.spigotmc.org/resources/yzdpasswordfree-let-your-player-login-without-password.98996/\n(请前往更新插件");
                }}
        );

        /*这里注意还是获取语言*/
        File setting = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"setting.yml");
        FileConfiguration setting_f = YamlConfiguration.loadConfiguration(setting);

        /*Bstats的接入（可以在bstats文件夹关闭）*/
        int pluginId = 13816;
        Metrics metrics = new Metrics(this, pluginId);

        /*文件生成*/
        this.saveResource("setting.yml",false);
        this.saveResource("language-Chinese.yml",false);
        this.saveResource("language-English.yml",false);

        /*指令注册*/
        Objects.requireNonNull(getCommand("ypf")).setExecutor(new maincommand());
        Objects.requireNonNull(getCommand("ypf")).setTabCompleter(new maincommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) //对于papi强依赖
        {
            /*一些注册*/
            saveDefaultConfig();
            if(Objects.equals(setting_f.getString("language"), "Chinese"))
            {
                /*获取中文*/
                File zh = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
                FileConfiguration zh_f = YamlConfiguration.loadConfiguration(zh);
                logger.info(ChatColor.YELLOW+"[YPS] "+zh_f.getString("have_papi"));
                getServer().getPluginManager().registerEvents(new joinevent(),this);
                getServer().getPluginManager().registerEvents(new loginevent(),this);
            }
            else if(Objects.equals(setting_f.getString("language"), "English")){
                /*获取英文*/
                File en = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-English.yml");
                FileConfiguration en_f = YamlConfiguration.loadConfiguration(en);
                logger.info(ChatColor.YELLOW+"[YPS] "+en_f.getString("have_papi"));
                getServer().getPluginManager().registerEvents(new joinevent(),this);
                getServer().getPluginManager().registerEvents(new loginevent(),this);
            }
            else
            {
                logger.info(ChatColor.YELLOW+"[YPS] Language cannot be obtained, please check Setting.yml!");
            }
        }
        else //如果没有找到papi
        {
            if(Objects.equals(setting_f.getString("language"), "Chinese"))
            {
                /*获取中文*/
                File zh = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
                FileConfiguration zh_f = YamlConfiguration.loadConfiguration(zh);
                logger.info(ChatColor.YELLOW+"[YPS] "+zh_f.getString("no_papi"));
            }
            else if(Objects.equals(setting_f.getString("language"), "English")){
                /*获取英文*/
                File en = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-English.yml");
                FileConfiguration en_f = YamlConfiguration.loadConfiguration(en);
                logger.info(ChatColor.YELLOW+"[YPS] "+en_f.getString("no_papi"));
            }
            else
            {
                logger.info(ChatColor.YELLOW+"[YPS] Language cannot be obtained, please check Setting.yml!");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
