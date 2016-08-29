package coolway99.discordpokebot.moves;

import coolway99.discordpokebot.MoveConstants;
import coolway99.discordpokebot.Player;
import coolway99.discordpokebot.Pokebot;
import coolway99.discordpokebot.StatHandler;
import coolway99.discordpokebot.battle.Battle;
import coolway99.discordpokebot.battle.IAttack;
import coolway99.discordpokebot.states.Abilities;
import coolway99.discordpokebot.states.Effects;
import coolway99.discordpokebot.states.Stats;
import coolway99.discordpokebot.states.Types;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.TreeMap;

//Fire punch, Ice punch, Thunder punch, Signal Beam, and Relic song are all the same, except with different effects
//Any accuracy over 100 or over 1D will always hit, and will automatically have evasion and accuracy excluded
//For multi-hit moves, refer to PMD if needed
@SuppressWarnings({"SpellCheckingInspection", "unused"})
public abstract class Move{

	public static final TreeMap<String, Move> REGISTRY = new TreeMap<>(new MoveSorter());
	/*//ENUM_NAME(Type, MoveType, PP, Power, Accuracy, cost, battlepriority, flags)
	NULL(Types.NULL, MoveType.PHYSICAL, 0, 0, 0), //TODO perhaps make this Struggle
	ABSORB(Types.GRASS, MoveType.SPECIAL, 25, 20, 100, 40, Flags.HAS_BEFORE), //Double it's cost because it heals
	ACUPRESSURE(Types.NORMAL, MoveType.STATUS, 30, -1, -1, 100),
	ACID_SPRAY(Types.POISON, MoveType.SPECIAL, 20, 40, 100, 100, Flags.HAS_AFTER, Flags.BALLBASED), //Lowers special
	// defense by two
	ACROBATICS(Types.FLYING, MoveType.PHYSICAL, 15, 55, 100, 120, Flags.HAS_BEFORE), //If the user has no item, the
	// power is doubled
	AERIAL_ACE(Types.FLYING, MoveType.PHYSICAL, 20, 60, 1000, 80),
	AEROBLAST(Types.FLYING, MoveType.SPECIAL, 5, 100, 95), //Lugia's signature move, only benefit is higher critical
	// hit ratio and can target any opponent or ally
	//TODO After You makes the opponent attack first
	AFTER_YOU(Types.NORMAL, MoveType.STATUS, 15, -1, -1, 50, Flags.HAS_BEFORE),
	AIR_CUTTER(Types.FLYING, MoveType.SPECIAL, 25, 60, 95),
	//Ally Switch can't be /used/ here
	ANCIENT_POWER(Types.ROCK, MoveType.SPECIAL, 5, 60, 100, 130, Flags.HAS_AFTER),
	AQUA_JET(Types.WATER, MoveType.PHYSICAL, 20, 40, 100, 60, Battle_Priority.P1),
	AQUA_RING(Types.WATER, MoveType.STATUS, 20, -1, -1, 150, Flags.UNTARGETABLE, Flags.HAS_BEFORE),
	AROMATHERAPY(Types.GRASS, MoveType.STATUS, 5, -1, -1, 50, Flags.HAS_BEFORE),
	//Aromatic Mist can't apply just yet TODO Team Battles
	//Assist randomly uses an ally's move TODO Team Battles
	//TODO Assurance deals double damage if the target has already taken damage that turn
	ATTACK_ORDER(Types.BUG, MoveType.PHYSICAL, 15, 90, 100, 100, Flags.NO_CONTACT),
	//TODO Attract
	AURA_SPHERE(Types.FIGHTING, MoveType.SPECIAL, 20, 80, 1000, 100),
	//TODO Lowers user's weight
	AUTOTOMIZE(Types.STEEL, MoveType.STATUS, 15, -1, -1, 50, Flags.HAS_BEFORE, Flags.UNTARGETABLE), //TODO lowers weight
	AVALANCHE(Types.ICE, MoveType.PHYSICAL, 10, 60, 100, 80, Battle_Priority.N4, Flags.HAS_BEFORE),
	BABY_DOLL_EYES(Types.FAIRY, MoveType.STATUS, 30, -1, 100, 25, Battle_Priority.P1, Flags.HAS_BEFORE),
	BARRIER(Types.PSYCHIC, MoveType.STATUS, 20, -1, -1, 50, Flags.HAS_BEFORE, Flags.UNTARGETABLE),
	//TODO Baton Pass
	//TODO BEAT_UP(Types.DARK, MoveType.PHYSICAL, 10, -1, 100, Flags.NO_CONTACT)
	//TODO BELCH(Types.POISON, MoveType.SPECIAL, 10, 120, 90)
	BELLY_DRUM(Types.NORMAL, MoveType.STATUS, 10, -1, -1, 150, Flags.HAS_BEFORE, Flags.UNTARGETABLE),
	//TODO Bestow
	//TODO Bide
	//TODO This is going to be a bit tricky to implement. Look at BIDE
	BIND(Types.NORMAL, MoveType.PHYSICAL, 20, 15, 85), //TODO Multiturn
	BITE(Types.DARK, MoveType.PHYSICAL, 25, 60, 100, 75, Flags.HAS_AFTER),
	BLAST_BURN(Types.FIRE, MoveType.SPECIAL, 5, 150, 90, Flags.HAS_BEFORE),
	BLIZZARD(Types.ICE, MoveType.SPECIAL, 5, 110, 70, 115, Flags.HAS_BEFORE, Flags.HAS_AFTER),
	//TODO Block
	//One of the signature moves of Reshiram, boosts fusion bolt
	BLUE_FLARE(Types.FIRE, MoveType.SPECIAL, 5, 130, 85, 140, Flags.HAS_AFTER),
	BODY_SLAM(Types.NORMAL, MoveType.PHYSICAL, 15, 85, 100, 100, Flags.HAS_BEFORE, Flags.HAS_AFTER),
	//One of the signature moves of Zekrom, boosts fusion flare
	BOLT_STRIKE(Types.ELECTRIC, MoveType.PHYSICAL, 5, 130, 85, 95, Flags.HAS_AFTER),
	BONE_CLUB(Types.GROUND, MoveType.PHYSICAL, 20, 65, 85, 70, Flags.HAS_AFTER, Flags.NO_CONTACT),
	CUT(Types.NORMAL, MoveType.PHYSICAL, 30, 50, 95),
	DESTINY_BOND(Types.GHOST, MoveType.STATUS, 5, -1, -1, 100, Flags.UNTARGETABLE, Flags.HAS_BEFORE),
	FAIRY_WIND(Types.FAIRY, MoveType.SPECIAL, 30, 40, 100), //Same as scratch
	FIRE_PUNCH(Types.FIRE, MoveType.PHYSICAL, 15, 75, 100, 80, Flags.HAS_AFTER),
	//Multiturn, boosted cost because of semiinvul
	FLY(Types.FLYING, MoveType.PHYSICAL, 15, 90, 95, 120, Flags.HAS_BEFORE, Flags.FLIGHT, Flags.MULTITURN),
	HEAL_BELL(Types.NORMAL, MoveType.STATUS, 5, -1, -1, 50, Flags.HAS_BEFORE),
	ICE_PUNCH(Types.ICE, MoveType.PHYSICAL, 15, 75, 100, 80, Flags.HAS_AFTER),
	GASTRO_ACID(Types.POISON, MoveType.STATUS, 10, -1, 100, 150, Flags.HAS_BEFORE), //Suppresses the target's ability
	GRAVITY(Types.PSYCHIC, MoveType.BATTLE_STATUS, 5, -1, -1, 100, Flags.HAS_BEFORE, Flags.UNTARGETABLE),
	GUILLOTINE(Types.NORMAL, MoveType.PHYSICAL, 5, -1, 30, 150, Flags.HAS_BEFORE),
	//Affects fly and other moves like that, dealing double damage. +10 cost because of that
	GUST(Types.FLYING, MoveType.SPECIAL, 35, 40, 100, 50), //Same as scratch, affects fly, bounce, etc
	JUMP_KICK(Types.FIGHTING, MoveType.PHYSICAL, 10, 100, 95, 100, Flags.HAS_BEFORE, Flags.FLIGHT), //Has recoil
	KARATE_CHOP(Types.FIGHTING, MoveType.PHYSICAL, 25, 50, 100),
	MEGA_KICK(Types.NORMAL, MoveType.PHYSICAL, 5, 120, 75),
	MEGA_PUNCH(Types.NORMAL, MoveType.PHYSICAL, 20, 80, 85),
	//PAY_DAY(Types.NORMAL, MoveType.Physical, 20, 80, 85), //Same as mega punch, but drops money
	POUND(Types.NORMAL, MoveType.PHYSICAL, 35, 40, 100), //Same as scratch
	POISON_TAIL(Types.POISON, MoveType.PHYSICAL, 25, 50, 100, 60, Flags.HAS_AFTER), //Same as Karate-Chop, Adding +10
	// cost for poison chance
	RELIC_SONG(Types.NORMAL, MoveType.SPECIAL, 10, 75, 100, 80, Flags.HAS_AFTER), //TODO sleep
	SCRATCH(Types.NORMAL, MoveType.PHYSICAL, 35, 40, 100), //Same as scratch
	SKY_ATTACK(Types.FLYING, MoveType.PHYSICAL, 5, 140, 90, Flags.MULTITURN, Flags.HAS_AFTER), //TODO multiturn
	SIGNAL_BEAM(Types.BUG, MoveType.SPECIAL, 15, 75, 100, 80, Flags.HAS_AFTER), //TODO confusion
	SLAM(Types.NORMAL, MoveType.PHYSICAL, 20, 80, 75),
	//TODO Named wrong
	STEAM_ROLLER(Types.BUG, MoveType.PHYSICAL, 20, 65, 100, 80, Flags.HAS_BEFORE, Flags.HAS_AFTER), //Same as Stomp,
	//Fine, you people win
	SPLASH(Types.WATER, MoveType.PHYSICAL, 999, 0, 100, 200, Flags.UNTARGETABLE, Flags.HAS_BEFORE, Flags.FLIGHT),
	STOMP(Types.NORMAL, MoveType.PHYSICAL, 20, 65, 100, 80, Flags.HAS_BEFORE, Flags.HAS_AFTER),
	THUNDER_PUNCH(Types.ELECTRIC, MoveType.PHYSICAL, 15, 75, 100, 80, Flags.HAS_AFTER),
	RAZOR_WIND(Types.NORMAL, MoveType.SPECIAL, 10, 80, 100, Flags.MULTITURN), //TODO It's a multiturn-attack,
	WATER_GUN(Types.WATER, MoveType.SPECIAL, 25, 40, 100), //Same as scratch
	WING_ATTACK(Types.FLYING, MoveType.SPECIAL, 35, 60, 100),
	//WHIRLWIND does not apply
	VICE_GRIP(Types.NORMAL, MoveType.PHYSICAL, 30, 55, 100),
	VINE_WHIP(Types.GRASS, MoveType.PHYSICAL, 25, 45, 100);*/

