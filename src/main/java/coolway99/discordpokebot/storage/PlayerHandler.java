package coolway99.discordpokebot.storage;

import coolway99.discordpokebot.Player;
import coolway99.discordpokebot.battle.BattleManager;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.Iterator;

public class PlayerHandler{

	public static final byte MAX_SLOTS = 6;
	
	private static final HashMap<String, Player> playerMap = new HashMap<>();
	private static final HashMap<String, MainFile> mainFileMap = new HashMap<>();
	
	public static Player getPlayer(IUser user){
		if(playerMap.containsKey(user.getStringID())) return playerMap.get(user.getStringID());
		return getPlayer(user, getMainFile(user).lastSlot);
	}

	//TODO This is a mess
	private static Player getPlayer(IUser user, byte slot){
		if(playerMap.containsKey(user.getStringID())){
			if(playerMap.get(user.getStringID()).slot == slot) return playerMap.get(user.getStringID());
			//ELSE
			removePlayer(user);
		}
		playerMap.put(user.getStringID(), new Player(user, slot));
		return playerMap.get(user.getStringID());
	}

	public static void switchSlot(IUser user, byte slot){
		String ID = user.getStringID();
		if(playerMap.containsKey(ID)) playerMap.remove(ID).saveData();
		getPlayer(user, slot);
		return;
	}

	public static void removePlayer(IUser user){
		if(!playerMap.containsKey(user.getStringID())) return;
		playerMap.remove(user.getStringID()).saveData();
	}

	public static MainFile getMainFile(IUser user){
		mainFileMap.computeIfAbsent(user.getStringID(), s -> new MainFile(user));
		return mainFileMap.get(user.getStringID());
	}

	public static void saveAll(){
		Iterator<Player> i = playerMap.values().iterator();
		while(i.hasNext()){
			Player player = i.next();
			player.saveData();
			//If the player is not in a battle, and the player does not have a battle pending
			if(!(player.inBattle() || BattleManager.hasBattlePending(player.user))){
				//System.out.println("Removing player");
				i.remove();
			}
		}
		Iterator<MainFile> j = mainFileMap.values().iterator();
		while(j.hasNext()){
			MainFile main = j.next();
			main.saveData();
			j.remove();
		}
	}
}
