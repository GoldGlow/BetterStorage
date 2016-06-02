package net.mcft.copy.betterstorage.misc;

import net.mcft.copy.betterstorage.content.BetterStorageItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabGGCustomisation extends CreativeTabs {

    public CreativeTabGGCustomisation() {
        super("ggCustomisation");
    }

    @Override
    public Item getTabIconItem() {
        return BetterStorageItems.duskullMask;
    }
}