	private final Types type;
	private final int power;
	private final MoveType moveType;
	private final int PP; //The default PP of the move
	private final double accuracy; //From 0 to 1
	private final Battle_Priority priority;
	private final int cost; //How many points will this move use?
	private final EnumSet<Flags> flags;

	protected Move(Types type, MoveType moveType, int PP, int power, int accuracy, int cost, Battle_Priority priority,
		 Flags... flags){
		this.type = type;
		this.power = power;
		this.moveType = moveType;
		this.PP = PP;
		this.accuracy = accuracy/100D; //TODO perhaps make it 0-100D
		this.priority = priority;
		this.cost = cost;
		if(flags.length <= 0){
			this.flags = EnumSet.noneOf(Flags.class);
		} else {
			this.flags = EnumSet.copyOf(Arrays.asList(flags));
		}

		switch(moveType){
			case PHYSICAL:{
				if(!this.flags.contains(Flags.NO_CONTACT)) this.flags.add(Flags.CONTACT);
				break;
			}
			case SPECIAL:
			default:{
					if(!this.flags.contains(Flags.CONTACT)) this.flags.add(Flags.NO_CONTACT);
				break;
			}
		}
	}

	Move(Types type, MoveType moveType, int PP, int power, int accuracy, int cost, Flags... flags){
		this(type, moveType, PP, power, accuracy, cost, Battle_Priority.P0, flags);
	}

