package yzdpasswordfree.yzdpasswordfree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.util.ArrayList;
import java.util.List;

public class maincommand implements CommandExecutor , TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        if(args.length==0)
        {
            player.sendMessage("§e[YPF]§aYzdPasswordFree插件,使用/yzdpasswordfree help获取帮助");
        }
        else if(args.length==1)
        {
            if(args[0].equals("help"))
            {
                player.sendMessage("§e[YPF]§a/yzdpasswordfree help - 获取帮助");
                player.sendMessage("§e[YPF]§a/yzdpasswordfree reload - 重载插件");
                player.sendMessage("§e[YPF]§a/yzdpasswordfree type - 修改认证模式");
            }
            else if (args[0].equals("type"))
            {
                if(config.getConfig().getString(player.getName()+"_type").equals("auto"))
                {
                    config.getConfig().set(player.getName()+"_type","manual");
                    config.saveConfig();
                    config.reloadConfig();
                    player.sendMessage("§e[YPF]§a已切换为 手动认证 模式");
                }
                else
                {
                    config.getConfig().set(player.getName()+"_type","auto");
                    config.saveConfig();
                    config.reloadConfig();
                    player.sendMessage("§e[YPF]§a已切换为 自动认证 模式");
                }
            }
            else if(args[0].equals("reload"))
            {

                config.reloadConfig();
            }
            else
            {
                player.sendMessage("§e[YPF]§c未知语法，使用/yzdpasswordfree help获取帮助");
            }
        }
        else
        {
            player.sendMessage("§e[YPF]§c未知语法，使用/yzdpasswordfree help获取帮助");
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sendermm, Command coand, String alias, String[] args) {
        if(args.length==1)
        {
            List<String> list = new ArrayList<>();
            list.add("intercept");
            list.add("reload");
            list.add("info");
            return list;
        }
        return null;
    }
}
