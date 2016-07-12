package net.mcft.copy.betterstorage.commands;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.content.Costumes;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketRouteNotification;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketUnlock;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

public class CommandGGRedeem extends CommandBase {

    public String getCommandName() {
        return "ggredeem";
    }


    public String getCommandUsage(ICommandSender sender) {
        return EnumChatFormatting.DARK_RED+"/ggredeem <costumeName> [playerName]";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(sender.getCommandSenderEntity()!=null && sender.getCommandSenderEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
            if (args.length < 1) {
                sender.addChatMessage(new ChatComponentText(Costumes.getCostumeNames().toString()));
                return;
            }
            if (args.length == 1) {
                if(args[0].equalsIgnoreCase("routeTest")) {
                    BetterStorage.networkChannel.sendTo(new PacketRouteNotification("Example Route"), player);
                } else {
                    String pieceName = args[0];
                    if (Costumes.getCostumeByName(pieceName) != null) {
                        CostumeHandler.get(player).unlockPiece(Costumes.getCostumeByName(pieceName));
                        BetterStorage.networkChannel.sendTo(new PacketUnlock(pieceName), player);
                    }
                }
            }
        }
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return Costumes.getCostumeNames();
    }
}