	Move(Types type, MoveType moveType, int PP, int power, int accuracy, Flags... flags){
		this(type, moveType, PP, power, accuracy, power, flags);
	}

	public boolean isSpecial(){
		return this.moveType == MoveType.SPECIAL;
	}

	public MoveType getMoveType(){
		return this.moveType;
	}

	public int getPower(){
		return this.power;
	}

	//TODO this is crucial
	/*public String getName(){
		return this.toString().replace('_', ' ');
	}*/

	public Types getType(Player attacker){
		return this.getType(attacker.getModifiedAbility());
	}

	public Types getType(Abilities ability){
		switch(ability){
			case NORMALIZE: return Types.NORMAL;
			case AERILATE: return Types.FLYING;

			default:
				break;
		}
		return this.type;
	}

	public int getPP(){
		return this.PP;
	}

	public int getCost(){
		return this.cost;
	}

	public double getAccuracy(){
		return this.accuracy;
	}

	public int getPriority(){
		return this.priority.getPriority();
	}

	public boolean has(Flags flag){
		return this.flags.contains(flag);
	}



	//Still run the normal battle logic?
	//If this move returns false, you have to manually damage the player then
	//Thankfully, there's still the getDamage() function that only gets the raw damage
	public BeforeResult runBefore(IChannel channel, Player attacker, Player defender){
		return BeforeResult.CONTINUE;
	}
	/*public BeforeResult runBefore(IChannel channel, Player attacker, Player defender){
		switch(this){
			case ABSORB:{
				if(willHit(this, attacker, defender, true)){
					//TODO Bigroot increases restoration
					//TODO Liquid Ooze ability inverts this
					if(!defender.has(Effects.VBattle.SUBSITUTE)
							&& !attacker.has(Effects.Volatile.HEAL_BLOCK)){
						int damage = getDamage(attacker, this, defender);
						if(damage > defender.HP) damage = defender.HP;
						defender.HP -= damage; //Don't have to check here, we did it above
						attackMessage(channel, attacker, this, defender, damage);
						//TODO If the defender has liquid ooze ability, then the attacker is actually hurt by this amount
						heal(channel, attacker, damage/2); //BigRoot increases this to +80% instead of +50%
					} else {
						failMessage(channel, attacker);
					}
				}
				return BeforeResult.STOP;
			}
			case ACROBATICS:{
				//TODO if a user has an item then this loses power
				return BeforeResult.HAS_ADJUSTED_DAMAGE;
			}
			case ACUPRESSURE:{
				if(attacker != defender && defender.has(Effects.VBattle.SUBSITUTE)){
					failMessage(channel, attacker);
					return BeforeResult.STOP;
				}
				attackMessage(channel, attacker, this, defender);
				List<Stats> stats = Arrays.asList(Stats.values());
				while(!stats.isEmpty()){
					Stats stat = stats.remove(Pokebot.ran.nextInt(stats.size()));
					if(defender.modifiers[stat.getIndex()] < 6){
						StatHandler.changeStat(channel, defender, stat, 2);
						return BeforeResult.STOP;
					}
				}
				failMessage(channel, attacker);
				return BeforeResult.STOP;
			}
			case AQUA_RING:{
				//TODO BigRoot increases restoration
				attacker.set(Effects.VBattle.AQUA_RING);
				return BeforeResult.STOP;
			}
			case AFTER_YOU:{
				//We have to be in a battle for this to work
				return BeforeResult.STOP;
			}
			case AROMATHERAPY:
			case HEAL_BELL:{
				//These are status moves, battle checking is done for us
				attacker.cureNV();
				for(Player player : attacker.battle.getParticipants()){
					if(player == attacker) continue;
					//TODO Sap Sipper will prevent Aromatherapy from working, instead raising their speed by 1 stage
					player.cureNV();
					player.lastAttacker = attacker; //Counts as "attacking" that pokemon
				}
				switch(this){
					case AROMATHERAPY:
					Pokebot.sendMessage(channel, "A soothing aroma wafted through the area, curing everyone of all" +
								" status effects!");
					break;
					case HEAL_BELL:
					Pokebot.sendMessage(channel, "A bell chimed, curing everyone of all status effects!");
					break;
					default:
					break;
				}
				return BeforeResult.STOP;
			}
			case AUTOTOMIZE:{
				StatHandler.changeStat(channel, attacker, Stats.SPEED, 2);
				return BeforeResult.STOP;
			}
			case AVALANCHE:{
				return BeforeResult.HAS_ADJUSTED_DAMAGE;
			}
			case BABY_DOLL_EYES:{
				if(willHit(this, attacker, defender, true)){
					StatHandler.changeStat(channel, defender, Stats.ATTACK, -1);
				} else {
					missMessage(channel, attacker);
				}
				return BeforeResult.STOP;
			}
			case BARRIER:{
				StatHandler.changeStat(channel, attacker, Stats.DEFENSE, 2);
				return BeforeResult.STOP;
			}
			case BELLY_DRUM:{
				if(attacker.HP <= Math.floor(attacker.getMaxHP()/2F)){
					failMessage(channel, attacker);
					return BeforeResult.STOP;
				}
				StatHandler.changeStat(channel, attacker, Stats.ATTACK, 12);
				Pokebot.sendMessage(channel, attacker.mention()+" maxed out their attack!");
				attacker.HP -= (int) Math.floor(attacker.getMaxHP()/2F);
				return BeforeResult.STOP;
			}
			case BLAST_BURN:{
				//Even if the user misses, they have to recharge
				attacker.set(Effects.VBattle.RECHARGING);
				return BeforeResult.CONTINUE;
			}
			case BLIZZARD:{
				//TODO Weather-HAIL
				//TODO This hits every opponent
				return BeforeResult.CONTINUE;
			}
			case DESTINY_BOND:{
				Pokebot.sendMessage(channel, attacker.mention()+" will take it's foe down with it!");
				return BeforeResult.STOP;
			}
			case DOUBLE_KICK:{
				return BeforeResult.HAS_ADJUSTED_DAMAGE;
			}
			case GASTRO_ACID:{
				if(willHit(this, attacker, defender, true)){
					attackMessage(channel, attacker, this, defender);
					defender.set(Effects.VBattle.ABILITY_BLOCK);
					Pokebot.sendMessage(channel, defender.mention()+" 's ability was suppressed!");
				} else {
					missMessage(channel, attacker);
				}
				return BeforeResult.STOP;
			}
			case GRAVITY:{
				attacker.battle.set(Battle.BattleEffects.GRAVITY, 1);
				attackMessage(channel, attacker, this);
				Pokebot.sendMessage(channel, "Gravity Intensified!");
				return BeforeResult.STOP;
			}
			case GUILLOTINE:{
				return BeforeResult.HAS_ADJUSTED_DAMAGE;
			}
			case BODY_SLAM:
			case STOMP:
			case STEAM_ROLLER:{
				if(defender.has(Effects.VBattle.MINIMIZE)){
					return BeforeResult.ALWAYS_HIT_ADJUSTED_DAMAGE;
				}
				return BeforeResult.CONTINUE;
			}
			case JUMP_KICK:{
				if(willHit(this, attacker, defender, true)){
					return BeforeResult.CONTINUE;
				}
				Pokebot.sendMessage(channel, attacker.mention()
						+" missed and took crash damage instead!");
				attacker.HP = Math.max(0, attacker.HP-(attacker.getMaxHP()/2));
				return BeforeResult.STOP;
			}
			case SPLASH:{
				Pokebot.sendMessage(channel, attacker.mention()+" used Splash!... but nothing happened.");
				return BeforeResult.STOP;
			}
			default:
				break;
		}
		return BeforeResult.CONTINUE;
	}*/

