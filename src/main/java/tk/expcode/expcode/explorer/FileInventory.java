package tk.expcode.expcode.explorer;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.builders.InventoryBuilder;
import tk.expcode.expcode.builders.ItemStackBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileInventory {

    private final ExpCode main;

    public FileInventory(ExpCode main) {
        this.main = main;
    }

    public void openFolder(HumanEntity humanEntity, File folder, int page) {
        if (folder.isFile()) return;
        if (page > Objects.requireNonNull(folder.listFiles()).length / 45 + 1)
            page = Objects.requireNonNull(folder.listFiles()).length / 45 + 1;
        if (page < 1) page = 1;
        main.getExplorerPage().put(humanEntity.getName(), page);
        InventoryBuilder inventoryBuilder = new InventoryBuilder(humanEntity, 9 * 6, ChatColor
                .translateAlternateColorCodes('&',
                        "&dExplorer " + page + "/" + (Objects.requireNonNull(folder.listFiles()).length /
                                45 + 1) + " (" + folder.getName() + ")"));
        List<File> folders = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(File::isDirectory)
                .collect(Collectors.toList());
        List<File> files = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(File::isFile)
                .collect(Collectors.toList());
        List<File> all = new LinkedList<>();
        all.addAll(folders);
        all.addAll(files);
        int i = (page - 1) * 45;
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
        while (i < page * 45 && i < all.size()) {
            inventoryBuilder.addItem(new ItemStackBuilder(all.get(i).isDirectory() ? Material.STORAGE_MINECART : Material.PAPER)
                    .displayName(ChatColor.translateAlternateColorCodes('&',
                            (all.get(i).isDirectory() ? "&9" : "&6") + all.get(i).getName()))
                    .addLore(ChatColor.translateAlternateColorCodes('&',
                            "&7Last change:"))
                    .addLore(ChatColor.translateAlternateColorCodes('&',
                            "&7- " + format.format(all.get(i).lastModified())))
                    .toItemStack());
            i++;
        }
        inventoryBuilder.setItem(9 * 6 - 1, new ItemStackBuilder(Material.BARRIER)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 2, new ItemStackBuilder(Material.ARROW)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aGet full Path"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 3, new ItemStackBuilder(Material.MILK_BUCKET)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Paste"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 4, new ItemStackBuilder(Material.REDSTONE)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&eReload"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 5, new ItemStackBuilder(Material.MAP)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aCreate new File"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 6, new ItemStackBuilder(Material.ENDER_CHEST)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aCreate new Folder"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 8, new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Next Page"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 9, new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Previous Page"))
                .toItemStack());
        humanEntity.openInventory(inventoryBuilder.toInventory());
    }

    public void openFile(HumanEntity humanEntity, File file) {
        if (file.isDirectory()) return;
        humanEntity.openInventory(new InventoryBuilder(humanEntity, 9 * 6, ChatColor
                .translateAlternateColorCodes('&',
                        "&aFile (" + file.getName() + ")"))
                .addItem(new ItemStackBuilder(Material.REDSTONE_COMPARATOR)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                "&cDelete"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(Material.BUCKET)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                "&7Copy"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(Material.FEATHER)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                "&dRename"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(Material.PAPER)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                "&6Read"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(Material.BONE)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                "&bHastebin"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(file.getName().endsWith(".jar") ? Material.FIREBALL : Material.STONE_BUTTON)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                file.getName().endsWith(".jar") ? "&cCopy to this Jar" : "&7File is not a Jar"))
                        .toItemStack())
                .addItem(new ItemStackBuilder(file.getName().endsWith(".jar") ? Material.DIAMOND : Material.STONE_BUTTON)
                        .displayName(ChatColor.translateAlternateColorCodes('&',
                                file.getName().endsWith(".jar") ? "&dSet as Base Plugin" : "&7File is not a Jar"))
                        .toItemStack())
                .setItem(9 * 6 - 1, new ItemStackBuilder(Material.BARRIER)
                        .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                        .toItemStack())
                .setItem(9 * 6 - 2, new ItemStackBuilder(Material.ARROW)
                        .displayName(ChatColor.translateAlternateColorCodes('&', "&aGet full Path"))
                        .toItemStack())
                .toInventory());
    }

    public void readFile(HumanEntity humanEntity, File file, int page) throws IOException {
        List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
        if (page > lines.size() / 45 + 1)
            page = lines.size() / 45 + 1;
        if (page < 1) page = 1;
        main.getExplorerPage().put(humanEntity.getName(), page);
        InventoryBuilder inventoryBuilder = new InventoryBuilder(humanEntity, 9 * 6, ChatColor
                .translateAlternateColorCodes('&',
                        "&9Read " + page + "/" + (lines.size() /
                                45 + 1) + " (" + file.getName() + ")"));
        int i = (page - 1) * 45;
        while (i < page * 45 && i < lines.size()) {
            inventoryBuilder.addItem(new ItemStackBuilder(Material.PAPER)
                    .displayName(ChatColor.translateAlternateColorCodes('&',
                            "&b" + lines.get(i)))
                    .toItemStack());
            i++;
        }
        inventoryBuilder.setItem(9 * 6 - 1, new ItemStackBuilder(Material.BARRIER)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 2, new ItemStackBuilder(Material.REDSTONE)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&eReload"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 8, new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Next Page"))
                .toItemStack());
        inventoryBuilder.setItem(9 * 6 - 9, new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Previous Page"))
                .toItemStack());
        humanEntity.openInventory(inventoryBuilder.toInventory());
    }
}
