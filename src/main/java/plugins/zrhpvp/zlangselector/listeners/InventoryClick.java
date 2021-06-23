package plugins.zrhpvp.zlangselector.listeners;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.zrhpvp.zlangselector.ZLangSelector;
import plugins.zrhpvp.zlangselector.language.Language;
import plugins.zrhpvp.zlangselector.utils.Item;
import plugins.zrhpvp.zlangselector.managers.LangManager;
import plugins.zrhpvp.zlangselector.utils.Message;

public class InventoryClick implements Listener {

    private final FileConfiguration config, messages;
    private final LangManager langManager;

    public InventoryClick() {
        config = ZLangSelector.getInstance().getConfig();
        messages = ZLangSelector.getInstance().getFileManager().getMessages();
        langManager = ZLangSelector.getInstance().getLangManager();
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();

        if (inv == null || inv.getName() == null || e.getCurrentItem() == null || inv.equals(p.getInventory()) || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (inv.getTitle().equals(Message.color(config.getString("GUI.LangSelector.Title")).replace("%player%", p.getName()))) {
            e.setCancelled(true);
            
            ItemStack filler = Item.create(160, 1, 9, " ");

            if (e.getCurrentItem().isSimilar(filler)) {
                return;
            }

            Language language = Language.getList().get(e.getSlot());

            langManager.setPlayerLang(p, language);
            p.closeInventory();
            Message.send(p, messages.getString(langManager.getPlayerLang(p) + ".Language.Change").replace("%lang%", language.toString()).replace("%lang-displayname%", language.getDisplayName()));
        }

    }

}