	public void runAfter(IChannel channel, Player attacker, Player defender, int damage){}
	@SuppressWarnings("unused")
	/*public void runAfter(IChannel channel, Player attacker, Player defender, int damage){
		if(!defender.inBattle()) return; //So far, any move that has after-effects needs a battle
			case BLUE_FLARE:{
				if(diceRoll(20)){
					burn(channel, defender);
				}
				break;
			}
			case BONE_CLUB:{
				if(diceRoll(10)) flinch(channel, defender);
				break;
			}
			case ANCIENT_POWER:{
				if(diceRoll(10)){
					for(int x = 1; x < 6; x++){ //ATTACK through SPEED
						attacker.modifiers[x] = (byte) Math.min(6, attacker.modifiers[x]+1);
					}
					Pokebot.sendMessage(channel, attacker.mention()+" raised all of their stats!");
				}
				break;
			}
			case BODY_SLAM:{
				if(diceRoll(30)) paralyze(channel, defender);
				break;
			}
			case BOLT_STRIKE:{
				if(diceRoll(20)) paralyze(channel, defender);
				break;
			}
			case BLIZZARD:
			case ICE_PUNCH:{
				if(diceRoll(10)){
					freeze(channel, defender);
				}
				break;
			}
			case POISON_TAIL:{
				if(diceRoll(10)){
					poison(channel, defender);
				}
				break;
			}
			case SKY_ATTACK:
			case STEAM_ROLLER:
			case STOMP:{
				if(diceRoll(30)) flinch(channel, defender);
				break;
			}
			case THUNDER_PUNCH:{
				if(diceRoll(10)){
					paralyze(channel, defender);
				}
				break;
			}
			default:
				break;
		}
	}*/

