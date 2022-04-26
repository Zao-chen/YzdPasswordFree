package yzdpasswordfree.yzdpasswordfree.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.io.File;
import java.util.List;
import java.util.Objects;

/*
 * 玩家进入时的事件
 * 主要就是检测ip是否相同
*/
public class joinevent implements Listener {
    @EventHandler
    public void joins(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        /*文件读取*/
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        File setting = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"setting.yml");
        FileConfiguration setting_f = YamlConfiguration.loadConfiguration(setting);
        File la;
        if(Objects.equals(setting_f.getString("language"), "Chinese")) //匹配语言进行获取
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
        }
        else
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-English.yml");
        }
        FileConfiguration la_f = YamlConfiguration.loadConfiguration(la);

        String head = la_f.getString("head");
        String nowip = Objects.requireNonNull(player.getAddress()).getHostString(); //获取ip
        try { //检测有没有ip
            if(Objects.requireNonNull(config.getConfig().getString(player.getName())).isEmpty())
            {
                player.sendMessage(head+la_f.getString("no_auto")+nowip);
            }
        }
        catch (Exception e)
        {
            player.sendMessage(head+la_f.getString("no_auto")+nowip);
            config.getConfig().set(player.getName()+"_type","auto");
            config.saveConfig();
            config.reloadConfig();
        }
        if(Objects.requireNonNull(config.getConfig().getString(player.getName() + "_type")).equals("no"))
        {
            player.sendMessage(head+la_f.getString("no_auto")+nowip); //如果没有开启自动登录
            return;
        }
        //判断是否相同
        if(Objects.equals(config.getConfig().getString(player.getName()), nowip))
        {
            player.sendMessage(head+la_f.getString("auto_login"));
            if(setting_f.getBoolean("login_command.enable")) //判断是否开启指令
            {
                List<String> join_command = setting_f.getStringList("login_command.command");
                for(String list_to_string : join_command) //输出列表指令
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),list_to_string.replace("{player}",player.getName()));
                }
            }
            AuthMeApi.getInstance().forceLogin(player);
        }
        else
        {
            player.sendMessage(head+la_f.getString("different_ip")+nowip);
        }
    }
}