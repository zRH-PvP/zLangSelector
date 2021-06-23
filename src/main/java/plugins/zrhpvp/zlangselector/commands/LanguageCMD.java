package plugins.zrhpvp.zlangselector.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.gui.GUI;
import plugins.zrhpvp.zlangselector.managers.LangManager;
import plugins.zrhpvp.zlangselector.utils.Message;

public class LanguageCMD implements CommandExecutor {

    private final FileConfiguration messages;
    private final LangManager langManager;
    private final GUI gui;

    public LanguageCMD() {
        messages = ZLangSelector.getInstance().getFileManager().getMessages();
        langManager = ZLangSelector.getInstance().getLangManager();
        gui = new GUI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {

        if (!(sender instanceof Player)) {
            Message.send(sender, "&cYou must be a player");
            return true;
        }

        Player p = (Player) sender;

        if (p.hasPermission("zlangselector.selectlanguage")) {
            if (args.length == 0) {
                gui.openLangSelector(p);
            } else {
                Message.send(p, messages.getString(langManager.getPlayerLang(p) + ".Error.UnknownCommand").replace("%command%", "/Language"));
            }
        } else {
            Message.send(p, messages.getString(langManager.getPlayerLang(p) + ".Error.NoPermission"));
        }

        return true;
    }
}
