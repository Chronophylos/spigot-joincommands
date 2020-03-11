package rlnt.joincommands.gui;

import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import rlnt.joincommands.JoinCommands;
import rlnt.joincommands.util.PluginLogger;

public final class Gui implements InventoryHolder, Listener {
    private static final ItemStack BORDER_ITEM = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    private static final ItemStack CLOSE_ITEM = new ItemStack(Material.BEEF);
    private static final ItemStack NONE_ITEM = new ItemStack(Material.AIR);
    private static final ItemStack ADD_ITEM = new ItemStack(Material.ALLIUM);
    private static final ItemStack REARRANGE_ITEM = new ItemStack(Material.ITEM_FRAME);

    private static final int INVENTORY_SIZE = 9 * 5;
    private static final int PAGE_ITEM_OFFSET = 9;

    private PluginLogger logger = JoinCommands.getInstance().getLogger();

    private final Inventory inventory;
    private static ItemStack[] initialContent;
    private Player targetPlayer;

    private int pageNumber = 0;
    private LinkedList<LinkedList<CommandItem>> pages = new LinkedList<LinkedList<CommandItem>>();

    public Gui(Player targetPlayer) {
        this.targetPlayer = targetPlayer;

        inventory = Bukkit.createInventory(this, INVENTORY_SIZE, targetPlayer.getDisplayName());
        inventory.setContents(getInitialContent());

        renderPage();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private ItemStack[] getInitialContent() {
        if (initialContent != null) {
            return initialContent;
        }

        LinkedList<ItemStack> content = new LinkedList<ItemStack>();

        for (int i = 0; i < 8; i++) {
            content.push(BORDER_ITEM);
        }

        content.push(CLOSE_ITEM);

        for (int i = 0; i < 27; i++) {
            content.push(NONE_ITEM);
        }

        for (int i = 0; i < 4; i++) {
            content.push(BORDER_ITEM);
        }

        content.push(ADD_ITEM);

        content.push(BORDER_ITEM);

        content.push(REARRANGE_ITEM);

        content.push(BORDER_ITEM);
        content.push(BORDER_ITEM);

        return content.toArray(new ItemStack[content.size()]);
    }


    private void renderPage() {
        LinkedList<CommandItem> page = pages.get(pageNumber);

        for (int i = 0; i < INVENTORY_SIZE - 2 * PAGE_ITEM_OFFSET; i++) {
            try {
                inventory.setItem(PAGE_ITEM_OFFSET + i, page.get(i).getItem());
            } catch (IndexOutOfBoundsException ignored) {
                inventory.setItem(PAGE_ITEM_OFFSET + i, NONE_ITEM);
            }
        }
    }

    //
    // Public API
    //

    /**
     * Show a specific page.
     * @param number - the number of the page to show
     * @throws IllegalArgumentException - if the page number is negative or higher than the amount of pages
     */
    public void showPage(int number) throws IllegalArgumentException {
        if (number < 0) {
            throw new IllegalArgumentException("page numer is negative");
        }

        if (number >= pages.size()) {
            throw new IllegalArgumentException("page number higher than the amount of pages");
        }

        pageNumber = number;

        renderPage();
    }

    /**
     * Show the next page if available.
     */
    public void nextPage() {
        if (pageNumber < pages.size()) {
            showPage(pageNumber + 1);
        }
    }

    /**
     * Show the previous page if available.
     */
    public void prevPage() {
        if (pageNumber > 0) {
            showPage(pageNumber - 1);
        }
    }

    /**
     * Show the inventory for the {@link Player} player.
     * @param player - the player to show the inventory to.
     */
    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    //
    // Event handling
    //

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        // if the inventory in the event is not this inventory ignore the event
        if (e.getInventory().getHolder() != this) {
            return;
        }

        // do not let any other handler handle this event
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
    }
}