	//This only runs in a battle context, has the same as runBefore. Multiturn attacks act like normal attacks
	// outside of battle context
	public BeforeResult runMultiturn(IChannel channel, Player attacker, Player defender){
		switch(this){
			//TODO Bind
			/*case BIND:{
				if(attacker.lastMoveData == MoveConstants.NOTHING){
					//TODO Rapid spin
					//TODO Grip claw
					//TODO Binding band

				}
			}*/
			case FLY:{
				switch(attacker.lastMoveData){
					case MoveConstants.NOTHING:{
						if(checkParalysis(attacker)) return BeforeResult.STOP;
						Pokebot.sendMessage(channel, attacker.mention()+" flew up high!");
						attacker.lastMoveData = MoveConstants.FLYING;
						attacker.set(Effects.VBattle.SEMI_INVULNERABLE);
						return BeforeResult.STOP;
					}
					case MoveConstants.FLYING:{
						attacker.remove(Effects.VBattle.SEMI_INVULNERABLE);
						attacker.lastMoveData = MoveConstants.NOTHING;
						return BeforeResult.CONTINUE;
					}
					default:
						return BeforeResult.STOP;
				}
			}
			case RAZOR_WIND:
			case SKY_ATTACK:{
				switch(attacker.lastMoveData){
					case MoveConstants.NOTHING:{
						if(checkParalysis(attacker)) return BeforeResult.STOP;
						switch(this){
							case SKY_ATTACK:
								Pokebot.sendMessage(channel, attacker.mention()+" is glowing!");
								break;
							case RAZOR_WIND:
								Pokebot.sendMessage(channel, attacker.mention()+" whipped up a whirlwind!");
								break;
							default:
								break;
						}
						attacker.lastMoveData = MoveConstants.GLOWING;
						attacker.set(Effects.VBattle.CHARGING);
						return BeforeResult.STOP;
					}
					case MoveConstants.GLOWING:{
						attacker.lastMoveData = MoveConstants.NOTHING;
						attacker.remove(Effects.VBattle.CHARGING);
						return BeforeResult.CONTINUE;
					}
					default:
						return BeforeResult.STOP;
				}
			}
			default:
				return BeforeResult.CONTINUE;
		}
	}

	private int getAdjustedDamage(Player attacker, Player defender){
		switch(this){
			case ACROBATICS:
			case STEAM_ROLLER:
			case STOMP:{
				return getDamage(attacker, this, defender, this.power*2);
			}
			case AVALANCHE:{
				return getDamage(attacker, this, defender); //TODO does double damage if attacker was hit
			}
			case DOUBLE_KICK:{
				return getDamage(attacker, this, defender)*2;
			}
			case GUILLOTINE:{
				return defender.HP;
			}
			default:
				return 0;
		}
	}

	public static void registerMoves(){
		System.out.println("Registering moves...");
		final float[] typicalHitRate = {100, 33.3F, 33.3F, 16.7F, 16.7F};
		REGISTRY.put("ARM_THRUST", new MultiHit(Types.FIGHTING, MoveType.PHYSICAL, 20, 15, 100, 50, typicalHitRate));
		REGISTRY.put("BARRAGE", new MultiHit(Types.NORMAL, MoveType.PHYSICAL, 20, 15, 85, 50, typicalHitRate, Flags.NO_CONTACT,
				Flags.BALLBASED));
		REGISTRY.put("COMET_PUNCH", new MultiHit(Types.NORMAL, MoveType.PHYSICAL, 15, 18, 85, 60, typicalHitRate));
		REGISTRY.put("DOUBLE_SLAP", new MultiHit(Types.NORMAL, MoveType.PHYSICAL, 10, 15, 85, 50, typicalHitRate));
		REGISTRY.put("DOUBLE_KICK", new MultiHit(Types.FIGHTING, MoveType.PHYSICAL, 30, 30, 100, 2)); //Hits twice

		REGISTRY.put("ACID_ARMOR", new StatusChange(Types.POISON, 20, Stats.DEFENSE, 2, Flags.UNTARGETABLE));
		REGISTRY.put("AGILITY", new StatusChange(Types.PSYCHIC, 30, Stats.SPEED, 2, Flags.UNTARGETABLE));
		REGISTRY.put("AMNESIA", new StatusChange(Types.PSYCHIC, 20, Stats.SPECIAL_DEFENSE, 2, Flags.UNTARGETABLE));
		REGISTRY.put("SWORDS_DANCE", new StatusChange(Types.NORMAL, 20, Stats.ATTACK, 2, Flags.UNTARGETABLE));

		REGISTRY.put("ACID", new StatChangeDamageMove(Types.POISON, MoveType.SPECIAL, 30, 40, 100, 10, Stats.SPECIAL_DEFENSE,
				-1, StatChangeDamageMove.Who.DEFENDER));
		REGISTRY.put("ACID_SPRAY", new StatChangeDamageMove(Types.POISON, MoveType.SPECIAL, 20, 40, 100, 100, 100, Stats
				.SPECIAL_DEFENSE, -2, StatChangeDamageMove.Who.DEFENDER, Flags.BALLBASED));
		REGISTRY.put("AURORA_BEAM", new StatChangeDamageMove(Types.ICE, MoveType.SPECIAL, 20, 65, 100, 75, 10, Stats.ATTACK,
				-1, StatChangeDamageMove.Who.DEFENDER));

		REGISTRY.put("AIR_SLASH", new FlinchDamageMove(Types.FLYING, MoveType.SPECIAL, 15, 75, 95, 90, 30));
		REGISTRY.put("ASTONISH", new FlinchDamageMove(Types.GHOST, MoveType.PHYSICAL, 15, 30, 100, 45, 30));
		REGISTRY.put("BITE", new FlinchDamageMove(Types.DARK, MoveType.PHYSICAL, 25, 60, 100, 75, 30));

		REGISTRY.put("BLAZE_KICK", new AilmentDamageMove(Types.FIRE, MoveType.PHYSICAL, 10, 85, 90, 90, 10, Move::burn));
		REGISTRY.put("FIRE_PUNCH", new AilmentDamageMove(Types.FIRE, MoveType.PHYSICAL, 15, 75, 100, 80, 10, Move::burn));

		System.out.println("Done registering moves");
	}

	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
	public static boolean attack(IChannel channel, IAttack attack){
		return attack(channel, attack.attacker, attack.move, attack.defender, -1);
	}

