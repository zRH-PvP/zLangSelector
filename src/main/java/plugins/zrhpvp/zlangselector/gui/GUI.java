package plugins.zrhpvp.zlangselector.gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.language.Language;
import plugins.zrhpvp.zlangselector.utils.Item;
import plugins.zrhpvp.zlangselector.utils.Message;

public class GUI {

    private final FileConfiguration config;

    public GUI() {
        config = ZLangSelector.getInstance().getConfig();
    }

    public void openLangSelector(Player p) {

        int neededRows = config.getConfigurationSection("GUI.LangSelector.Items").getKeys(false).size();

        Inventory langSelector = Bukkit.createInventory(null, neededRows * 9, Message.color(config.getString("GUI.LangSelector.Title")).replace("%player%", p.getName()));

        ItemStack filler = Item.create(160, 1, 9, " ");
        for (int i = 0; i < langSelector.getSize(); i++) {
            langSelector.setItem(i, filler);
        }
        
        int i = 0;
        for (Language language : Language.getList()) {
            langSelector.setItem(i, (ItemStack) config.getItemStack("GUI.LangSelector.Items." + language.toString()));
            i++;
        }

        p.openInventory(langSelector);
    }

}
