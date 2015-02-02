package fakemon;

import java.util.ArrayList;
import java.util.HashSet;

import effects.Effect;

/*
 * This is the superclass for all pokemon moves.
 * 
 * @param {string) name - The name of the move. Accepts any string.
 * @param {int} basePP - The maximum number of times a move can be used before resting or recovering. Accepts any integer, but nonpositive integers will make the move unusable.
 * @param {int} maxPP - The highest the player should be able to raise that maximum. Should be equal to or greater than basePP.
 * @param {int} accuracy - The chance of hitting with a move. Accepts any integer, but values below 1 cannot possibly hit ever, no matter what accuracy-enhancers are in play. Values above 100 are more likely to hit when the user suffers from accuracy-reducing effects.
 * @param {int} power - Affects the damage of a move. A value of 0 will deal no damage, while negative values will heal the target (but that may not be the preferred way to heal).
 * @param {boolean} contact - Unknown. It may connect with moves such as Ignition, which check for whether a move "contacts" the target.
 * @param {Category} cat - The category of the move, such as Category.PHYSICAL, Category.SPECIAL, or Category.STATUS. Affects many things, such as what stat is used for damage and resistance.
 * @param {...Type} types - What type of move this is, such as Fighting, Psychic, or Flying. Affects many things, such as bonus damage and resistance.
 */
public abstract class MoveInfo {
	private static HashSet<MoveInfo> moves = new HashSet<MoveInfo>();
	private String name;
	public enum Category {PHYSICAL,SPECIAL,STATUS};
	private ArrayList<Type> types;
	private int maxPP;
	private int basePP;
	private int accuracy;
	private int power;
	private boolean contact;
	private Category cat;
	protected int priority;

	protected void init(String name, int basePP, int maxPP, int accuracy, int power, boolean contact, Category cat, Type... types)
	{

		if (name == null)
			name = "???";
		this.name = name;
		moves.add(this);

		this.types = new ArrayList<Type>();
		for(Type t:types)
			this.types.add(t);

		this.accuracy = accuracy;
		this.power = power;
		this.maxPP = maxPP;
		this.basePP = basePP;
		this.cat = cat;
		this.contact = contact;
		moves.add(this);
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public static MoveInfo getByName(String name)
	{
		for(MoveInfo m : moves)
			if(m.name.equalsIgnoreCase(name))
				return m;
		System.out.println("Move not found : " + name);
		return null;

	}
	public static MoveInfo[] getList()
	{
		MoveInfo[] m = new MoveInfo[moves.size()]; 
		return moves.toArray(m);			
	}
	public int getMaxPP() {
		return maxPP;
	}
	public int getBasePP() {
		return basePP;
	}
	public boolean isValid() {
		return true;
	}
	public int getPriority() {
		return priority;
	}
	public void hit(Pokemon user, Pokemon target, BattleScreen battle){
		if(cat == Category.STATUS){
			return;
		}
		double critBonus;
		if(isCritical()){
			critBonus = getCritDamMod();
			battle.displayMessage("Critical hit!");

		} else {
			critBonus = 1;
		}


		if(target == null || user == null) return;

		double stabBonus = 1;
		for(Type t : types)
			if(user.getTypes().contains(t))
				stabBonus *= 1.5;

		double typeBonus = 1;
		for(Type t : types)
			for(Type t2 : target.getTypes())
				typeBonus *= t.getEffectiveness(t2);

		if(typeBonus > 1.00001 && typeBonus <= 2.00001)
			battle.displayMessage(String.format("It was super effective. (%.2fx)",typeBonus));
		if (typeBonus > 2.00001)
			battle.displayMessage(String.format("Wow! It was incredibly effective!. (%.2fx)",typeBonus));
		if(typeBonus < .99999 && typeBonus > 0.49999)
			battle.displayMessage(String.format("But it wasn't very effective. (%.2fx)",typeBonus));
		if(typeBonus < 0.49999 && typeBonus >= 0.00001)
			battle.displayMessage(String.format("Oh. It was very ineffective. (%.2fx)",typeBonus));

		if(typeBonus < 0.00001)
			battle.displayMessage(String.format("But it had no effect . (%.2fx)",typeBonus));
		
		double mod = stabBonus*typeBonus*critBonus * getDamMod()*user.getDamMod();
		
		if(cat == Category.PHYSICAL){
			float attack = user.getStat(PokemonInfo.ATTACK);
			float defense = target.getStat(PokemonInfo.DEFENSE);
			int damage = (int) (((((2.0*user.getLevel()/5+2)*attack*power/defense)/50.0)+2)*mod*(Math.random()*.15 + .85));
			battle.damage(target, damage);

		} else if (cat == Category.SPECIAL){

			float attack = user.getStat(PokemonInfo.SPECIAL_ATTACK);
			float defense = target.getStat(PokemonInfo.SPECIAL_DEFENSE);
			int damage = (int) (((((2.0*user.getLevel()/5+2)*attack*power/defense)/50.0)+2)*mod*(Math.random()*.15 + .85));
			battle.damage(target, damage);
		}

	}
	public boolean isCritical(){
		return Util.flip(.0625f * getCritRateMod());
	}

	public void onUse(Pokemon user, Pokemon target, BattleScreen battle){}

	public boolean onHit(Pokemon user, Pokemon target, BattleScreen battle){
		hit(user, target, battle);
		return true;
	}

	public void onMiss(Pokemon user, Pokemon target2, BattleScreen battle){}

	public boolean doesHit(Pokemon user, Pokemon target, BattleScreen battle){
		float hitChance = accuracy/100f * user.getStat(Effect.ACCURACY)/target.getStat(Effect.EVASION);
		return Util.flip(hitChance);
	}

	public float getCritRateMod(){
		return 1;
	}
	
	public float getCritDamMod(){
		return 1.5f;
	}
	
	public float getDamMod(){
		return 1;
	}
}
