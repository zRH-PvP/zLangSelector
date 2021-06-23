package plugins.zrhpvp.zlangselector.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.utils.Message;

public class ZLangSelectorCMD implements CommandExecutor {

    private final FileConfiguration messages;

    public ZLangSelectorCMD() {
        messages = ZLangSelector.getInstance().getFileManager().getMessages();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {

        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("zlangselector.reload")) {
                        ZLangSelector.getInstance().getFileManager().reloadFiles();
                        ZLangSelector.getInstance().reloadConfig();
                        Bukkit.getPluginManager().disablePlugin(ZLangSelector.getInstance());
                        Bukkit.getPluginManager().enablePlugin(ZLangSelector.getInstance());
                        Message.send(sender, messages.getString("EN.zLangSelector.Reload"));
                    } else {
                        Message.send(sender, messages.getString("EN.Error.NoPermission"));
                    }
                }
                break;
            default:
                Message.send(sender, messages.getString("EN.Error.UnknownCommand").replace("%command%", "/zLangSelector [Reload]"));
                break;
        }
        return true;
    }
}