	//Returns ifDefenderFainted
	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
	public static boolean attack(IChannel channel, Player attacker, Move move, Player defender, int slot){
		if(!attacker.inBattle() &&
				(move.getMoveType() == MoveType.STATUS || move.getMoveType() == MoveType.BATTLE_STATUS)){
			Pokebot.sendMessage(channel, "But it doesn't work here!");
			return false;
		}
		if(attacker.has(Effects.NonVolatile.FROZEN)){
			//TODO if the attacker uses Fusion Flare, Flame Wheel, Sacred Fire, Flare Blitz
			//TODO or Scald, it will thaw them and/or the opponent out
			if(diceRoll(20)){
				attacker.cureNV();
				Pokebot.sendMessage(channel, attacker.mention()+" thawed out!");
			} else {
				Pokebot.sendMessage(channel, attacker.mention()+" is frozen solid!");
				return false;
			}
		}
		if(attacker.has(Effects.NonVolatile.SLEEP)){
			//TODO if the move is snore or sleep talk, then it will work
			if(attacker.counter-- <= 0){
				Pokebot.sendMessage(channel, attacker.mention()+" woke up!");
			} else {
				Pokebot.sendMessage(channel, attacker.mention()+" is fast asleep!");
				return false;
			}
		}
		BeforeResult cont = BeforeResult.CONTINUE;
		if(move.has(Flags.HAS_BEFORE)){
			cont = move.runBefore(channel, attacker, defender);
		}
		if(cont != BeforeResult.STOP){
			if(slot >= 0 && slot < 4) attacker.PP[slot]--;
			//We can assume we are in a battle context from inBattle. Also, the move.has operation is faster than the
			// player.inBattle operation
			if(move.has(Flags.MULTITURN) && attacker.inBattle()){
				cont = move.runMultiturn(channel, attacker, defender);
			}
		}
		switch(cont){
			//noinspection DefaultNotLastCaseInSwitch
			default:{
				if(cont.willHitAlways() || willHit(move, attacker, defender, !(move.getAccuracy() > 1))){
					//Do battle attack logic
					int damage;
					if(cont.hasAdjustedDamage()){
						damage = move.getAdjustedDamage(attacker, defender);
					} else {
						damage = getDamage(attacker, move, defender);
					}
					defender.HP = Math.max(0, defender.HP-damage);
					if(move.has(Flags.HAS_AFTER)) move.runAfter(channel, attacker, defender, damage);
					attackMessage(channel, attacker, move, defender, damage);
				} else { //we check here again to make sure cont wasn't what made it not run
					missMessage(channel, attacker);
				}
				break;
			}
			case RUN_AFTER:{
				if(move.has(Flags.HAS_AFTER)) move.runAfter(channel, attacker, defender, 0);
				break;
			}
			case STOP:
				break;
		}
		if(attacker.HP == 0 && !attacker.inBattle()){
			//Checking for things like recoil
			faintMessage(channel, attacker);
		}
		if(defender.HP == 0){
			faintMessage(channel, defender);
			return true;
		}
		Pokebot.sendMessage(channel, defender.mention()+" has "+defender.HP+"HP left!");
		return false;
	}

	private static void attackMessage(IChannel channel, Player attacker, Move move, Player defender, int damage){
		Pokebot.sendMessage(channel, attacker.mention()
				+" attacked "+defender.mention()+" with "+move.getName()
				+" for "+damage+" damage!");
	}

	private static void attackMessage(IChannel channel, Player attacker, Move move, Player defender){
		Pokebot.sendMessage(channel, attacker.mention()+" used "+move.getName()+" on "+defender.mention()+'!');
	}

	private static void attackMessage(IChannel channel, Player attacker, Move move){
		Pokebot.sendMessage(channel, attacker.mention()+" used "+move.getName()+'!');
	}

	private static void recoilMessage(IChannel channel, Player attacker){
		Pokebot.sendMessage(channel, attacker.mention()+" took damage from recoil!");
	}

