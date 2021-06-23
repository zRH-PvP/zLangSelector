package plugins.zrhpvp.zlangselector.utils;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public static void sendMessageList(CommandSender sender, List<String> list) {
        for (String message : list) {
            send(sender, message);
        }
    }

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
