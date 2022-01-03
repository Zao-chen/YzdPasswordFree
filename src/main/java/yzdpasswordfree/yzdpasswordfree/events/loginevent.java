package yzdpasswordfree.yzdpasswordfree.events;

import fr.xephi.authme.events.LoginEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.util.Objects;

public class loginevent implements Listener {
    @EventHandler
    public void joins(LoginEvent event) {
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        Player player = event.getPlayer();
        if(Objects.equals(config.getConfig().getString(event.getPlayer().getName() + "_type"), "auto")) {
            String nowip = "%player_ip%";
            nowip = PlaceholderAPI.setPlaceholders(player, nowip);
            config.getConfig().set(event.getPlayer().getName(), nowip);
            config.saveConfig();
            config.reloadConfig();
            player.sendMessage("§e[YPF]§a已记录ip，下次会自动登录，当前ip：" + nowip);
        }
        else
        {
            player.sendMessage("§e[YPF]§a当前为手动模式，ip不会被记录");
        }
    }
}