/*
The intent is for creatures to use this move when they have no PP in any other moves. When they use it, it deals some sort of damage, and the user takes 1/4 of damage dealt. It's unclear what else this move does.
*/

import fakemon.BattleScreen;
import fakemon.MoveInfo;
import fakemon.Pokemon;
import fakemon.PokemonInfo;
import fakemon.Type;
import fakemon.MoveInfo.Category;


public class Struggle extends MoveInfo {

	public void hit(Pokemon user, Pokemon target, BattleScreen battle){
		double critBonus;
		if(isCritical()){
			critBonus = getCritDamMod();
			battle.displayMessage("Critical hit!");

		} else {
			critBonus = 1;
		}


		if(target == null || user == null) return;

		double stabBonus = 1;

		double mod = stabBonus*critBonus * getDamMod()*user.getDamMod();

		int power = 50;
		
		float attack = user.getStat(PokemonInfo.ATTACK);
		float defense = target.getStat(PokemonInfo.DEFENSE);
		int damage = (int) (((((2.0*user.getLevel()/5+2)*attack*power/defense)/50.0)+2)*mod*(Math.random()*.15 + .85));
		
		battle.damage(target, damage);
		battle.displayMessage(target.getName() + " hurt itself in its struggle");

		battle.damage(target,(int) ((1f/4) * target.getStat(PokemonInfo.MAX_HP)));

	}
}
