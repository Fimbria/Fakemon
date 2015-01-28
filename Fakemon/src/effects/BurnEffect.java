/*
Burn, baby, burn!

Deals 1/8th of the afflicted's max HP every round. The afflicted deals half damage.

Burn does not apply to Fire types.
*/

package effects;

import fakemon.BattleScreen;
import fakemon.Pokemon;
import fakemon.PokemonInfo;
import fakemon.Screen;
import fakemon.Type;

public class BurnEffect extends Effect {

	// No special stacking effects.
	@Override
	public boolean add(Effect e, Screen screen) {
		
		return false;
	}

	// Can't ignite what's already lit.
	public boolean prevents(Effect e,Screen screen)
	{
		if(e instanceof BurnEffect)
		{
			return true;
		}
		return false;
	}

	// Deals 1/8 max hp damage.
	public void onTurnEnd(BattleScreen screen){
		screen.displayMessage(target.getName() + " took damage from burn!");

		screen.damage(target,(int) ((1f/8) * target.getStat(PokemonInfo.MAX_HP)));
	}

	// Can't burn fire types.
	public boolean canBeApplied(Pokemon p,Screen screen)
	{
		return !p.getTypes().contains(Type.getByName("Fire"));
	}

	// Nothing conflicts with burn.
	@Override
	public boolean conflicts(Effect e, Screen screen) {
		return false;
	}

	// Application message.
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		screen.displayMessage(target.getName() + " was burned!");
	}

	// The afflicted deals half damage.
	public float getDamMod(){
		return .5f;
	}
}
