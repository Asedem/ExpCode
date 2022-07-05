package tk.expcode.expcode.builders;

import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class ItemStacks {

    private ItemStacks() {
    }

    @NotNull
    public static String encodeBase64(@NotNull ItemStack item) throws IOException {
        try(ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str)) {
            data.writeObject(item);

            return Base64.getEncoder().encodeToString(str.toByteArray());
        }
    }

    @NotNull
    public static ItemStack decodeBase64(@NotNull String base64) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream str = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
            BukkitObjectInputStream data = new BukkitObjectInputStream(str)) {

            return (ItemStack) data.readObject();
        }
    }

    @NotNull
    public static String getCustomizedName(@NotNull ItemStack itemStack) {
        if(itemStack.getItemMeta() != null) {
            if(itemStack.getItemMeta().hasDisplayName()) {
                return itemStack.getItemMeta().getDisplayName();
            } else if(itemStack.getItemMeta().hasLocalizedName()) {
                return itemStack.getItemMeta().getLocalizedName();
            }
        }
        return WordUtils.capitalize(itemStack.getType().toString());
    }

    @NotNull
    public static ItemMeta getSafeItemMeta(@NotNull ItemStack item) {
        if(item.getItemMeta() != null) {
            return item.getItemMeta();
        }
        throw new IllegalStateException();
    }

}
