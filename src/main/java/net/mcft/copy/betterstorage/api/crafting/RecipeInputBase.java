package net.mcft.copy.betterstorage.api.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class RecipeInputBase implements IRecipeInput {
	
	@Override
	public abstract int getAmount();
	
	@Override
	public abstract boolean matches(ItemStack stack);
	
	@Override
	public void craft(ItemStack input, ContainerInfo containerInfo) {
		if (input == null) return;

		Item item = input.getItem();
		ItemStack containerItem = item.getContainerItem(input.copy());
		//TODO (1.8): investigate if this is needed anymore
//		boolean doesLeaveCrafting = item.doesContainerItemLeaveCraftingGrid(input);
		containerInfo.set(containerItem, true);
		
		input.stackSize -= getAmount();
	}
	
}
