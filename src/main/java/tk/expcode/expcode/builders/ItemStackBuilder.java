package tk.expcode.expcode.builders;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemStackBuilder {

    protected ItemStack currentBuilding;

    public ItemStackBuilder() {
        this.currentBuilding = new ItemStack(Material.STONE);
    }

    public ItemStackBuilder(@NotNull ItemStack base) {
        this.currentBuilding = base.clone();
    }

    public ItemStackBuilder(@NotNull Material material) {
        this.currentBuilding = new ItemStack(material);
    }

    public ItemStack toItemStack() {
        return currentBuilding;
    }

    @NotNull
    public ItemStackBuilder displayName(String displayName) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        meta.setDisplayName(displayName);
        currentBuilding.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder enchantment(Enchantment enchantment, int level) {
        this.currentBuilding.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /*@NotNull
    public ItemStackBuilder modelData(@Nullable Integer model) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        meta.setCustomModelData(model);
        this.currentBuilding.setItemMeta(meta);

        return this;
    }*/

    @NotNull
    public ItemStackBuilder removeEnchantment(Enchantment enchantment) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        meta.removeEnchant(enchantment);
        currentBuilding.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder lore(List<String> lore) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        meta.setLore(lore);
        currentBuilding.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder lore(String... lines) {
        Validate.notEmpty(lines, "new Lines cannot be empty or null");
        return this.lore(Arrays.asList(lines));
    }

    @NotNull
    public ItemStackBuilder addLore(String line) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        if (!meta.hasLore()) {
            meta.setLore(Collections.singletonList(line));
            return this;
        }
        List<String> lore = meta.getLore();
        lore.add(line);
        return this.lore(lore);
    }

    @NotNull
    public ItemStackBuilder hideEnchantments(boolean result) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        if(result) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        else meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        currentBuilding.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder flags(ItemFlag... flags) {
        ItemMeta meta = ItemStacks.getSafeItemMeta(currentBuilding);
        for(ItemFlag flag : meta.getItemFlags()) {
            meta.removeItemFlags(flag);
        }
        meta.addItemFlags(flags);
        currentBuilding.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder amount(int amount) {
        Validate.isTrue(amount >= 0, "Amount must be positive");
        currentBuilding.setAmount(amount);
        return this;
    }
}
