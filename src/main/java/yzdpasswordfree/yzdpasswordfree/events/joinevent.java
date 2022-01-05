package yzdpasswordfree.yzdpasswordfree.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.clip.placeholderapi.PlaceholderAPI;
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
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        String nowip = "%player_ip%";
        nowip = PlaceholderAPI.setPlaceholders(player, nowip); //通过papi获取ip
        try { //检测有没有ip
            if(Objects.requireNonNull(config.getConfig().getString(player.getName())).isEmpty())
            {
                player.sendMessage("§e[YPF]§c你没有开启自动登录，当前ip："+nowip);
            }
        }
        catch (Exception e)
        {
            player.sendMessage("§e[YPF]§c你没有开启自动登录，当前ip："+nowip);
            config.getConfig().set(player.getName()+"_type","auto");
            config.saveConfig();
            config.reloadConfig();
        }

        //判断是否系统
        if(Objects.equals(config.getConfig().getString(player.getName()), nowip))
        {
            player.sendMessage("§e[YPF]§a已自动登录");
            File setting = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"setting.yml");
            FileConfiguration setting_f = YamlConfiguration.loadConfiguration(setting);
            if(setting_f.getBoolean("login_command.enable")) //判断是否开启指令
            {
                List<String> join_command = setting_f.getStringList("login_command.command");
                for(String list_to_string : join_command) //输出列表指令
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),list_to_string);
                }
            }
            AuthMeApi.getInstance().forceLogin(player);
        }
        else
        {
            player.sendMessage("§e[YPF]§c无法自动登录，当前ip："+nowip);
        }
    }
}