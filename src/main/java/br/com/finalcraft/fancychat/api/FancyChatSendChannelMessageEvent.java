package br.com.finalcraft.fancychat.api;

import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FancyChatSendChannelMessageEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final CommandSender sender;
	private String msg;
	private FancyChannel channel;
	private boolean cancelChat = true;
	
	public FancyChatSendChannelMessageEvent(CommandSender sender, FancyChannel channel, String msg){
		this.sender = sender;
		this.msg = msg;
		this.channel = channel;
	}


	public void cancelIncomingChat(boolean cancel){
		this.cancelChat = cancel;
	}
	

	public boolean getCancelIncomingChat(){
		return this.cancelChat;
	}


	public FancyChannel getChannel(){
		return this.channel;
	}
	

	public void setMessage(String newMsg){
		this.msg = newMsg;
	}
	

	public String getMessage(){
		return this.msg;
	}
	

	public CommandSender getSender(){
		return this.sender;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }

}
