package tk.expcode.expcode.builders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryBuilder {

    protected Inventory currentBuilding;

    public InventoryBuilder() {
        currentBuilding = Bukkit.createInventory(null, InventoryType.CHEST);
    }

    public InventoryBuilder(@NotNull Inventory base) {
        currentBuilding = base;
    }

    public InventoryBuilder(@NotNull InventoryType inventoryType) {
        currentBuilding = Bukkit.createInventory(null, inventoryType);
    }

    public InventoryBuilder(int inventorySize) {
        currentBuilding = Bukkit.createInventory(null, inventorySize);
    }

    public InventoryBuilder(@NotNull InventoryHolder owner, InventoryType inventoryType) {
        currentBuilding = Bukkit.createInventory(owner, inventoryType);
    }

    public InventoryBuilder(@NotNull InventoryHolder owner, int inventorySize) {
        currentBuilding = Bukkit.createInventory(owner, inventorySize);
    }

    public InventoryBuilder(InventoryType inventoryType, @NotNull String title) {
        currentBuilding = Bukkit.createInventory(null, inventoryType, title);
    }

    public InventoryBuilder(int inventorySize, @NotNull String title) {
        currentBuilding = Bukkit.createInventory(null, inventorySize, title);
    }

    public InventoryBuilder(@NotNull InventoryHolder owner, InventoryType inventoryType, @NotNull String title) {
        currentBuilding = Bukkit.createInventory(owner, inventoryType, title);
    }

    public InventoryBuilder(@NotNull InventoryHolder owner, int inventorySize, @NotNull String title) {
        currentBuilding = Bukkit.createInventory(owner, inventorySize, title);
    }

    @NotNull
    public Inventory toInventory() {
        return currentBuilding;
    }

    public void openToHumanEntity(HumanEntity humanEntity) {
        humanEntity.openInventory(currentBuilding);
    }

    @NotNull
    public InventoryBuilder fillBlanks(@NotNull Material material) {
        for (int i = 0; i < currentBuilding.getSize(); i++)
            if (currentBuilding.getItem(i).getType() == Material.AIR)
                currentBuilding.setItem(i, new ItemStackBuilder(material)
                        .displayName(ChatColor.translateAlternateColorCodes('&', "&7"))
                        .toItemStack());
        return this;
    }

    @NotNull
    public InventoryBuilder addItem(@NotNull ItemStack itemStack) {
        currentBuilding.addItem(itemStack);
        return this;
    }

    @NotNull
    public InventoryBuilder setItem(int index, @NotNull ItemStack itemStack) {
        currentBuilding.setItem(index, itemStack);
        return this;
    }

    @NotNull
    public InventoryBuilder addItems(@NotNull List<ItemStack> itemStacks) {
        itemStacks.forEach(itemStack -> currentBuilding.addItem(itemStack));
        return this;
    }

    @NotNull
    public InventoryBuilder setItems(int startIndex, @NotNull List<ItemStack> itemStacks) {
        AtomicInteger index = new AtomicInteger(startIndex);
        itemStacks.forEach(itemStack -> {
            if (index.get() < currentBuilding.getSize()) {
                currentBuilding.setItem(index.get(), itemStack);
                index.set(index.get() + 1);
            }
        });
        return this;
    }

    @NotNull
    public InventoryBuilder setMaxStackSize(int size) {
        currentBuilding.setMaxStackSize(size);
        return this;
    }

    @NotNull
    public InventoryBuilder setContents(ItemStack[] items) {
        currentBuilding.setContents(items);
        return this;
    }

    @NotNull
    public InventoryBuilder setStorageContents(ItemStack[] items) {
        currentBuilding.setStorageContents(items);
        return this;
    }
}
