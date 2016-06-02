package net.mcft.copy.betterstorage.tile;

import net.mcft.copy.betterstorage.item.tile.ItemLockable;
import net.mcft.copy.betterstorage.proxy.ClientProxy;
import net.mcft.copy.betterstorage.tile.entity.TileEntityReinforcedChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileReinforcedChest extends TileLockable {
	
	public TileReinforcedChest() {
		this(Material.wood);
	}
	
	public TileReinforcedChest(Material material) {
		super(material);
		setHardness(8.0F);
		setResistance(20.0F);
		setHarvestLevel("axe", 2);
		setStepSound(soundTypeWood);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemLockable.class; }
	
	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("log_oak");
	}
	*/
	
	@Override
	public boolean isOpaqueCube() { return false; }

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() { return ClientProxy.reinforcedChestRenderId; }
	

	
	/*
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntityReinforcedChest chest = WorldUtils.get(world, x, y, z, TileEntityReinforcedChest.class);
		if (chest.isConnected()) {
			ForgeDirection connected = chest.getConnected();
			if (connected == ForgeDirection.NORTH)
				setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
			else if (connected == ForgeDirection.SOUTH)
				setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
			else if (connected == ForgeDirection.WEST)
				setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
			else if (connected == ForgeDirection.EAST)
				setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
		} else setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	*/
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityReinforcedChest();
	}
}
