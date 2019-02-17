package coolway99.discordpokebot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

public class BotReadyHandler{

	private final Thread mainThread;

	public BotReadyHandler(Thread mainThread){
		this.mainThread = mainThread;
	}

	@EventSubscriber
	public void handle(ReadyEvent event){
		try{
			this.mainThread.join();
		} catch(InterruptedException e){
			System.err.println("Error in ready thread, Interrupt received");
			e.printStackTrace();
			System.exit(-1);
			return;
		}
		System.out.println("The bot is ready");//reggie");
		Pokebot.client.getDispatcher().registerListener(new EventHandler());
		Pokebot.client.getDispatcher().unregisterListener(this);
		Pokebot.client.changePresence(StatusType.ONLINE, ActivityType.PLAYING, Pokebot.getRandomGame());

		try {
			Pokebot.client.changeUsername(Pokebot.config.BOTNAME);
		} catch(DiscordException | RateLimitException e) {
			e.printStackTrace();
			System.err.println("\nError changing username");
		}
	}
}