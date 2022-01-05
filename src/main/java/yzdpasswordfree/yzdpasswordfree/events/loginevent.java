package yzdpasswordfree.yzdpasswordfree.events;

import fr.xephi.authme.events.LoginEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.util.Objects;

/*
 * 检测玩家成功登录
 * 也就是会刷新ip
*/
public class loginevent implements Listener {
    @EventHandler
    public void joins(LoginEvent event) {
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        Player player = event.getPlayer();

        /*如果开启了自动模式*/
        if(Objects.equals(config.getConfig().getString(event.getPlayer().getName() + "_type"), "auto")) {
            String nowip = "%player_ip%";
            nowip = PlaceholderAPI.setPlaceholders(player, nowip); //通过papi获取ip
            config.getConfig().set(event.getPlayer().getName(), nowip); //报错ip
            config.saveConfig();
            config.reloadConfig();
            player.sendMessage("§e[YPF]§a已记录ip，下次会自动登录，当前ip：" + nowip);
        }
        else //如果没有开启
        {
            player.sendMessage("§e[YPF]§a当前为手动模式，ip不会被记录");
        }
    }
}