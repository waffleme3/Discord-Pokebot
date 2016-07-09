package coolway99.discordpokebot.storage;

import java.util.HashMap;

import coolway99.discordpokebot.Player;
import sx.blah.discord.handle.obj.IUser;

//TODO
public class PlayerHandler{
	
	private static final HashMap<String, Player> playerMap = new HashMap<>();
	
	public static Player getPlayer(IUser user){
		System.out.println("Accessing map");
		if(!playerMap.containsKey(user.getID())){
			System.out.println("Creating new entry"); 
			Player player = new Player(user);
			System.out.println("Created new user");
			playerMap.put(user.getID(), player);
			}
		System.out.println(user.getID());
		return playerMap.get(user.getID());
	}
	
	public static void removePlayer(IUser user){
		if(!playerMap.containsKey(user.getID())) return;
		playerMap.remove(user.getID()).saveData();
	}
	
	public static void saveAll(){
		for(Player player : playerMap.values()){
			player.saveData();
		}
		playerMap.clear();
	}
	
}