/*
Bad Poison.

At the end of the first turn, the afflicted loses 1/16 of their maximum health. At the end of every turn, the poison damage increases by another 1/16 of the target's maximum health. If the afflicted gets hit with bad poison again, the damage increases by another 1/16th.

Bad poison does not affect Steel-Poison types or Poison-types.

Bad poison does not conflict with any other effect, so it is possible to poison and bad-poison a target.

*/

package effects;

import fakemon.BattleScreen;
import fakemon.Pokemon;
import fakemon.PokemonInfo;
import fakemon.Screen;
import fakemon.Type;

public class BadPoisonEffect extends Effect{
	
	double psnAmount = 1/16f;
	
	// At the end of every turn, the afflicted takes damage and the damage increases.
	public void onTurnEnd(BattleScreen screen){
		screen.displayMessage(target.getName() + " took damage from poison!");

		screen.damage(target,(int) (psnAmount * target.getStat(PokemonInfo.MAX_HP)));
		psnAmount += 1/16f;
	}

	// Bad poison stacks with more bad poison.
	@Override
	public boolean add(Effect e, Screen screen) {
		if(e instanceof BadPoisonEffect)
		{
			psnAmount += 1/16f;
			return true;
		}
		return false;
	}

	// Bad Poison does not affect Steel types or Poison types.
	@Override
	public boolean canBeApplied(Pokemon p,Screen screen)
	{
		boolean isPoison = p.getTypes().contains(Type.getByName("Poison"));
		boolean isSteel = p.getTypes().contains(Type.getByName("Steel"));

		return !isPoison && !isSteel;
	}
	// Doesn't conflict with anything.
	@Override
	public boolean conflicts(Effect e, Screen screen) {
		return false;
	}
	// Application message.
	public void onNewApply(Screen screen,Pokemon user, Pokemon target){
		super.onNewApply(screen, user, target);
		screen.displayMessage(target.getName() + " was badly poisoned!");
	}
}
