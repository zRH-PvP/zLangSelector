package plugins.zrhpvp.zlangselector.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    
    public static ItemStack create(int material, int amount, int data, String displayName) {

        ItemStack item = new ItemStack(Material.getMaterial(material), amount, (short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Message.color(displayName));
        item.setItemMeta(itemMeta);

        return item;
    }
    
}
