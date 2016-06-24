package com.lothrazar.cyclicmagic.net;
import com.lothrazar.cyclicmagic.ModMain;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncPlayerData implements IMessage, IMessageHandler<PacketSyncPlayerData, IMessage> {
  public static final int ID = 50;
  NBTTagCompound tags = new NBTTagCompound();
  public PacketSyncPlayerData() {
  }
  public PacketSyncPlayerData(NBTTagCompound ptags) {
    tags = ptags;
  }
  @Override
  public void fromBytes(ByteBuf buf) {
    tags = ByteBufUtils.readTag(buf);
  }
  @Override
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeTag(buf, this.tags);
  }
  @Override
  public IMessage onMessage(PacketSyncPlayerData message, MessageContext ctx) {
    if (ctx.side == Side.CLIENT) {
      //update it through client proxy
      ModMain.proxy.setClientPlayerData(message.tags);
    }
    return null;
  }
}