# ExpCode
ExpCode is an API for Spigot that make coding Plugins more <b>efficient</b> and more <b>pleasant</b> with many features!

## How to use it
Download the Git repository and run with the maven goals `clean` and `install`<br>
Then add the following dependency in you pom.xml:
```xml
<dependency>
    <groupId>tk.expcode</groupId>
    <artifactId>ExpCode</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

# Features

### InventoryBuilder
With the InventoryBuilder you can make a filled Inventory with only one line of code
```java
Inventory inventory = new InventoryBuilder(player, 3 * 9, "My Inventory")
        .fillBlanks(Material.STAINED_GLASS_PANE)
        .setItem(12, new ItemStack(Material.BOW))
        .setItem(14, new ItemStack(Material.ARROW))
        .toInventory();
```

### ItemStackBuilder
The ItemStackBuilder is the perfect addition for the InventoryBuilder to make the matching Items
```java
ItemStack itemStack = new ItemStackBuilder(Material.DIAMOND_AXE)
        .displayName("My Item")
        .lore("Line 1", "Line2", "Line3")
        .enchantment(Enchantment.DAMAGE_ALL, 5)
        .hideEnchantments(true)
        .toItemStack();
```

### TextComponentBuilder
For sending clickable Chat messages the TextComponentBuilder is something to try
```java
new TextComponentBuilder("The Test in the Chat Message")
        .setClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugins")
        .setHoverEvent(HoverEvent.Action.SHOW_TEXT, "See the Plugins:")
        .toTextComponent();
```

### Hastebin
The Hastebin API is for showing big texts or code block to a Player
```java
try {
    String link = new Hastebin()
            .post("First Line\nAnother Line", true);
} catch (IOException e) {
    throw new RuntimeException(e);
}
```

### Cryptor
The Cryptor is for crypt and decrypt Strings at runtime with a custom key
```java
String cryped = Cryptor.crypt("My Text to hide!");
```

## Plans for the Future
For now the API is only for the 1.12.x, but the versions 1.8.x and 1.18.x are following<br>
Also there will be classes for reflections in the Future and an advanced ItemStackBuilder

<br>
<br>

### Have fun to try it yourself :D

```java
System.exit(0);
```
