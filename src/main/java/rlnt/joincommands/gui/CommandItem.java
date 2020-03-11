package rlnt.joincommands.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CommandItem {
    private static final ItemStack ITEM_TEMPLATE = new ItemStack(Material.COMMAND_BLOCK);
    private ItemStack item;
    private String command; // placeholder

    public CommandItem(String command) {
        this.command = command;

        item = new ItemStack(ITEM_TEMPLATE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(command);
        item.setItemMeta(itemMeta);
    }

    public ItemStack getItem() {
        return item;
    }
}