	public static void faintMessage(IChannel channel, Player defender){
		Pokebot.sendMessage(channel, defender.mention()+" has fainted!");
	}

	protected static void missMessage(IChannel channel, Player attacker){
		Pokebot.sendMessage(channel, "But "+attacker.mention()+" missed!");
	}

	private static void failMessage(IChannel channel, Player attacker){
		Pokebot.sendMessage(channel, "But "+attacker.mention()+"'s move failed!");
	}

	//Call AFTER you heal
	public static void heal(IChannel channel, Player attacker, int heal){
		if(heal < 0){
			attack(channel, attacker, -heal);
			return;
		}
		if(heal == 0){
			Pokebot.sendMessage(channel, "But it had no effect!");
			return;
		}
		attacker.HP = Math.min(attacker.getMaxHP(), attacker.HP+heal);
		if(attacker.HP == attacker.getMaxHP()){
			Pokebot.sendMessage(channel, attacker.mention()+" was fully healed!");
		} else {
			Pokebot.sendMessage(channel, attacker.mention()+" restored "+heal+"HP!");
		}
	}

	private static void attack(IChannel channel, Player defender, int damage){
		if(damage < 0){
			heal(channel, defender, -damage);
			return;
		}
		if(damage == 0){
			Pokebot.sendMessage(channel, "But it had no effect!");
			return;
		}
		defender.HP = Math.max(0, defender.HP-damage);
		Pokebot.sendMessage(channel, defender.mention()+" hurt themselves for "+damage+" damage!");
	}

	private static void burn(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		boolean isImmune = isType(defender, Types.FIRE);
		if(!isImmune){
			defender.set(Effects.NonVolatile.BURN);
		}
		effectMessage(channel, defender, isImmune, "burns", "burned");
	}

	private static void freeze(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		boolean isImmune = isType(defender, Types.ICE);
		if(!isImmune){
			defender.set(Effects.NonVolatile.FROZEN);
		}
		effectMessage(channel, defender, isImmune, "freezing", "frozen");
	}

	private static void paralyze(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		boolean isImmune = isType(defender, Types.ELECTRIC);
		if(!isImmune){
			defender.set(Effects.NonVolatile.PARALYSIS);
		}
		effectMessage(channel, defender, isImmune, "paralysis", "paralyzed");
	}

	private static void paralysisMessage(IChannel channel, Player attacker){
		Pokebot.sendMessage(channel, attacker.mention()+" is paralyzed! They can't move!");
	}

	private static void poison(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		boolean isImmune = isType(defender, Types.POISON) || isType(defender, Types.STEEL);
		if(!isImmune){
			defender.set(Effects.NonVolatile.POISON);
		}
		effectMessage(channel, defender, isImmune, "poison", "poisoned");
	}

	private static void toxic(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		boolean isImmune = isType(defender, Types.POISON) || isType(defender, Types.STEEL);
		if(!isImmune){
			defender.set(Effects.NonVolatile.TOXIC);
			defender.counter = 0;
		}
		effectMessage(channel, defender, isImmune, "poison", "badly poisoned");
	}

	private static void sleep(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		defender.set(Effects.NonVolatile.SLEEP);
		defender.counter = Pokebot.ran.nextInt(3)+1;

	}

	protected static void flinch(IChannel channel, Player defender){
		if(defender.has(Effects.VBattle.SUBSITUTE)){
			Pokebot.sendMessage(channel, defender.mention()+"'s subsitute blocked it!");
			return;
		}
		defender.set(Effects.Volatile.FLINCH); //TODO Check for abilities
		Pokebot.sendMessage(channel, defender.mention()+" flinched!");
		//We assume this is only called within-battle
	}

	private static void effectMessage(IChannel channel, Player defender, boolean isImmune, String immune, String
			afflicted){
		if(isImmune){
			Pokebot.sendMessage(channel, defender.mention()+"'s type is immune to "+immune+"!");
		} else {
			Pokebot.sendMessage(channel, defender.mention()+" was "+afflicted+"!");
		}
	}

	private static boolean checkParalysis(Player attacker){
		if(!attacker.has(Effects.NonVolatile.PARALYSIS)) return false;
		if(diceRoll(25)){
			//We assume paralysis only takes place in a battle
			paralysisMessage(attacker.battle.channel, attacker);
			return false;
		}
		return true;
	}

	private static boolean runBattleLogic(Player attacker, Player defender){
		return attacker.inBattle() && defender.inBattle() && attacker.battle == defender.battle;
	}

	public static int getDamage(Player attacker, Move move, Player defender){
		return getDamage(attacker, move, defender, move.getPower());
	}

	public static int getDamage(Player attacker, Move move, Player defender, int power){
		double modifier =
				getStab(attacker, move) //STAB
						*Types.getTypeMultiplier(attacker, move, defender) //Effectiveness
						*getOtherModifiers(move, defender)
						*((Pokebot.ran.nextInt(100-85)+85)/100D) //Random chance
				;
		if(attacker.hasAbility(Abilities.ANALYTIC)) modifier *= 1.3; //30% increase
		double a = ((2*attacker.level)+10D)/250D;
		double b;
		if(move.isSpecial()){
			b = attacker.getSpecialAttackStat();
			b /= defender.getSpecialDefenseStat();
		} else {
			b = attacker.getAttackStat();
			b /= defender.getDefenseStat();
		}
		power = (int) getPowerChange(attacker, move, defender, power);
		return (int) (((a*b*power)+2)*modifier);
	}

