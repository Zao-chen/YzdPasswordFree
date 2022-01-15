package yzdpasswordfree.yzdpasswordfree.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import yzdpasswordfree.yzdpasswordfree.YzdPasswordFree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * 插件的主指令
 */
public class maincommand implements CommandExecutor , TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*获取文件*/
        Plugin config = YzdPasswordFree.getProvidingPlugin(YzdPasswordFree.class);
        File setting = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"setting.yml");
        FileConfiguration setting_f = YamlConfiguration.loadConfiguration(setting);
        File la ;
        FileConfiguration la_f;
        if(Objects.equals(setting_f.getString("language"), "Chinese")) //匹配语言进行获取
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-Chinese.yml");
        }
        else
        {
            la = new File(YzdPasswordFree.getPlugin(YzdPasswordFree.class).getDataFolder(),"language-English.yml");
        }
        la_f = YamlConfiguration.loadConfiguration(la);

        if(args.length==0) //判断长度
        {
            sender.sendMessage("§e[YPF]§ause /ypf help to find help(使用/ypf help获取帮助)");
        }
        else if(args.length==1)
        {
            if(args[0].equals("help")) //帮助
            {
                sender.sendMessage("§e[YPF]§a/ypf help - get help(获取帮助)");
                sender.sendMessage("§e[YPF]§a/ypf reload - reload plugin(重载插件)");
                sender.sendMessage("§e[YPF]§a/ypf type - Modify authentication mode(修改认证模式)");
                sender.sendMessage("§e[YPF]§a/ypf settp [ip] - Manually set ip(手动设置ip)");
            }
            else if (args[0].equals("type")) //更改模式
            {
                /*改为手动模式*/
                if(Objects.equals(config.getConfig().getString(sender.getName() + "_type"), "auto"))
                {
                    config.getConfig().set(sender.getName()+"_type","manual");
                    sender.sendMessage("§e[YPF]§a"+la_f.getString("change_mode_to_manual"));
                }
                /*改为自动模式*/
                else if(Objects.equals(config.getConfig().getString(sender.getName() + "_type"), "no"))
                {
                    config.getConfig().set(sender.getName()+"_type","auto");
                    sender.sendMessage("§e[YPF]§a"+la_f.getString("change_mode_to_auto"));
                }
                /*关闭自动登录*/
                else
                {
                    config.getConfig().set(sender.getName()+"_type","no");
                    sender.sendMessage("§e[YPF]§a"+la_f.getString("change_mode_to_no"));
                }
                config.saveConfig();
                config.reloadConfig();
            }
            else if(args[0].equals("reload")) //重载
            {
                if(sender.hasPermission("yzdpasswordfree.commands.reload")) //检测是否有权限重载
                {
                    config.reloadConfig();
                    sender.sendMessage("§e[YPF]§areload successfully(重载成功)");
                }
                else
                {
                    sender.sendMessage("§e[YPF]§cno permission(你没有权限)");
                }
            }
            else
            {
                sender.sendMessage("§e[YPF]§cUnknown syntax, use /ypf help to get help(未知语法，使用/ypf help获取帮助)");
            }
        }
        else if(args.length==2)
        {
            if(args[0].equals("setip")) //设置ip
            {
                config.getConfig().set(sender.getName(), args[1]); //保存ip
                config.saveConfig();
                config.reloadConfig();
                config.reloadConfig();
                sender.sendMessage("§e[YPF]§a"+la_f.getString("change_ip"));
            }
            else
            {
                sender.sendMessage("§e[YPF]§cUnknown syntax, use /ypf help to get help(未知语法，使用/ypf help获取帮助)");
            }
        }
        else
        {
            sender.sendMessage("§e[YPF]§cUnknown syntax, use /ypf help to get help(未知语法，使用/ypf help获取帮助)");
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
            list.add("reload");
            list.add("setip");
            return list;
        }
        return null;
    }
}
