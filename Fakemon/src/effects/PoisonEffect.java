/*
Poison.

At the end of every turn, the afflicted loses 1/16 of their maximum health.
*/

package effects;

import fakemon.BattleScreen;
import fakemon.Pokemon;
import fakemon.PokemonInfo;
import fakemon.Screen;

public class PoisonEffect extends Effect {

	// Doesn't add to anything.
	@Override
	public boolean add(Effect e, Screen screen) {
		
		return false;
	}

	// Cannot poison a poisoned target.
	@Override
	public boolean prevents(Effect e,Screen screen)
	{
		if(e instanceof PoisonEffect)
		{
			return true;
		}
		return false;
	}

	// Message when applying damage.
	@Override
	public void onTurnEnd(BattleScreen screen){
		screen.displayMessage(target.getName() + " took damage from poison!");

		screen.damage(target,(int) ((1f/16) * target.getStat(PokemonInfo.MAX_HP)));
	}

	// Poison doesn't conflict with anything.
	@Override
	public boolean conflicts(Effect e, Screen screen) {
		return false;
	}

	// On-apply message.
	@Override
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		screen.displayMessage(target.getName() + " was poisoned!");
	}

}
