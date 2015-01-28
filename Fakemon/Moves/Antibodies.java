/*
When this hits, the user poisons itself, then maximizes its special defense. It might not hit because the move erroneously checks for accuracy.

This will be the Signature move of Igaken.

*/

import effects.Effect;
import effects.PoisonEffect;
import effects.StatModEffect;
import fakemon.BattleScreen;
import fakemon.MoveInfo;
import fakemon.Pokemon;
import fakemon.Type;


public class Antibodies extends MoveInfo {
	public Antibodies (){
		init("Antibodies", 20, 36, 100, 0, false, Category.STATUS, Type.getByName("Poison"));
	}
	public boolean onHit(Pokemon user, Pokemon target, BattleScreen battle){
		user.addEffect(new PoisonEffect(),user,battle);
		user.addEffect(new StatModEffect(Effect.SPECIAL_DEFENSE,12), user, battle);
		return true;	
	}
}
