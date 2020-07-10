package fr.lycoon.cameractrl.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GamemodeChangePacket implements IMessage
{
    private boolean cameraToggled;
    
    public GamemodeChangePacket() { }
    public GamemodeChangePacket(boolean cameraToggled) 
    {
        this.cameraToggled = cameraToggled;
    }
 
    @Override
    public void fromBytes(ByteBuf buf)
    {
    	this.cameraToggled = buf.readBoolean();
    }
 
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(cameraToggled);
    }
 
    public static class HandlerServerGamemode implements IMessageHandler<GamemodeChangePacket, IMessage>
    {
        @Override
        public IMessage onMessage(GamemodeChangePacket message, MessageContext ctx)
        {
            ctx.getServerHandler().player.setGameType(message.cameraToggled ? GameType.SPECTATOR : GameType.CREATIVE);
            return null;
        }
    }
}