	public static double getStab(Player attacker, Move move){
		if(isType(attacker, move.getType(attacker))){
			if(attacker.hasAbility(Abilities.ADAPTABILITY)) return 2;
			return 1.5;
		}
		return 1;
	}

	@SuppressWarnings("UnusedParameters")
	public static double getPowerChange(Player attacker, Move move, Player defender, double power){
		switch(attacker.getModifiedAbility()){
			case AERILATE:{
				power *= 1.3;
				break;
			}
			default:
				break;
		}
		return power;
	}

	//TODO perhaps this isn't necessary?
	public static int getOtherModifiers(Move move, Player defender){
		switch(move){
			case GUST:{
				switch(defender.lastMove){
					case FLY:{
						if(defender.lastMoveData == MoveConstants.FLYING) return 2;
						break;
					}
					default:
						break;
				}
				break;
			}
			default:
				break;
		}
		return 1;
	}

	//Dice rolls for a hit, if not factoring in changes to accuracy and evasion, you can safely
	//pass in null for Attacker and Defender
	@SuppressWarnings("BooleanParameter")
	public static boolean willHit(Move move, Player attacker, Player defender, boolean factorChanges){
		if(checkParalysis(attacker)) return false;
		if(defender.inBattle()){
			if(defender.has(Effects.VBattle.SEMI_INVULNERABLE)){
				switch(move){
					//Pokémon that use Fly, Bounce, or Sky Drop, or are targeted by Sky Drop fly or are flown up high, and
					// are vulnerable to Gust, Smack Down, Sky Uppercut, Thunder, Twister, and Hurricane
					//If the move Gravity is used, these moves cannot be used and any Pokémon in the air return to the ground with their move cancelled

					case GUST:{
						switch(defender.lastMove){
							case FLY:{
								//case BOUNCE:{
								return true;
							}
							default:
								return false;
						}
					}
					default:
						return false;
				}
			}
			if(defender.has(Effects.VBattle.PROTECTION)){
				switch(move){
					//TODO Shadow Force and Fient remove protection for the rest of the turn
					//TODO
					default:
						Pokebot.sendMessage(defender.battle.channel, "But "+defender.mention()+" protected itself!");
						return false;
				}
			}
			switch(defender.getModifiedAbility()){
				case BULLETPROOF:{
					if(move.has(Flags.BALLBASED)){
						Pokebot.sendMessage(defender.battle.channel, defender.mention()+" is immune to the attack!");
						return false;
					}
					break;
				}
				default:
					break;
			}
		}

		double accuracy = move.getAccuracy();
		if(factorChanges){
			accuracy *= attacker.getAccuracy()/defender.getEvasion();
		}
		return Pokebot.ran.nextDouble() <= accuracy;
	}

	public static boolean isType(Player player, Types type){
		return player.primary == type || player.secondary == type;
	}

	@SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
	public static boolean diceRoll(double chance){
		return Pokebot.ran.nextDouble()*100D <= chance;
	}

	public static int getTimesHit(float... chances){
		int times = 0;
		while(times < chances.length){
			if(!diceRoll(chances[times])){
				break;
			}
			times++;
		}
		return times;
	}

	protected enum BeforeResult{
		CONTINUE,
		HAS_ADJUSTED_DAMAGE(false, true),
		ALWAYS_HIT(true),
		ALWAYS_HIT_ADJUSTED_DAMAGE(true, true),
		//SKIP_DAMAGE,
		RUN_AFTER,
		STOP;

		private final boolean hitAlways;
		private final boolean hasAdjustedDamage;

		BeforeResult(){
			this(false);
		}

		BeforeResult(boolean hitAlways){
			this(hitAlways, false);
		}

		BeforeResult(boolean hitAlways, boolean hasAdjustedDamage){
			this.hitAlways = hitAlways;
			this.hasAdjustedDamage = hasAdjustedDamage;
		}

		public boolean willHitAlways(){
			return this.hitAlways;
		}

		public boolean hasAdjustedDamage(){
			return this.hasAdjustedDamage;
		}
	}

	public enum Flags{
		UNTARGETABLE,
		BYPASSES_IMMUNITIES,
		MULTITURN, //If a move takes more than one turn
		FLIGHT, //If a move requires the use of flying
		CONTACT, //Overrides the default setting
		NO_CONTACT, //Overrides the default setting
		BALLBASED, //The move is based on balls or bombs
	}

	public enum Battle_Priority{
		P5(+5),
		P4(+4),
		P3(+3),
		P2(+2),
		P1(+1),
		P0(0),
		N1(-1),
		N2(-2),
		N3(-3),
		N4(-4),
		N5(-5),
		N6(-6),
		N7(-7);

		private final int priority;

		Battle_Priority(int priority){
			this.priority = priority;
		}

		public int getPriority(){
			return this.priority;
		}
	}

	public enum MoveType{
		PHYSICAL,
		SPECIAL,
		STATUS,
		BATTLE_STATUS
	}

	private static class MoveSorter implements Comparator<String>{

		@Override
		public int compare(String o1, String o2){
			try{
				char c1, c2;
				for(int x = 0; x < o2.length(); x++){
					c1 = o1.charAt(x);
					c2 = o2.charAt(x);
					if(c1 == c2) continue;
					return c2 - c1;
				}
				return 0;
			}catch(IndexOutOfBoundsException e){
				return 1;
			}
		}
	}
}
