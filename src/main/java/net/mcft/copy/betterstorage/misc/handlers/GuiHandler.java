package net.mcft.copy.betterstorage.misc.handlers;

import net.mcft.copy.betterstorage.client.gui.GuiBetterStorage;
import net.mcft.copy.betterstorage.container.ContainerBetterStorage;
import net.mcft.copy.betterstorage.inventory.InventoryBackpackEquipped;
import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.misc.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case Constants.guiBackpackID:
                System.out.println("Received Server Side");
                IInventory inventory = ItemBackpack.getBackpackItems(player, player);
                inventory = new InventoryBackpackEquipped(player, player, inventory);

                Container container = new ContainerBetterStorage(player, inventory, x, y);
                return container;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case Constants.guiBackpackID:
                System.out.println("Received Client Side");
                IInventory inventory = ItemBackpack.getBackpackItems(player, player);
                inventory = new InventoryBackpackEquipped(player, player, inventory);
                return new GuiBetterStorage(player, x, y, inventory);
        }
        return null;
    }
}
