package yzdpasswordfree.yzdpasswordfree.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * 插件的主指令
 */
public class maincommand implements CommandExecutor , TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) //对于papi强依赖
        {
           sender.sendMessage(ChatColor.YELLOW+"[YPS] "+ChatColor.GREEN+"PlaceholderAPI cannot be found, PlaceholderAPI feature has been automatically disabled,plugin can't work(papi未接入,无法加载插件)");
           return false;
        }

        if(args.length==0) //判断长度
        {
            player.sendMessage("§e[YPF]§aYzdPasswordFree插件,使用/yzdpasswordfree help获取帮助");
        }
        else if(args.length==1)
        {
            if(args[0].equals("help")) //判断子指令
            {
                player.sendMessage("§e[YPF]§a/yzdpasswordfree help - 获取帮助");
                player.sendMessage("§e[YPF]§a/yzdpasswordfree reload - 重载插件");
                player.sendMessage("§e[YPF]§a/yzdpasswordfree type - 修改认证模式");
            }
            else if (args[0].equals("type"))
            {
                if(Objects.equals(config.getConfig().getString(player.getName() + "_type"), "auto"))
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
                player.sendMessage("§e[YPF]§a重载成功");
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

    /*
     *子指令的tab补全
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1)
        {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("type");
            list.add("info");
            return list;
        }
        return null;
    }
}
