package yzdpasswordfree.yzdpasswordfree.events;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.io.File;
import java.util.Objects;

/*
 * 检测玩家成功登录
 * 也就是会刷新ip
*/
public class loginevent implements Listener {
    @EventHandler
    public void joins(LoginEvent event) {
        /*获取文件*/
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        File setting = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"setting.yml");
        FileConfiguration setting_f = YamlConfiguration.loadConfiguration(setting);
        File la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
        FileConfiguration la_f = YamlConfiguration.loadConfiguration(la);
        if(Objects.equals(setting_f.getString("language"), "Chinese")) //匹配语言进行获取
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
        }
        else
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-English.yml");
        }
        la_f = YamlConfiguration.loadConfiguration(la);
        String head = la_f.getString("head"); //前缀
        Player player = event.getPlayer();

        /*如果开启了自动模式*/
        if(Objects.equals(config.getConfig().getString(event.getPlayer().getName() + "_type"), "auto")) {
            String nowip = "%player_ip%";
            nowip = player.getAddress().getHostString(); //获取ip
            config.getConfig().set(event.getPlayer().getName(), nowip); //保存ip
            config.saveConfig();
            config.reloadConfig();
            player.sendMessage(head+la_f.getString("record_ip")+ nowip);
        }
        else //如果没有开启
        {
            player.sendMessage(head+la_f.getString("manual_mode"));
        }
    }
}